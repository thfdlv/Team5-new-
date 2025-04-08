package com.example.jsonExam.image;

import com.amazonaws.services.rekognition.*;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final String BUCKET_NAME = "s3-team5";

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String showUploadPage() {
        return "image-upload";
    }

    @PostMapping("/labels")
    public String analyzeLabels(MultipartFile imageFile, Model model, Locale locale) {
        if (imageFile.isEmpty()) {
            String errorMsg = messageSource.getMessage("error.empty", null, locale);
            model.addAttribute("error", errorMsg);
            return "image-upload";
        }

        try {
            String fileName = uploadToS3(imageFile);

            AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                    .withRegion("ap-northeast-2")
                    .build();

            DetectLabelsRequest request = new DetectLabelsRequest()
                    .withImage(new Image().withS3Object(new S3Object().withBucket(BUCKET_NAME).withName(fileName)))
                    .withMaxLabels(5)
                    .withMinConfidence(70F);

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            List<String> descriptions = new ArrayList<>();
            for (Label label : labels) {
                String format = messageSource.getMessage("label.format", null, locale);
                String desc = format
                        .replace("{0}", label.getName())
                        .replace("{1}", String.format("%.1f", label.getConfidence()));
                descriptions.add(desc);
            }
            model.addAttribute("labels", descriptions);

            String imageUrl = "https://" + BUCKET_NAME + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
            model.addAttribute("imageUrl", imageUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "image-upload";
    }

    @PostMapping("/faces")
    public String detectFaces(MultipartFile imageFile, Model model, Locale locale) {
        if (imageFile.isEmpty()) {
            String errorMsg = messageSource.getMessage("error.empty", null, locale);
            model.addAttribute("error", errorMsg);
            return "image-upload";
        }

        try {
            String fileName = uploadToS3(imageFile);

            AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                    .withRegion("ap-northeast-2")
                    .build();

            DetectFacesRequest request = new DetectFacesRequest()
                    .withImage(new Image().withS3Object(new S3Object().withBucket(BUCKET_NAME).withName(fileName)))
                    .withAttributes(Attribute.ALL);

            DetectFacesResult result = rekognitionClient.detectFaces(request);
            List<FaceDetail> faceDetails = result.getFaceDetails();

            List<String> faceDescriptions = new ArrayList<>();
            for (FaceDetail face : faceDetails) {
                String emotion = face.getEmotions().stream()
                        .max(Comparator.comparing(Emotion::getConfidence))
                        .map(e -> e.getType() + " (" + String.format("%.1f", e.getConfidence()) + "%)")
                        .orElse(messageSource.getMessage("face.no.emotion", null, locale));
                String prefix = messageSource.getMessage("face.prefix", null, locale);
                faceDescriptions.add(prefix + emotion);
            }

            model.addAttribute("faces", faceDescriptions);
            String imageUrl = "https://" + BUCKET_NAME + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
            model.addAttribute("imageUrl", imageUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "image-upload";
    }

    private String uploadToS3(MultipartFile imageFile) throws Exception {
        String fileName = "uploads/" + UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("ap-northeast-2").build();
        InputStream inputStream = imageFile.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageFile.getSize());
        metadata.setContentType(imageFile.getContentType());
        s3.putObject(BUCKET_NAME, fileName, inputStream, metadata);
        return fileName;
    }
}
