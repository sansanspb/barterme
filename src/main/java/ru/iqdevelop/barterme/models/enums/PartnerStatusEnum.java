package ru.iqdevelop.barterme.models.enums;

public enum PartnerStatusEnum {
    WAIT(0, "WAIT"), ACTIVE(1, "ACTIVE"), COMPLETE(2, "COMPLETE"), REJECTED(3, "REJECTED");

    private int id;
    private String code;

    private PartnerStatusEnum(int id, String code) {
        this.id = id;
        this.code = code;
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

    public static PartnerStatusEnum getById(Integer id) {
        for (PartnerStatusEnum status : values()) {
            if (status.getId() == id)
                return status;
        }
        return null;
    }

    public static PartnerStatusEnum getByCode(String code) {
        for (PartnerStatusEnum status : values()) {
            if (status.getCode().equals(code))
                return status;
        }
        return null;
    }
}
