package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.entities.ReviewEntity;
import ru.iqdevelop.barterme.models.CompanyReviewsRequestModel;
import ru.iqdevelop.barterme.models.SendReviewRequestModel;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.ReviewService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/getCompanyReviews", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getCompanyReviews(@RequestBody CompanyReviewsRequestModel model, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            List<ReviewEntity> reviews = reviewService.getCompanyReviews(model.getCompanyId());
            return AnswerMessage.getSuccessMessage(reviews);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные");
        }
    }

    @RequestMapping(value = "/sendReview", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getCompanyReviews(@RequestBody SendReviewRequestModel model, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            reviewService.createReview(userPrincipal.getName(), model);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные");
        }
    }
}
