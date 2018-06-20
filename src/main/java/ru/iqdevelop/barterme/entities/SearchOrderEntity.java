package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.iqdevelop.barterme.utils.Helpers;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "search_orders")
public class SearchOrderEntity {

    @Id
    @SequenceGenerator(name = "search_orders_seq", sequenceName = "search_orders_search_order_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_orders_seq")
    @Column(name = "search_order_id")
    private Long searchOrderId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @JsonProperty(value = "companyId")
    @Column(name = "company_id", insertable = false, updatable = false)
    private Long fkCompanyId;

    @Column(name = "search")
    private String search;

    @Column(name = "marketing_task")
    private String marketingTask;

    @Column(name = "region")
    private String region;

    @Column(name = "offer")
    private String offer;

    @Column(name = "deadline")
    private Timestamp deadline;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "categories")
    private String categories;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "photo_id")
    private PhotoEntity photo;

    @JsonProperty(value = "photoId")
    @Column(name = "photo_id", insertable = false, updatable = false)
    private Long fkPhotoId;

    @Column(name = "order_date")
    private Timestamp orderDate = Helpers.getCurrentTimestamp();

    public Long getSearchOrderId() {
        return searchOrderId;
    }

    public void setSearchOrderId(Long searchOrderId) {
        this.searchOrderId = searchOrderId;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getMarketingTask() {
        return marketingTask;
    }

    public void setMarketingTask(String marketingTask) {
        this.marketingTask = marketingTask;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public PhotoEntity getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoEntity photo) {
        this.photo = photo;
    }

    public Long getFkPhotoId() {
        return fkPhotoId;
    }

    public void setFkPhotoId(Long fkPhotoId) {
        this.fkPhotoId = fkPhotoId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
}
