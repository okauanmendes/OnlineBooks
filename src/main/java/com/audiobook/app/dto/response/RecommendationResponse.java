package com.audiobook.app.dto.response;

public class RecommendationResponse {
    private Long bookId;
    private String reason;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
