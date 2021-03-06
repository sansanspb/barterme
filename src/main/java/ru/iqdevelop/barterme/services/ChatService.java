package ru.iqdevelop.barterme.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.ChatMessageEntity;
import ru.iqdevelop.barterme.entities.CompanyEntity;
import ru.iqdevelop.barterme.repositories.ChatRepository;
import ru.iqdevelop.barterme.repositories.CompanyRepository;

import java.util.List;

@Service
@EnableTransactionManagement
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public List<ChatMessageEntity> getMessages(Long sender, Long receiver, Integer stage) {
        return chatRepository.getMessagesHistoryPart(sender, receiver, stage);
    }

    @Transactional
    public void sendMessage(Long senderId, Long receiverId, String msg) {
        CompanyEntity senderCompany = companyRepository.getById(senderId);
        CompanyEntity receiverCompany = companyRepository.getById(receiverId);

        if (senderCompany == null || receiverCompany == null)
            logger.error(String.format("Company sender id=%d or receiver id=%d are not found.", senderId, receiverId));

        ChatMessageEntity msgEntity = new ChatMessageEntity();
        msgEntity.setSender(senderCompany);
        msgEntity.setReceiver(receiverCompany);
        msgEntity.setFkSenderId(senderId);
        msgEntity.setFkReceiverId(receiverId);
        msgEntity.setMessage(msg);

        chatRepository.put(msgEntity);
    }
}
