package com.example.jsonExam.community.like;

public class LikeDTO {
    private int id;
    private int postId;
    private String ip;  // 사용자 IP로 중복 방지

    // 생성자 (선택)
    public LikeDTO() {}

    public LikeDTO(int postId, String ip) {
        this.postId = postId;
        this.ip = ip;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
