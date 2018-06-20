package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.iqdevelop.barterme.models.enums.PartnerStatusEnum;
import ru.iqdevelop.barterme.utils.Helpers;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "partners")
public class PartnerEntity {

    @Id
    @SequenceGenerator(name = "partners_seq", sequenceName = "partners_partners_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partners_seq")
    @Column(name = "partners_id")
    private Long partnersId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private CompanyEntity sender;

    @JsonProperty(value = "senderId")
    @Column(name = "sender_id", insertable = false, updatable = false)
    private Long fkSenderId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private CompanyEntity receiver;

    @JsonProperty(value = "receiverId")
    @Column(name = "receiver_id", insertable = false, updatable = false)
    private Long fkReceiverId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PartnerStatusEnum status;

    @Column(name = "create_date")
    private Timestamp createDate = Helpers.getCurrentTimestamp();

    @Column(name = "last_change_date")
    private Timestamp lastChangeDate = Helpers.getCurrentTimestamp();

    @Column(name = "mark_for_sender")
    private Long markForSender;

    @Column(name = "mark_for_receiver")
    private Long markForReceiver;

    public Long getMarkForSender() {
        return markForSender;
    }

    public void setMarkForSender(Long markForSender) {
        this.markForSender = markForSender;
    }

    public Long getMarkForReceiver() {
        return markForReceiver;
    }

    public void setMarkForReceiver(Long markForReceiver) {
        this.markForReceiver = markForReceiver;
    }

    public Long getPartnersId() {
        return partnersId;
    }

    public void setPartnersId(Long partnersId) {
        this.partnersId = partnersId;
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

    public PartnerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PartnerStatusEnum status) {
        this.status = status;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Timestamp lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }
}
