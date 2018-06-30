package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "contact_person")
    private String contactPerson = "";

    @Column(name = "contact_email")
    private String contactEmail = "";

    @Column(name = "caption")
    private String caption = "";

    @Column(name = "description")
    private String description = "";

    @Column(name = "about")
    private String about = "";

    @Column(name = "rating")
    private Double rating;

    @Column(name = "gender_male")
    private Boolean genderMale = false;

    @Column(name = "gender_female")
    private Boolean genderFemale = false;

    @Column(name = "age_min")
    private Long ageMin = 0L;

    @Column(name = "age_max")
    private Long ageMax = 0L;

    @Column(name = "income_min")
    private Long incomeMin = 0L;

    @Column(name = "income_max")
    private Long incomeMax = 0L;

    @Column(name = "client_count")
    private Long clientCount = 0L;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "companies_regions",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "region_id")}
    )
    List<RegionEntity> regions = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "offer_categories",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    List<CategoryEntity> offerCategories = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "search_categories",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    List<CategoryEntity> searchCategories = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "marketing_channels_companies",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "marketing_channel_id")}
    )
    List<MarketingChannelEntity> marketingChannels = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorites",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "favorite_company_id")}
    )
    List<CompanyEntity> favoritesCompanies = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "photo_id")
    private PhotoEntity photo;

    @JsonProperty(value = "photoId")
    @Column(name = "photo_id", updatable = false, insertable = false)
    private Long fkPhotoId;

    public List<CompanyEntity> getFavoritesCompanies() {
        return favoritesCompanies;
    }

    public void setFavoritesCompanies(List<CompanyEntity> favoritesCompanies) {
        this.favoritesCompanies = favoritesCompanies;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<RegionEntity> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionEntity> regions) {
        this.regions = regions;
    }

    public Boolean getGenderMale() {
        return genderMale;
    }

    public void setGenderMale(Boolean genderMale) {
        this.genderMale = genderMale;
    }

    public Boolean getGenderFemale() {
        return genderFemale;
    }

    public void setGenderFemale(Boolean genderFemale) {
        this.genderFemale = genderFemale;
    }

    public Long getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Long ageMin) {
        this.ageMin = ageMin;
    }

    public Long getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Long ageMax) {
        this.ageMax = ageMax;
    }

    public Long getIncomeMin() {
        return incomeMin;
    }

    public void setIncomeMin(Long incomeMin) {
        this.incomeMin = incomeMin;
    }

    public Long getIncomeMax() {
        return incomeMax;
    }

    public void setIncomeMax(Long incomeMax) {
        this.incomeMax = incomeMax;
    }

    public Long getClientCount() {
        return clientCount;
    }

    public void setClientCount(Long clientCount) {
        this.clientCount = clientCount;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public List<CategoryEntity> getOfferCategories() {
        return offerCategories;
    }

    public void setOfferCategories(List<CategoryEntity> offerCategories) {
        this.offerCategories = offerCategories;
    }

    public List<CategoryEntity> getSearchCategories() {
        return searchCategories;
    }

    public void setSearchCategories(List<CategoryEntity> searchCategories) {
        this.searchCategories = searchCategories;
    }

    public List<MarketingChannelEntity> getMarketingChannels() {
        return marketingChannels;
    }

    public void setMarketingChannels(List<MarketingChannelEntity> marketingChannels) {
        this.marketingChannels = marketingChannels;
    }
}
