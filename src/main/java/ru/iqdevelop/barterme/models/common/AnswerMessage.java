package ru.iqdevelop.barterme.models.common;

public class AnswerMessage {

    private String message;
    private Object data;
    private Boolean success;

    private AnswerMessage() {
    }

    public static AnswerMessage getSuccessMessage() {
        return getSuccessMessage("");
    }

    public static AnswerMessage getSuccessMessage(Object sendData) {
        AnswerMessage result = new AnswerMessage();
        result.setMessage("");
        result.setData(sendData);
        result.setSuccess(true);
        return result;
    }

    public static AnswerMessage getFailMessage(String message) {
        AnswerMessage result = new AnswerMessage();
        result.setMessage(message);
        result.setData(null);
        result.setSuccess(false);
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
