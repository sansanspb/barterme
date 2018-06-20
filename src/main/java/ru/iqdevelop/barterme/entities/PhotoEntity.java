package ru.iqdevelop.barterme.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.nio.file.Paths;

@Entity
@Table(name = "photos")
public class PhotoEntity {

    @Id
    @SequenceGenerator(name = "photos_seq", sequenceName = "photos_photo_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photos_seq")
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "original_path")
    private String originalPath;

    @JsonProperty(value = "photoPath")
    private String getPhotoPath() {
        if (originalPath != null) {
            return Paths.get(originalPath).getFileName().toString();
        } else {
            return "default-photo.jpg";
        }

    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }
}
