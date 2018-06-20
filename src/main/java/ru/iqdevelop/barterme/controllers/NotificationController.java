package ru.iqdevelop.barterme.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.iqdevelop.barterme.models.Message;
import ru.iqdevelop.barterme.models.OutputMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NotificationController {


    @MessageMapping("/notification")
    @SendTo("/topic/messages")
    public OutputMessage send(final Message message) throws Exception {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }


//    private void sendUpdateSignal() {
//        OutputMessage msg = new OutputMessage("System", "Need update", new SimpleDateFormat("HH:mm").format(Helpers.getCurrentTimestamp()));
//        messagingTemplate.convertAndSend("/topic/messages", msg);
//    }
}
