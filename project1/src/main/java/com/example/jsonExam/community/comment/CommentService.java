package com.example.jsonExam.community.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<CommentDTO> getCommentsByPostId(int postId) {
        return commentMapper.getCommentsByPostId(postId);
    }

    public void addComment(CommentDTO comment) {
        commentMapper.addComment(comment);
    }

    public void deleteComment(int id) {
        commentMapper.deleteComment(id);
    }

    // ✅ 댓글 ID로 단일 댓글 조회 (삭제 권한 확인용)
    public CommentDTO getCommentById(int id) {
        return commentMapper.getCommentById(id);
    }
}
