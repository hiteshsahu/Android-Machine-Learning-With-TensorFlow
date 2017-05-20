package com.hiteshsahu.tensorflow_android.utils;

/**
 * Created by Hitesh.Sahu on 5/20/2017.
 */

public interface AppConfig {

    int INPUT_SIZE = 224;
    int IMAGE_MEAN = 117;

    // These are the settings for the original v1 Inception model. If you want to
    // use a model that's been produced from the TensorFlow for Poets codelab,
    // you'll need to set IMAGE_SIZE = 299, IMAGE_MEAN = 128, IMAGE_STD = 128,
    // INPUT_NAME = "Mul", and OUTPUT_NAME = "final_result".
    // You'll also need to update the MODEL_FILE and LABEL_FILE paths to point to
    // the ones you produced.
    //
    // To use v3 Inception model, strip the DecodeJpeg Op from your retrained
    // model first:
    //
    String MODEL_OBJECT_CLASSIFICATION =
            "file:///android_asset/tensorflow_inception_graph.pb";

    String LABEL_OBJECT_CLASSIFICATION =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    // These are the settings for multibox person detection model
    String MODEL_MB_LOCATION_DETECTION =
            "file:///android_asset/multibox_model.pb";

    String LABEL_MB_LOCATION_DETECTION =
            "file:///android_asset/multibox_location_priors.txt";

    String MODEL_STYLING = "file:///android_asset/stylize_quantized.pb";
}
