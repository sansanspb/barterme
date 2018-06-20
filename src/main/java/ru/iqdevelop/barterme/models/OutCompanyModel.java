package ru.iqdevelop.barterme.models;

public class OutCompanyModel {
    private Long companyId;
    private String contactPerson;
    private String contactEmail;
    private String caption;
    private String description;
    private String about;
    private Double rating;
    private Long regionId;
    private Boolean genderMale;
    private Boolean genderFemale;
    private Long ageMin;
    private Long ageMax;
    private Long incomeMin;
    private Long incomeMax;
    private Long clientCount;
    private Long photoId;
    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
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

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
