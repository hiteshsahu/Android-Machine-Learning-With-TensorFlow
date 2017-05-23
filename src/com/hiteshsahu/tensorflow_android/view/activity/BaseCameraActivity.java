/*
 * Copyright 2016 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hiteshsahu.tensorflow_android.view.activity;

import android.media.Image.Plane;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.hiteshsahu.tensorflow_android.utils.Animatrix;
import com.hiteshsahu.tensorflow_android.utils.Logger;
import com.hiteshsahu.tensorflow_android.view.custome_view.OverlayView;
import com.hiteshsahu.tensorflow_android.view.fragments.CameraConnectionFragment;

import org.tensorflow.demo.R;

import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseCameraActivity extends AppCompatActivity implements OnImageAvailableListener {
    private static final Logger LOGGER = new Logger();

    private boolean debug = false;
    private Handler handler;
    private HandlerThread handlerThread;

    @BindView(R.id.frag_container)
    View fragContainer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        LOGGER.d("onCreate " + this);
        super.onCreate(null);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        //Animate Circular Reveal on APp Start
        fragContainer.setVisibility(View.INVISIBLE);
        ViewTreeObserver viewTreeObserver = fragContainer.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Animatrix.circularRevealView(fragContainer);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        fragContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        fragContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }

        //Display fragment according to current Activity
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container,
                        CameraConnectionFragment.newInstance(
                                new CameraConnectionFragment.ConnectionCallback() {
                                    @Override
                                    public void onPreviewSizeChosen(final Size size, final int rotation) {
                                        BaseCameraActivity.this.
                                                onPreviewSizeChosen(size, rotation);
                                    }
                                },
                                this,
                                getFragmentLayoutId(),
                                getDesiredPreviewFrameSize()))
                .commit();
    }

    /**
     * DO image processing in Background Thread
     * @param runnable
     */
    protected synchronized void runInBackground(final Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        }
    }

    /**
     * Extract YUB data from pixel plane array
     * @param planes array of pixel planes for this Image.
     * @param yuvBytes extracted YUV data from plane array
     */
    protected void fillBytes(final Plane[] planes, final byte[][] yuvBytes) {
        // Because of the variable row stride it's not possible to know in
        // advance the actual necessary dimensions of the yuv planes.

        for (int i = 0; i < planes.length; ++i) {
            final ByteBuffer buffer = planes[i].getBuffer();

            if (yuvBytes[i] == null) {
                LOGGER.d("Initializing buffer %d at size %d", i, buffer.capacity());
                yuvBytes[i] = new byte[buffer.capacity()];
            }

            buffer.get(yuvBytes[i]);
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public void requestRender() {
        final OverlayView overlay = (OverlayView) findViewById(R.id.debug_overlay);
        if (overlay != null) {
            overlay.postInvalidate();
        }
    }

    public void addCallback(final OverlayView.DrawCallback callback) {
        final OverlayView overlay = (OverlayView) findViewById(R.id.debug_overlay);
        if (overlay != null) {
            overlay.addCallback(callback);
        }
    }

    /**
     * Enable Debugging and display system logs in overlay window
     * @param debug
     */
    public void onSetDebug(final boolean debug) {
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            debug = !debug;
            requestRender();
            onSetDebug(debug);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract void onPreviewSizeChosen(final Size size, final int rotation);

    protected abstract int getFragmentLayoutId();

    protected abstract Size getDesiredPreviewFrameSize();


    @Override
    public synchronized void onStart() {
        LOGGER.d("onStart " + this);
        super.onStart();
    }

    @Override
    public synchronized void onResume() {
        LOGGER.d("onResume " + this);
        super.onResume();

        handlerThread = new HandlerThread("inference");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    @Override
    public synchronized void onPause() {
        LOGGER.d("onPause " + this);

        if (!isFinishing()) {
            LOGGER.d("Requesting finish");
            finish();
        }

        handlerThread.quitSafely();
        try {
            handlerThread.join();
            handlerThread = null;
            handler = null;
        } catch (final InterruptedException e) {
            LOGGER.e(e, "Exception!");
        }

        super.onPause();
    }

    @Override
    public synchronized void onStop() {
        LOGGER.d("onStop " + this);
        super.onStop();
    }

    @Override
    public synchronized void onDestroy() {
        LOGGER.d("onDestroy " + this);
        super.onDestroy();
    }

}
