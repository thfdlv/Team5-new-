package com.example.jsonExam.community.commentlike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment-like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/toggle")
    public String toggle(@ModelAttribute CommentLikeDTO like, @RequestParam("postId") int postId) {
        commentLikeService.toggleLike(like);
        return "redirect:/post/view?id=" + postId;
    }
}
