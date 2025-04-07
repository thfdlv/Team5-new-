package com.example.jsonExam.community.post;

import com.example.jsonExam.community.comment.CommentDTO;
import com.example.jsonExam.community.comment.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {

    @Autowired private PostMapper postMapper;

    @Autowired private CommentMapper commentMapper; // ✅ 댓글 매퍼 주입

    public List<PostDTO> getAllPosts() {
        return postMapper.getAllPosts();
    }

    public PostDTO getPostById(int id) {
        PostDTO post = postMapper.getPostById(id);
        if (post != null) {
            // ✅ 댓글 리스트를 불러와서 설정
            List<CommentDTO> comments = commentMapper.getCommentsByPostId(id);
            post.setComments(comments);
        }
        return post;
    }

    public void createPost(PostDTO post) {
        postMapper.createPost(post);
    }

    public void updatePost(PostDTO post) {
        postMapper.updatePost(post);
    }

    public void deletePost(int id) {
        postMapper.deletePost(id);
    }
}
