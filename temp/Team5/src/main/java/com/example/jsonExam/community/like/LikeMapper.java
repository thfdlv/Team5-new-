package com.example.jsonExam.community.like;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    void likePost(LikeDTO like);
    void unlikePost(LikeDTO like);
    int getLikeCount(int postId);

    // 추가: 해당 사용자가 이미 좋아요를 눌렀는지 여부 확인
    boolean exists(LikeDTO like);
}
