package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.entities.MarketingChannelEntity;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.MarketingChannelsService;

import java.util.List;

@Controller
@RequestMapping("/marketingChannels")
public class MarketingChannelsController {

    private static final Logger logger = LoggerFactory.getLogger(MarketingChannelsController.class);

    @Autowired
    MarketingChannelsService marketingChannelsService;

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getAll() {
        try {
            List<MarketingChannelEntity> models = marketingChannelsService.getAll();
            return AnswerMessage.getSuccessMessage(models);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить каналы");
        }
    }

}
