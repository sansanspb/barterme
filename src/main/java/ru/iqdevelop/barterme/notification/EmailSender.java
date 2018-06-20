package ru.iqdevelop.barterme.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.NotificationEntity;
import ru.iqdevelop.barterme.models.enums.NotificationStatusEnum;
import ru.iqdevelop.barterme.repositories.NotificationRepository;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static final int REPEAT_DELAY = 60 * 1000;

    private ReentrantLock locker = new ReentrantLock();

    @Autowired
    NotificationRepository notificationDao;

    @Autowired
    MailSender mailSender;

    @Autowired
    private Environment environment;

    @Scheduled(fixedDelay = REPEAT_DELAY)
    @Transactional
    public void scanAndSend() {
        logger.debug("scanAndSend START");
        locker.lock();
        try {
            List<NotificationEntity> waitingNots = notificationDao.getWaited();
            for (NotificationEntity currNot : waitingNots) {
                if (sendEmail(currNot.getSubject(), currNot.getMessageTest(), currNot.getReceiver().getEmail())) {
                    currNot.setStatus(NotificationStatusEnum.SENDED);
                } else {
                    currNot.setStatus(NotificationStatusEnum.ERROR);
                }
                notificationDao.update(currNot);
            }
        } catch (Exception e) {
            logger.error(String.format("scanAndSend ERROR: %s", e.getMessage()));
        } finally {
            locker.unlock();
        }
    }

    private boolean sendEmail(String subject, String messageTest, String to) {
        try {
            SimpleMailMessage smm = new SimpleMailMessage();

            smm.setFrom(environment.getRequiredProperty("mail.from"));
            smm.setTo(to);
            smm.setSubject(subject);
            smm.setText(messageTest);

            mailSender.send(smm);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
}
