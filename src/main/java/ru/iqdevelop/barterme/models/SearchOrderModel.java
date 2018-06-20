package ru.iqdevelop.barterme.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class SearchOrderModel {
    private String search;
    private String marketingTask;
    private String region;
    private String offer;
    private String contacts;
    private String categories;

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
}
