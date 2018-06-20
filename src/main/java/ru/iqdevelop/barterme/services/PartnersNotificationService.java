package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.iqdevelop.barterme.repositories.PartnersNotificationRepository;

@Service
@EnableTransactionManagement
public class PartnersNotificationService {

    @Autowired
    private PartnersNotificationRepository partnersNotificationRepository;


}
