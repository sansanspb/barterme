package ru.iqdevelop.barterme.models.categories;

import ru.iqdevelop.barterme.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyCategoriesModel {

    private List<Long> offerCategories = new ArrayList<>();

    private List<Long> searchCategories = new ArrayList<>();

    public List<Long> getOfferCategories() {
        return offerCategories;
    }

    public void setOfferCategories(List<Long> offerCategories) {
        this.offerCategories = offerCategories;
    }

    public List<Long> getSearchCategories() {
        return searchCategories;
    }

    public void setSearchCategories(List<Long> searchCategories) {
        this.searchCategories = searchCategories;
    }

    public void parseAndSetOfferCategories(List<CategoryEntity> offerCategories) {
        if (offerCategories != null) {
            this.offerCategories = offerCategories.stream().map(CategoryEntity::getCategoryId).collect(Collectors.toList());
        }
    }

    public void parseAndSetSearchCategories(List<CategoryEntity> searchCategories) {
        if (offerCategories != null) {
            this.searchCategories = searchCategories.stream().map(CategoryEntity::getCategoryId).collect(Collectors.toList());
        }
    }
}
