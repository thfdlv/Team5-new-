package com.example.jsonExam.community.commentlike;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeMapper {
    void likeComment(CommentLikeDTO like);
    void unlikeComment(CommentLikeDTO like);
    boolean exists(CommentLikeDTO like);
    int getLikeCount(int commentId);
}
