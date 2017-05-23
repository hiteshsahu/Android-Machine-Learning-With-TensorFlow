package com.hiteshsahu.tensorflow_android;

/**
 * Created by Hitesh.Sahu on 5/20/2017.
 *
 * ADD YOUR TENSOR MODEL LOCATION AND HERE
 */

public interface AppConfig {

    int IMAGE_MEAN = 117;
    int INPUT_SIZE = 224;

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
    String OBJECT_CLASSIFICATION_MODEL_FILE =
            "file:///android_asset/tensorflow_inception_graph.pb";

    String LABEL_OBJECT_CLASSIFICATION =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    // These are the settings for MULTIBOX PERSON DETECTION model
    String MB_PERSON_LOCATION_DETECTION_MODEL_FILE =
            "file:///android_asset/multibox_model.pb";

    String LABEL_MB_PERSON_LOCATION_DETECTION =
            "file:///android_asset/multibox_location_priors.txt";

    // These are the settings for STYLING FRAMES model
    String STYLING_MODEL_FILE = "file:///android_asset/stylize_quantized.pb";

    // Configuration values for tiny-yolo-voc. Note that the graph is not included with TensorFlow and
    // must be manually placed in the assets/ directory by the user.
    // Graphs and models downloaded from http://pjreddie.com/darknet/yolo/ may be converted e.g. via
    // DarkFlow (https://github.com/thtrieu/darkflow). Sample command:
    // ./flow --model cfg/tiny-yolo-voc.cfg --load bin/tiny-yolo-voc.weights --savepb --verbalise=True
    String YOLO_MODEL_FILE = "file:///android_asset/graph-tiny-yolo-voc.pb";

}
