package ru.iqdevelop.barterme.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.controllers.BlogController;
import ru.iqdevelop.barterme.entities.BlogEntity;
import ru.iqdevelop.barterme.models.blogs.BlogModel;
import ru.iqdevelop.barterme.models.blogs.BlogModelView;
import ru.iqdevelop.barterme.models.blogs.BlogPhotoUpdateModel;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.BlogService;

import java.util.List;

@Controller
@RequestMapping("/admin/blogs")
public class ABlogController {

    @Autowired
    BlogService blogService;

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage save(@RequestBody BlogModel blogModel) {
        try {
            BlogEntity blog = blogService.save(blogModel);
            BlogModelView model = blogService.getModelById(blog.getBlogId());
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось сохранить блог");
        }
    }

    @RequestMapping(value = "/updateBlogPhoto", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage updateBlogPhoto(@RequestBody BlogPhotoUpdateModel blogPhotoUpdateModel) {
        try {
            BlogEntity blog = blogService.updateBlogPhoto(blogPhotoUpdateModel);
            BlogModelView model = blogService.getModelById(blog.getBlogId());
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось сохранить блог");
        }
    }


    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getAll() {
        try {
            List<BlogModelView> models = blogService.getAllModels();
            return AnswerMessage.getSuccessMessage(models);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить блоги");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage save(@RequestBody Long blogId) {
        try {
            blogService.delete(blogId);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось сохранить блог");
        }
    }

}
