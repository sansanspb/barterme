package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.iqdevelop.barterme.repositories.ChatRepository;

@Service
@EnableTransactionManagement
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
}
