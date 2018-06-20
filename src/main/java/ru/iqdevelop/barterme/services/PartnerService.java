package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.iqdevelop.barterme.models.PartnerWithCompanyModel;
import ru.iqdevelop.barterme.repositories.PartnerRepository;

@Service
@EnableTransactionManagement
public class PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    public void setPartnerStatus(PartnerWithCompanyModel partnerStatus) {

    }
}
