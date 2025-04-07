package com.example.jsonExam.community.commentlike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    public void toggleLike(CommentLikeDTO like) {
        if (commentLikeMapper.exists(like)) {
            commentLikeMapper.unlikeComment(like);
        } else {
            commentLikeMapper.likeComment(like);
        }
    }

    public int getLikeCount(int commentId) {
        return commentLikeMapper.getLikeCount(commentId);
    }

    // ✅ 선택적으로 공개해두면 좋음 (예: 프론트에 하트 색깔 표시 용도 등)
    public boolean hasLiked(CommentLikeDTO like) {
        return commentLikeMapper.exists(like);
    }
}
