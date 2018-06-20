package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.iqdevelop.barterme.utils.Helpers;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @SequenceGenerator(name = "reviews_seq", sequenceName = "reviews_review_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_seq")
    @Column(name = "review_id")
    private Long reviewId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @JsonProperty(value = "companyId")
    @Column(name = "company_id", insertable = false, updatable = false)
    private Long fkCompanyId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private CompanyEntity author;

    @JsonProperty(value = "authorId")
    @Column(name = "author_id", insertable = false, updatable = false)
    private Long fkAuthorId;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "message")
    private String message;

    @Column(name = "is_good")
    private Boolean isGood;

    @Column(name = "create_date")
    private Timestamp createDate = Helpers.getCurrentTimestamp();

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public Long getFkCompanyId() {
        return fkCompanyId;
    }

    public void setFkCompanyId(Long fkCompanyId) {
        this.fkCompanyId = fkCompanyId;
    }

    public CompanyEntity getAuthor() {
        return author;
    }

    public void setAuthor(CompanyEntity author) {
        this.author = author;
    }

    public Long getFkAuthorId() {
        return fkAuthorId;
    }

    public void setFkAuthorId(Long fkAuthorId) {
        this.fkAuthorId = fkAuthorId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
