package com.audiobook.app.dto.request;

public class UpdateProgressRequest {
    private Long bookId;
    private Integer progress;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
