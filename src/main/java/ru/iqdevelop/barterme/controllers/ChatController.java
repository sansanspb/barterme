package ru.iqdevelop.barterme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.ChatService;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @RequestMapping(value = "/getMsg", method = RequestMethod.GET)
    public @ResponseBody
    AnswerMessage getMessages(@RequestParam(name = "senderId") Long senderId, @RequestParam(name = "receiverId") Long receiverId,
                              @RequestParam(name = "stage") Integer stage) {
        try {
            return AnswerMessage.getSuccessMessage(chatService.getMessages(senderId, receiverId, stage));
        } catch (Exception e) {
            return AnswerMessage.getFailMessage("Не удалось обновить сообщения");
        }
    }

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage sendMessage(@RequestParam(name = "senderId") Long senderId, @RequestParam(name = "receiverId") Long receiverId,
                              @RequestParam(name = "message") String message) {
        chatService.sendMessage(senderId, receiverId, message);

        return AnswerMessage.getSuccessMessage();
    }

    /**
     * Возвращает id пользователей, с которыми у данного пользователя
     * есть чат
     *
     * @param email email текущего пользователя
     * @return список id пользователей
     */
    @RequestMapping(value = "/getChats", method = RequestMethod.GET)
    public @ResponseBody
    AnswerMessage getChats(@RequestParam(name = "email") String email) {
        try {
            return AnswerMessage.getSuccessMessage(chatService.getChats(email));
        } catch (Exception e) {
            return AnswerMessage.getFailMessage("Не удалось получить чаты");
        }
    }
}
