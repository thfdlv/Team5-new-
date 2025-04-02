package com.example.jsonExam.community.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeMapper likeMapper;

    // 좋아요 추가
    public void likePost(LikeDTO like) {
        likeMapper.likePost(like);
    }

    // 좋아요 취소
    public void unlikePost(LikeDTO like) {
        likeMapper.unlikePost(like);
    }

    // 좋아요 개수 조회
    public int getLikeCount(int postId) {
        return likeMapper.getLikeCount(postId);
    }

    // 사용자가 이미 좋아요 했는지 확인
    public boolean hasLiked(LikeDTO like) {
        return likeMapper.exists(like);
    }
}
