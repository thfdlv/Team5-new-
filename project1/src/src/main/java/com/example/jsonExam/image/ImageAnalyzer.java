package com.example.jsonExam.image;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;

import java.util.List;

public class ImageAnalyzer {

    public static List<Label> analyzeImage(String bucket, String key) throws Exception {
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                .withRegion("ap-northeast-2")
                .build();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image().withS3Object(new S3Object().withBucket(bucket).withName(key)))
                .withMaxLabels(5)
                .withMinConfidence(70F);

        DetectLabelsResult result = rekognitionClient.detectLabels(request);
        return result.getLabels(); // 결과 반환
    }
}
