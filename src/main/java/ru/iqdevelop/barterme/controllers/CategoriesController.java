package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.entities.CategoryEntity;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.CategoriesService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @Autowired
    private CategoriesService categoriesService;

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getAll() {
        try {
            List<CategoryEntity> result = categoriesService.getAll();
            return AnswerMessage.getSuccessMessage(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить категории");
        }
    }
}
