package ru.iqdevelop.barterme.models.enums;

public enum NotificationStatusEnum {
    WAIT(0, "WAIT"), SENDED(1, "SENDED"), ERROR(2, "ERROR");

    private int id;
    private String code;

    NotificationStatusEnum(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public static NotificationStatusEnum getById(Integer id) {
        for (NotificationStatusEnum status : values()) {
            if (status.getId() == id)
                return status;
        }

        return null;
    }

    public static NotificationStatusEnum getByCode(String code) {
        for (NotificationStatusEnum status : values()) {
            if (status.getCode().equals(code))
                return status;
        }

        return null;
    }

    public boolean isEqual(int id) {
        return this.getId() == id;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }
}