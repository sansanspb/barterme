package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.models.blogs.BlogModelView;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.BlogService;

import java.util.List;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
    @Autowired
    BlogService blogService;

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
}
