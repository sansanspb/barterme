package ru.iqdevelop.barterme.models.blogs;

import ru.iqdevelop.barterme.entities.BlogEntity;

import java.io.File;
import java.sql.Timestamp;

public class BlogModelView {
    private Long blogId;
    private String title;
    private String body;
    private Timestamp createDate;
    private String photo;
    private Long photoId;


    public BlogModelView(BlogEntity blogEntity) {
        setBlogId(blogEntity.getBlogId());
        setBody(blogEntity.getBody());
        setCreateDate(blogEntity.getCreateDate());

        File file = new File(blogEntity.getPhoto().getOriginalPath());
        setPhoto(file.getName());
        setPhotoId(blogEntity.getPhoto().getPhotoId());
        setTitle(blogEntity.getTitle());
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }
}
