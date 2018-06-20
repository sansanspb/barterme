package ru.iqdevelop.barterme.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendReviewRequestModel {

    private Long companyId;

    private String message;

    @JsonProperty(value = "isGood")
    private Boolean isGood;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getGood() {
        return isGood;
    }

    public void setGood(Boolean good) {
        isGood = good;
    }
}
