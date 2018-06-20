package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_messages")
public class ChatMessageEntity {

    @Id
    @SequenceGenerator(name = "chat_messages_seq", sequenceName = "chat_messages_chat_history_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_messages_seq")
    @Column(name = "chat_history_id")
    private Long chatHistoryId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private CompanyEntity sender;

    @JsonProperty(value = "senderId")
    @Column(name = "sender_id", insertable = false, updatable = false)
    private Long fkSenderId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private CompanyEntity receiver;

    @JsonProperty(value = "receiverId")
    @Column(name = "receiver_id", insertable = false, updatable = false)
    private Long fkReceiverId;

    @Column(name = "message")
    private String message;

    @Column(name = "send_date")
    private Timestamp sendDate;

    public Long getChatHistoryId() {
        return chatHistoryId;
    }

    public void setChatHistoryId(Long chatHistoryId) {
        this.chatHistoryId = chatHistoryId;
    }

    public CompanyEntity getSender() {
        return sender;
    }

    public void setSender(CompanyEntity sender) {
        this.sender = sender;
    }

    public Long getFkSenderId() {
        return fkSenderId;
    }

    public void setFkSenderId(Long fkSenderId) {
        this.fkSenderId = fkSenderId;
    }

    public CompanyEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(CompanyEntity receiver) {
        this.receiver = receiver;
    }

    public Long getFkReceiverId() {
        return fkReceiverId;
    }

    public void setFkReceiverId(Long fkReceiverId) {
        this.fkReceiverId = fkReceiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }
}
