package ru.iqdevelop.barterme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iqdevelop.barterme.entities.*;
import ru.iqdevelop.barterme.models.*;
import ru.iqdevelop.barterme.models.categories.CompanyCategoriesModel;
import ru.iqdevelop.barterme.models.enums.PartnerStatusEnum;
import ru.iqdevelop.barterme.notification.NotificationBuilder;
import ru.iqdevelop.barterme.repositories.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private MarketingChannelRepository marketingChannelRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PartnersNotificationRepository partnersNotificationRepository;

    @Transactional
    public CompanyEntity save(InCompanyModel inCompanyModel, String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        convertModelToEntity(inCompanyModel, currCompany);
        companyRepository.update(currCompany);
        return currCompany;
    }

    @Transactional
    public OutCompanyModel getModelByEmail(String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        return convertEntityToModel(currCompany);
    }

    @Transactional(readOnly = true)
    public List<RegionEntity> getRegions() {
        return regionRepository.getAll();
    }

    @Transactional(readOnly = true)
    public CompanyCategoriesModel getCategories(String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        CompanyCategoriesModel model = new CompanyCategoriesModel();
        model.parseAndSetOfferCategories(currCompany.getOfferCategories());
        model.parseAndSetSearchCategories(currCompany.getSearchCategories());

        return model;
    }

    @Transactional(readOnly = true)
    public CompanyCategoriesModel getOtherCompanyCategories(Long companyId) {


        CompanyEntity neededCompany = companyRepository.getById(companyId);
        CompanyCategoriesModel model = new CompanyCategoriesModel();
        model.parseAndSetOfferCategories(neededCompany.getOfferCategories());
        model.parseAndSetSearchCategories(neededCompany.getSearchCategories());

        return model;
    }

    @Transactional
    public void setCategories(String userEmail, CompanyCategoriesModel companyCategoriesModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        syncCategories(currCompany.getSearchCategories(), companyCategoriesModel.getSearchCategories());
        syncCategories(currCompany.getOfferCategories(), companyCategoriesModel.getOfferCategories());
    }

    @Transactional(readOnly = true)
    public List<MarketingChannelEntity> getChannels(String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        // костыль для lazy load
        List<MarketingChannelEntity> list = currCompany.getMarketingChannels();
        list = list.stream().filter(t -> t.getMarketingChannelId() != null).collect(Collectors.toList());
        return list;
    }

    @Transactional
    public void setChannels(String userEmail, CompanyChannelsModel companyChannelsModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        syncChannels(currCompany.getMarketingChannels(), companyChannelsModel.getChannelsIds());
    }

    @Transactional
    public void sendPartner(String userEmail, PartnerWithCompanyModel partnerWithCompanyModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        PartnerEntity partner = partnerRepository.getCurrentPartnerByCompanies(currCompany.getCompanyId(), partnerWithCompanyModel.getCompanyId());
        if (partner != null) {
            throw new RuntimeException("Уже есть активно партнерство или предложение о партнерстве с этой компанией");
        } else {
            PartnerEntity newPartner = new PartnerEntity();
            newPartner.setStatus(PartnerStatusEnum.WAIT);
            newPartner.setSender(currCompany);
            newPartner.setReceiver(companyRepository.getById(partnerWithCompanyModel.getCompanyId()));
            partnerRepository.insert(newPartner);


            NotificationEntity newNot = NotificationBuilder.buildSendedPartnerNotification(currCompany.getCaption(),  userRepository.getById(newPartner.getReceiver().getCompanyId()));
            notificationRepository.insert(newNot);

            PartnersNotificationEntity ent = new PartnersNotificationEntity();
            ent.setReceiverId(newPartner.getReceiver().getCompanyId());
            ent.setSenderId(newPartner.getSender().getCompanyId());
            ent.setStatus(PartnerStatusEnum.WAIT);
            partnersNotificationRepository.insert(ent);
        }
    }

    @Transactional
    public void receivePartner(String userEmail, PartnerWithCompanyModel partnerWithCompanyModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        PartnerEntity partner = partnerRepository.getPartnerInviteFromCompany(partnerWithCompanyModel.getCompanyId(), currCompany.getCompanyId());
        if (partner == null) {
            throw new RuntimeException("У вас нет партнерства с этой компанией");
        } else {
            partner.setStatus(PartnerStatusEnum.ACTIVE);
            partnerRepository.update(partner);

            PartnersNotificationEntity ent = new PartnersNotificationEntity();
            // не перепутано, ресивер тот, кто должен причитать нотификацию
            ent.setReceiverId(partner.getSender().getCompanyId());
            ent.setSenderId(partner.getReceiver().getCompanyId());
            ent.setStatus(PartnerStatusEnum.ACTIVE);
            partnersNotificationRepository.insert(ent);

            NotificationEntity newNot = NotificationBuilder.buildReceivedPartnerNotification(currCompany.getCaption(),  userRepository.getById(partner.getFkSenderId()));
            notificationRepository.insert(newNot);
        }
    }

    @Transactional
    public void rejectPartner(String userEmail, PartnerWithCompanyModel partnerWithCompanyModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        PartnerEntity partner = partnerRepository.getPartnerInviteFromCompany(partnerWithCompanyModel.getCompanyId(), currCompany.getCompanyId());
        if (partner == null) {
            throw new RuntimeException("У вас нет партнерства с этой компанией");
        } else {
            partnerRepository.delete(partner);

            PartnersNotificationEntity ent = new PartnersNotificationEntity();
            // не перепутано, ресивер тот, кто должен причитать нотификацию
            ent.setReceiverId(partner.getSender().getCompanyId());
            ent.setSenderId(partner.getReceiver().getCompanyId());
            ent.setStatus(PartnerStatusEnum.REJECTED);
            partnersNotificationRepository.insert(ent);

            NotificationEntity newNot = NotificationBuilder.buildRejectedPartnerNotification(currCompany.getCaption(),  userRepository.getById(partner.getFkSenderId()));
            notificationRepository.insert(newNot);
        }
    }



    @Transactional(readOnly = true)
    public List<PartnerEntity> getPartners(String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);
        return partnerRepository.getAllPartnersOfCompany(currCompany.getCompanyId());
    }

    @Transactional(readOnly = true)
    public List<PartnersNotificationEntity> getPartnerNotifications(String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);
        return partnersNotificationRepository.getAllOfCompany(currCompany.getCompanyId());
    }

    @Transactional
    public void setPartnerNotificationReaded(String userEmail, PartnerNotificationModel partnerNotificationModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);
        PartnersNotificationEntity ent = partnersNotificationRepository.getById(partnerNotificationModel.getPartnersNotificationId());

        if (!currCompany.getCompanyId().equals(ent.getReceiverId())) {
            throw new RuntimeException("Это не ваше уведомление");
        }

        ent.setReaded(true);
        partnersNotificationRepository.update(ent);
    }

    @Transactional(readOnly = true)
    public List<OutCompanyModel> getOthersModels(String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);
        List<CompanyEntity> dbList = companyRepository.getAllOthers(currCompany.getCompanyId());
        return dbList.stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OutCompanyModel> getCompaniesBySubCategory(String userEmail, CompaniesBySubCategoryModel model) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);
        List<CompanyEntity> dbList = companyRepository.getCompaniesBySubCategory(currCompany.getCompanyId(), model.getCategoryId());
        return dbList.stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Transactional
    public void addCompanyToFavorite(String userEmail, FavoriteCompanyModel favoriteCompanyModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        CompanyEntity favoriteCompany = companyRepository.getById(favoriteCompanyModel.getCompanyId());
        if (favoriteCompany == null) {
            throw new RuntimeException("Нет такой компании, чтобы добавить ее в избранные!");
        }

        List<CompanyEntity> favorites = currCompany.getFavoritesCompanies();
        if (favorites.contains(favoriteCompany)) {
            throw new RuntimeException("Уже добавлено в избранные!");
        }

        favorites.add(favoriteCompany);
        companyRepository.update(currCompany);
    }

    @Transactional
    public void removeCompanyFromFavorite(String userEmail, FavoriteCompanyModel favoriteCompanyModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);
        CompanyEntity favoriteCompany = companyRepository.getById(favoriteCompanyModel.getCompanyId());

        List<CompanyEntity> companies = currCompany.getFavoritesCompanies();
        companies.remove(favoriteCompany);

        companyRepository.update(currCompany);
    }

    @Transactional
    public List<OutCompanyModel> getFavorites(String userEmail) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);
        return currCompany.getFavoritesCompanies().stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }
//
//    @Transactional
//    public void completePartner(String userEmail, PartnerWithCompanyModel partnerWithCompanyModel) {
//        CompanyEntity currCompany = getCurrentCompany(userEmail);
//
//        PartnerEntity partner = partnerRepository.getActivePartnerOfCompanies(partnerWithCompanyModel.getCompanyId(), currCompany.getCompanyId());
//        if (partner == null) {
//            throw new RuntimeException("У вас нет партнерства с этой компанией");
//        } else {
//            partner.setStatus(PartnerStatusEnum.COMPLETE);
//            partnerRepository.update(partner);
//        }
//    }

    @Transactional
    public void completePartnerWithMark(String userEmail, PartnerMarkModel partnerMarkModel) {
        CompanyEntity currCompany = getCurrentCompany(userEmail);

        if (partnerMarkModel.getMark() == null || partnerMarkModel.getMark() > 5 || partnerMarkModel.getMark() < 1) {
            throw new RuntimeException("Оценка должна быть от 1 до 5!");
        }

        Long initiatorId = null;
        Long companyPartnerId = null;
        PartnerEntity partner = partnerRepository.getById(partnerMarkModel.getPartnerId());
        if (partner.getSender().equals(currCompany)) {
            partner.setMarkForReceiver(partnerMarkModel.getMark());
            companyPartnerId = partner.getFkReceiverId();
            initiatorId = partner.getFkSenderId();
        } else if (partner.getReceiver().equals(currCompany)) {
            partner.setMarkForSender(partnerMarkModel.getMark());
            companyPartnerId = partner.getFkSenderId();
            initiatorId = partner.getFkReceiverId();
        } else {
            throw new RuntimeException("У вас нет прав ставить оценку в данном партнерстве!");
        }
        partner.setStatus(PartnerStatusEnum.COMPLETE);
        partnerRepository.update(partner);

        // сказали не надо
//        PartnersNotificationEntity ent = new PartnersNotificationEntity();
//        ent.setReceiverId(companyPartnerId);
//        ent.setSenderId(initiatorId);
//        ent.setStatus(PartnerStatusEnum.COMPLETE);
//        partnersNotificationRepository.insert(ent);

        List<PartnerEntity> partners = partnerRepository.getAllPartnersOfCompany(companyPartnerId);
        double sum = 0L;
        double count = 0L;
        for (PartnerEntity currPartner : partners) {
            if (currPartner.getFkReceiverId().equals(companyPartnerId) && currPartner.getMarkForReceiver() != null) {
                count++;
                sum += currPartner.getMarkForReceiver();
            } else if (currPartner.getFkSenderId().equals(companyPartnerId) && currPartner.getMarkForSender() != null) {
                count++;
                sum += currPartner.getMarkForSender();
            } else {
                continue;
            }
        }
        double rating = sum / count;
        CompanyEntity company = companyRepository.getById(companyPartnerId);
        company.setRating(rating);
        companyRepository.update(company);
    }


    @Transactional(readOnly = true)
    public CompanyEntity getById(Long Id) {
        return companyRepository.getById(Id);
    }


    private CompanyEntity getCurrentCompany(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail);

        if (user == null) {
            throw new RuntimeException("Такого пользователя нет или вы не зарегистрированны!");
        }

        CompanyEntity currCompany = companyRepository.getById(user.getUserId());
        if (currCompany == null) {
            currCompany = new CompanyEntity();
            currCompany.setCompanyId(user.getUserId());
            currCompany.setContactEmail(userEmail);
            currCompany.setCaption(user.getTitle());
            companyRepository.insert(currCompany);
        }
        return currCompany;
    }

    private void syncChannels(List<MarketingChannelEntity> listToChange, List<Long> newChannelsIds) {
        List<Long> oldChannelsIds = listToChange.stream().map(MarketingChannelEntity::getMarketingChannelId).collect(Collectors.toList());

        List<Long> needDeleteIds = oldChannelsIds.stream().filter(t -> !newChannelsIds.contains(t)).collect(Collectors.toList());
        List<Long> needInsertIds = newChannelsIds.stream().filter(t -> !oldChannelsIds.contains(t)).collect(Collectors.toList());

        List<MarketingChannelEntity> tempChannels = listToChange.stream().filter(t -> needDeleteIds.contains(t.getMarketingChannelId())).collect(Collectors.toList());
        listToChange.removeAll(tempChannels);

        needInsertIds.forEach(t -> {
            MarketingChannelEntity chan = marketingChannelRepository.getById(t);
            listToChange.add(chan);
        });
    }

    private void syncCategories(List<CategoryEntity> listToChange, List<Long> newCategoriesIds) {
        List<Long> oldCategoriesIds = listToChange.stream().map(CategoryEntity::getCategoryId).collect(Collectors.toList());

        List<Long> needDeleteIds = oldCategoriesIds.stream().filter(t -> !newCategoriesIds.contains(t)).collect(Collectors.toList());
        List<Long> needInsertIds = newCategoriesIds.stream().filter(t -> !oldCategoriesIds.contains(t)).collect(Collectors.toList());

        List<CategoryEntity> tempCategs = listToChange.stream().filter(t -> needDeleteIds.contains(t.getCategoryId())).collect(Collectors.toList());
        listToChange.removeAll(tempCategs);

        needInsertIds.forEach(t -> {
            CategoryEntity categ = categoriesRepository.getById(t);
            listToChange.add(categ);
        });
    }

    private OutCompanyModel convertEntityToModel(CompanyEntity companyEntity) {
        OutCompanyModel model = new OutCompanyModel();

        model.setCompanyId(companyEntity.getCompanyId());
        model.setAbout(companyEntity.getAbout());
        model.setAgeMax(companyEntity.getAgeMax());
        model.setAgeMin(companyEntity.getAgeMin());
        model.setCaption(companyEntity.getCaption());
        model.setClientCount(companyEntity.getClientCount());
        model.setContactPerson(companyEntity.getContactPerson());
        model.setDescription(companyEntity.getDescription());
        model.setGenderFemale(companyEntity.getGenderFemale());
        model.setGenderMale(companyEntity.getGenderMale());
        model.setIncomeMin(companyEntity.getIncomeMin());
        model.setIncomeMax(companyEntity.getIncomeMax());
        model.setPhotoId(companyEntity.getFkPhotoId());

        if (companyEntity.getPhoto() != null) {
            Path path = Paths.get(companyEntity.getPhoto().getOriginalPath());
            model.setPhotoPath(path.getFileName().toString());
        } else {
            model.setPhotoPath("default-photo.jpg");
        }

        model.setRating(companyEntity.getRating());
        model.setRegionId(companyEntity.getFkRegionId());
        model.setContactEmail(companyEntity.getContactEmail());
        return model;
    }


    private void convertModelToEntity(InCompanyModel inCompanyModel, CompanyEntity currCompany) {
        currCompany.setAbout(inCompanyModel.getAbout());
        currCompany.setAgeMax(inCompanyModel.getAgeMax());
        currCompany.setAgeMin(inCompanyModel.getAgeMin());
        currCompany.setCaption(inCompanyModel.getCaption());
        currCompany.setClientCount(inCompanyModel.getClientCount());
        currCompany.setContactPerson(inCompanyModel.getContactPerson());
        currCompany.setDescription(inCompanyModel.getDescription());
        currCompany.setGenderFemale(inCompanyModel.getGenderFemale());
        currCompany.setGenderMale(inCompanyModel.getGenderMale());
        currCompany.setIncomeMin(inCompanyModel.getIncomeMin());
        currCompany.setIncomeMax(inCompanyModel.getIncomeMax());
        currCompany.setContactEmail(inCompanyModel.getContactEmail());

        PhotoEntity photo = photoRepository.getById(inCompanyModel.getPhotoId());
        currCompany.setPhoto(photo);

        RegionEntity region = regionRepository.getById(inCompanyModel.getRegionId());
        currCompany.setRegion(region);
    }


}
