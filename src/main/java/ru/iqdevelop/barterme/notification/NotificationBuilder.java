package ru.iqdevelop.barterme.notification;

import ru.iqdevelop.barterme.entities.NotificationEntity;
import ru.iqdevelop.barterme.entities.UserEntity;
import ru.iqdevelop.barterme.models.enums.NotificationStatusEnum;


public class NotificationBuilder {

    private NotificationBuilder() {
    }

    public static NotificationEntity buildConfirmCodeNotification(String confirmCode, UserEntity receiver) {
        return buildNotification(receiver, "Код подтверждения Barter.Me", String.format("Ваш код для подтверждения почтового адреса на Barter.Me: %s", confirmCode));
    }

    public static NotificationEntity buildSendedPartnerNotification(String companyName, UserEntity notificationReceiver) {
        return buildNotification(notificationReceiver, "Партнерство на Barter.Me", String.format("Вам прислали заявку на партнерство на Barter.Me: '%s'", companyName));
    }

    public static NotificationEntity buildReceivedPartnerNotification(String companyName, UserEntity notificationReceiver) {
        return buildNotification(notificationReceiver, "Партнерство на Barter.Me", String.format("Вашу заявку на партнерство приняли: '%s'", companyName));
    }

    public static NotificationEntity buildRejectedPartnerNotification(String companyName, UserEntity notificationReceiver) {
        return buildNotification(notificationReceiver, "Партнерство на Barter.Me", String.format("Вашу заявку на партнерство отклонили: '%s'", companyName));
    }

    private static NotificationEntity buildNotification(UserEntity receiver, String subject, String format) {
        NotificationEntity newNot = new NotificationEntity();

        newNot.setSubject(subject);
        newNot.setMessageTest(format);
        newNot.setReceiver(receiver);
        newNot.setStatus(NotificationStatusEnum.WAIT);
        return newNot;
    }
}
