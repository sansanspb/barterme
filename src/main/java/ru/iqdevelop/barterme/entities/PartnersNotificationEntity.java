package ru.iqdevelop.barterme.entities;

import ru.iqdevelop.barterme.models.enums.PartnerStatusEnum;
import ru.iqdevelop.barterme.utils.Helpers;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "partners_notifications")
public class PartnersNotificationEntity {

    @Id
    @SequenceGenerator(name = "partners_notifications_seq", sequenceName = "partners_notifications_partners_notification_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partners_notifications_seq")
    @Column(name = "partners_notification_id")
    private Long partnersNotificationId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PartnerStatusEnum status;

    @Column(name = "date_create")
    private Timestamp dateCreate = Helpers.getCurrentTimestamp();

    @Column(name = "is_readed")
    private Boolean isReaded = false;

    public Long getPartnersNotificationId() {
        return partnersNotificationId;
    }

    public void setPartnersNotificationId(Long partnersNotificationId) {
        this.partnersNotificationId = partnersNotificationId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public PartnerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PartnerStatusEnum status) {
        this.status = status;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Boolean getReaded() {
        return isReaded;
    }

    public void setReaded(Boolean readed) {
        isReaded = readed;
    }
}
