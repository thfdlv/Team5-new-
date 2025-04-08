package com.example.jsonExam.community.commentlike;

public class CommentLikeDTO {
    private int id;
    private int commentId;
    private String ip;

    // 기본 생성자
    public CommentLikeDTO() {}

    // 생성자 (선택적으로 사용할 수 있음)
    public CommentLikeDTO(int commentId, String ip) {
        this.commentId = commentId;
        this.ip = ip;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCommentId() { return commentId; }
    public void setCommentId(int commentId) { this.commentId = commentId; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    @Override
    public String toString() {
        return "CommentLikeDTO{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", ip='" + ip + '\'' +
                '}';
    }
}
