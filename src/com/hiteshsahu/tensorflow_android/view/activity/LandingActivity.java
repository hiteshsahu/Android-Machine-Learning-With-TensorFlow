package com.hiteshsahu.tensorflow_android.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Toast;

import com.hiteshsahu.tensorflow_android.utils.Animatrix;

import org.tensorflow.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    private long lastBackPressTime;
    @BindView(R.id.appRoot)
    View appRoot;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        //Animate Circular Reveal on APp Start
        appRoot.setVisibility(View.INVISIBLE);
        ViewTreeObserver viewTreeObserver = appRoot.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Animatrix.circularRevealView(appRoot);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        appRoot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        appRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }

        setSupportActionBar(toolbar);

        findViewById(R.id.classification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(new Intent(LandingActivity.this, ClassifierActivityBase.class));

            }
        });


        findViewById(R.id.detection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(new Intent(LandingActivity.this, DetectorActivityBase.class));

            }
        });


        findViewById(R.id.styling).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(new Intent(LandingActivity.this, StylizeActivityBase.class));

            }
        });

//        findViewById(R.id.view_webform).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent myIntent = new Intent(LandingActivity.this, WebViewActivity.class);
////                myIntent.putExtra(AppConstants.X_POSITION, openWebForm.getLeft() + openWebForm.getWidth() / 2);
////                myIntent.putExtra(AppConstants.Y_POSITION, openWebForm.getBottom() + openWebForm.getHeight() / 2);
//
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////
////                    Pair<View, String> pair1 = create(openWebForm, openWebForm.getTransitionName());
////                    ActivityOptionsCompat options = ActivityOptionsCompat.
////                            makeSceneTransitionAnimation(LandingActivity.this, pair1);
////                    startActivity(myIntent, options.toBundle());
////                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
////
////                } else {
//                   overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    startActivity(myIntent);
////                }
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() >= 2) {
            getFragmentManager().popBackStack();
        } else {
            if (lastBackPressTime + 2000 > System.currentTimeMillis()) {
                animateExitApplication();
            } else {
                Toast.makeText(getBaseContext(), "Please click BACK again to exit",
                        Toast.LENGTH_SHORT).show();
            }
        }
        lastBackPressTime = System.currentTimeMillis();
    }

    private void animateExitApplication() {

        //Circular exit Animation
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Animator anim = Animatrix.exitReveal(appRoot);

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    supportFinishAfterTransition();
                }
            });

            anim.start();
        } else {
            finish();
        }
    }

}
