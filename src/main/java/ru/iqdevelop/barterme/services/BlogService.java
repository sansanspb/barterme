package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.BlogEntity;
import ru.iqdevelop.barterme.entities.PhotoEntity;
import ru.iqdevelop.barterme.models.blogs.BlogModel;
import ru.iqdevelop.barterme.models.blogs.BlogModelView;
import ru.iqdevelop.barterme.models.blogs.BlogPhotoUpdateModel;
import ru.iqdevelop.barterme.repositories.BlogRepository;
import ru.iqdevelop.barterme.repositories.PhotoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Transactional
    public BlogEntity save(BlogModel blogModel) {
        if (blogModel.getBlogId() == null) {
            return insertBlog(blogModel);
        } else {
            return updateBlog(blogModel);
        }
    }

    private BlogEntity updateBlog(BlogModel blogModel) {
        BlogEntity entity = blogRepository.getById(blogModel.getBlogId());
        entity.setTitle(blogModel.getTitle());
        entity.setBody(blogModel.getBody());

        blogRepository.update(entity);
        return entity;
    }

    public BlogEntity updateBlogPhoto(BlogPhotoUpdateModel blogPhotoUpdateModel) {
        BlogEntity entity = blogRepository.getById(blogPhotoUpdateModel.getBlogId());
        PhotoEntity photo = photoRepository.getById(blogPhotoUpdateModel.getPhotoId());
        entity.setPhoto(photo);

        blogRepository.update(entity);
        return entity;
    }

    private BlogEntity insertBlog(BlogModel blogModel) {
        BlogEntity entity = new BlogEntity();
        entity.setBody(blogModel.getBody());
        entity.setTitle(blogModel.getTitle());

        blogRepository.insert(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public BlogModelView getModelById(Long blogId) {
        BlogEntity blogEntity = blogRepository.getById(blogId);
        return new BlogModelView(blogEntity);
    }

    @Transactional(readOnly = true)
    public List<BlogModelView> getAllModels() {
        return blogRepository.getAll().stream().map(BlogModelView::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long blogId) {
        blogRepository.delete(blogId);
    }
}
