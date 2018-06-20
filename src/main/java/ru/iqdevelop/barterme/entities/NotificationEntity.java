package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.iqdevelop.barterme.models.enums.NotificationStatusEnum;
import ru.iqdevelop.barterme.utils.Helpers;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @SequenceGenerator(name = "notifications_seq", sequenceName = "notifications_notification_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_seq")
    @Column(name = "notification_id")
    private Long notificationId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message_test")
    private String messageTest;

    @Column(name = "create_date")
    private Timestamp createDate = Helpers.getCurrentTimestamp();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatusEnum status;

    @JsonProperty(value = "receiverId")
    @Column(name = "receiver_id", updatable = false, insertable = false)
    private Long fkReceiverId;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessageTest() {
        return messageTest;
    }

    public void setMessageTest(String messageTest) {
        this.messageTest = messageTest;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public NotificationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(NotificationStatusEnum status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public Long getFkReceiverId() {
        return fkReceiverId;
    }

    public void setFkReceiverId(Long fkReceiverId) {
        this.fkReceiverId = fkReceiverId;
    }
}
