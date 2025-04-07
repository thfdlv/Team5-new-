package com.example.jsonExam.image;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final String BUCKET_NAME = "s3-team5";

    @GetMapping
    public String showUploadPage() {
        return "image-upload";
    }

    @PostMapping("/upload")
    public String uploadImage(MultipartFile imageFile, Model model) {
        if (imageFile.isEmpty()) {
            return "redirect:/error";
        }

        try {
            // 1. 랜덤 파일 이름 생성
            String fileName = "uploads/" + UUID.randomUUID() + "-" + imageFile.getOriginalFilename();

            // 2. S3 클라이언트
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    .withRegion("ap-northeast-2")
                    .build();

            // 3. 파일 업로드
            InputStream inputStream = imageFile.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageFile.getSize());
            metadata.setContentType(imageFile.getContentType());
            s3.putObject(BUCKET_NAME, fileName, inputStream, metadata);
            System.out.println("✅ S3 업로드 성공: " + fileName);

            // 4. Rekognition 클라이언트 생성
            AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                    .withRegion("ap-northeast-2")
                    .build();

            // 5. Rekognition 요청
            DetectLabelsRequest request = new DetectLabelsRequest()
                    .withImage(new Image().withS3Object(new S3Object().withBucket(BUCKET_NAME).withName(fileName)))
                    .withMaxLabels(5)
                    .withMinConfidence(70F);

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            // 6. 결과 전달
            List<String> descriptions = new ArrayList<>();
            for (Label label : labels) {
                String desc = label.getName() + " (" + String.format("%.1f", label.getConfidence()) + "%)";
                descriptions.add(desc);
                System.out.println("🔍 " + desc);
            }
            model.addAttribute("labels", descriptions);

            // ✅ 이미지 URL도 JSP에 전달
            String imageUrl = "https://" + BUCKET_NAME + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
            model.addAttribute("imageUrl", imageUrl);
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "image-upload"; // 여기서 image-upload.jsp에 결과도 같이 보여줄 수 있어
    }
}
