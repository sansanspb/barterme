package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.models.SearchOrderModel;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.SearchOrderService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/searchOrder")
public class SearchOrderController {

    private static final Logger logger = LoggerFactory.getLogger(SearchOrderController.class);

    @Autowired
    SearchOrderService searchOrderService;

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public @ResponseBody
    AnswerMessage save(@RequestBody SearchOrderModel searchOrderModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            String userEmail = null;
            if (userPrincipal != null) {
                userEmail = userPrincipal.getName();
            }

            searchOrderService.save(userEmail, searchOrderModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось сохранить запрос");
        }
    }

}
