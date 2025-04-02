package com.example.jsonExam.community.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public String add(@ModelAttribute CommentDTO comment, HttpSession session, RedirectAttributes ra) {
        String userId = (String) session.getAttribute("id");

        if (userId == null) {
            ra.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        comment.setAuthorId(userId);      // 실제 사용자 ID
        comment.setAuthor(userId);        // ✔ 작성자 표시도 사용자 ID로 설정
        commentService.addComment(comment);
        return "redirect:/post/view?id=" + comment.getPostId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id, @RequestParam("postId") int postId,
                         HttpSession session, RedirectAttributes ra) {
        String sessionId = (String) session.getAttribute("id");
        CommentDTO comment = commentService.getCommentById(id);

        if (sessionId == null || comment == null || !sessionId.equals(comment.getAuthorId())) {
            ra.addFlashAttribute("error", "본인이 작성한 댓글만 삭제할 수 있습니다.");
            return "redirect:/post/view?id=" + postId;
        }

        commentService.deleteComment(id);
        ra.addFlashAttribute("message", "댓글이 삭제되었습니다.");
        return "redirect:/post/view?id=" + postId;
    }
}