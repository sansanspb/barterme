package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @SequenceGenerator(name = "categories_seq", sequenceName = "categories_category_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_seq")
    @Column(name = "category_id")
    private Long categoryId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @JsonProperty(value = "parentId")
    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long fkParentId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "parent")
    private List<CategoryEntity> childs;

    @Column(name = "title")
    private String title;

    @JsonIgnore
    @ManyToMany(mappedBy = "offerCategories")
    private List<CompanyEntity> offeredCompanies = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "searchCategories")
    private List<CompanyEntity> searchedCompanies = new ArrayList<>();

    public List<CompanyEntity> getOfferedCompanies() {
        return offeredCompanies;
    }

    public void setOfferedCompanies(List<CompanyEntity> offeredCompanies) {
        this.offeredCompanies = offeredCompanies;
    }

    public List<CompanyEntity> getSearchedCompanies() {
        return searchedCompanies;
    }

    public void setSearchedCompanies(List<CompanyEntity> searchedCompanies) {
        this.searchedCompanies = searchedCompanies;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }

    public Long getFkParentId() {
        return fkParentId;
    }

    public void setFkParentId(Long fkParentId) {
        this.fkParentId = fkParentId;
    }

    public List<CategoryEntity> getChilds() {
        return childs;
    }

    public void setChilds(List<CategoryEntity> childs) {
        this.childs = childs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
