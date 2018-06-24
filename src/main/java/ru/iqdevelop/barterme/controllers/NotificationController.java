package ru.iqdevelop.barterme.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.iqdevelop.barterme.models.NotificationInputMessage;
import ru.iqdevelop.barterme.models.NotificationOutputMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NotificationController {


    @MessageMapping("/notification")
    @SendTo("/topic/messages")
    public NotificationOutputMessage send(final NotificationInputMessage notificationInputMessage) {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new NotificationOutputMessage(notificationInputMessage.getFrom(), notificationInputMessage.getText(), time);
    }


//    private void sendUpdateSignal() {
//        NotificationOutputMessage msg = new NotificationOutputMessage("System", "Need update", new SimpleDateFormat("HH:mm").format(Helpers.getCurrentTimestamp()));
//        messagingTemplate.convertAndSend("/topic/messages", msg);
//    }
}
