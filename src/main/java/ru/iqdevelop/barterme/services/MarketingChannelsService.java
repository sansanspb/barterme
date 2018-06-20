package ru.iqdevelop.barterme.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.MarketingChannelEntity;
import ru.iqdevelop.barterme.repositories.MarketingChannelRepository;

import java.util.List;

@Service
@EnableTransactionManagement
public class MarketingChannelsService {

    private static final Logger logger = LoggerFactory.getLogger(MarketingChannelsService.class);

    @Autowired
    private MarketingChannelRepository marketingChannelRepository;

    @Transactional
    public List<MarketingChannelEntity> getAll() {
        return marketingChannelRepository.getAll();
    }
}
