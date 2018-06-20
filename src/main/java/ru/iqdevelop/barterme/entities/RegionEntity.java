package ru.iqdevelop.barterme.entities;

import javax.persistence.*;

@Entity
@Table(name = "regions")
public class RegionEntity {

    @Id
    @SequenceGenerator(name = "regions_seq", sequenceName = "regions_region_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regions_seq")
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "title")
    private String title;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
