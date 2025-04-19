package com.example.ProjectAPI.DTO.request;

public class RecentSearchRequest {
    private String keyword;
    private Long userId;

    // Constructors
    public RecentSearchRequest() {
    }

    public RecentSearchRequest(String keyword, Long userId) {
        this.keyword = keyword;
        this.userId = userId;
    }

    // Getters and Setters
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
