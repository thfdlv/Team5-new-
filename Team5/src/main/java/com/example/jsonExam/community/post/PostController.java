package com.example.jsonExam.community.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/community")
    public String community(Model model) {
        return list(model);
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<PostDTO> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "community";
    }

    @GetMapping("/view")
    public String view(@RequestParam("id") int id, Model model) {
        PostDTO post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/community";
        }
        model.addAttribute("post", post);
        return "post-page";
    }

    @PostMapping("/create")
    public String create(PostDTO post, HttpSession session) {
        String userId = (String) session.getAttribute("id");
        post.setAuthor(userId); // ✅ 사용자 ID로 저장!
        post.setAuthorId(userId);
        postService.createPost(post);
        return "redirect:/community";
    }


    @PostMapping("/delete")
    public String deletePost(@RequestParam("id") int id, HttpSession session, RedirectAttributes ra) {
        String sessionId = (String) session.getAttribute("id");
        PostDTO post = postService.getPostById(id);
        if (sessionId == null || post == null || !sessionId.equals(post.getAuthorId())) {
            ra.addFlashAttribute("error", "본인이 작성한 글만 삭제할 수 있습니다.");
            return "redirect:/post/list";
        }
        postService.deletePost(id);
        ra.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        return "redirect:/post/list";
    }
}