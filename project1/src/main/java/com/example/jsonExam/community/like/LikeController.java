package com.example.jsonExam.community.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle")
    public String toggle(@ModelAttribute LikeDTO like, @RequestHeader("Referer") String referer) {
        boolean liked = likeService.hasLiked(like);

        if (liked) {
            likeService.unlikePost(like);
        } else {
            likeService.likePost(like);
        }

        // 💡 사용자가 있던 페이지로 리다이렉트
        return "redirect:" + referer;
    }
}
