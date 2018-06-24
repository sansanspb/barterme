package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.iqdevelop.barterme.entities.MarketingChannelEntity;
import ru.iqdevelop.barterme.entities.PartnerEntity;
import ru.iqdevelop.barterme.entities.PartnersNotificationEntity;
import ru.iqdevelop.barterme.entities.RegionEntity;
import ru.iqdevelop.barterme.models.*;
import ru.iqdevelop.barterme.models.categories.CompanyCategoriesModel;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.CompanyService;
import ru.iqdevelop.barterme.services.PartnerService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PartnerService partnerService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage save(@RequestBody InCompanyModel inCompanyModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.save(inCompanyModel, userEmail);
            OutCompanyModel model = companyService.getModelByEmail(userEmail);
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось сохранить данные компании");
        }
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getInfo(HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            OutCompanyModel model = companyService.getModelByEmail(userEmail);
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/getOthersCompanies", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getOthersCompanies(HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            List<OutCompanyModel> models = companyService.getOthersModels(userEmail);
            return AnswerMessage.getSuccessMessage(models);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/getCompaniesBySubCategory", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getCompaniesBySubCategory(@RequestBody CompaniesBySubCategoryModel model, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            List<OutCompanyModel> models = companyService.getCompaniesBySubCategory(userEmail, model);
            return AnswerMessage.getSuccessMessage(models);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/getCategories", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getCategories(HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            CompanyCategoriesModel model = companyService.getCategories(userEmail);
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/getOtherCompanyCategories", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getOtherCompanyCategories(@RequestBody PartnerWithCompanyModel partnerWithCompanyModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            CompanyCategoriesModel model = companyService.getOtherCompanyCategories(partnerWithCompanyModel.getCompanyId());
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/setCategories", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage setCategories(@RequestBody CompanyCategoriesModel companyCategoriesModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.setCategories(userEmail, companyCategoriesModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/getChannels", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getChannels(HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            List<MarketingChannelEntity> model = companyService.getChannels(userEmail);
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/setChannels", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage setChannels(@RequestBody CompanyChannelsModel companyChannelsModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.setChannels(userEmail, companyChannelsModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось получить данные компании");
        }
    }

    @RequestMapping(value = "/getRegions", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getRegions(HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            List<RegionEntity> regions = companyService.getRegions();
            return AnswerMessage.getSuccessMessage(regions);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AnswerMessage.getFailMessage("Не удалось получить данные");
        }
    }

    @RequestMapping(value = "/sendPartner", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage sendPartner(@RequestBody PartnerWithCompanyModel partnerWithCompanyModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.sendPartner(userEmail, partnerWithCompanyModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

    @RequestMapping(value = "/receivePartner", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage receivePartner(@RequestBody PartnerWithCompanyModel partnerWithCompanyModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.receivePartner(userEmail, partnerWithCompanyModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }


    @RequestMapping(value = "/rejectPartner", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage rejectPartner(@RequestBody PartnerWithCompanyModel partnerWithCompanyModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.rejectPartner(userEmail, partnerWithCompanyModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

    @RequestMapping(value = "/completePartnerWithMark", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage completePartner(@RequestBody PartnerMarkModel partnerMarkModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.completePartnerWithMark(userEmail, partnerMarkModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

    @RequestMapping(value = "/getPartners", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getPartners(HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            List<PartnerEntity> model = companyService.getPartners(userEmail);
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

    @RequestMapping(value = "/getPartnerNotifications", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getPartnerNotifications(HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            List<PartnersNotificationEntity> model = companyService.getPartnerNotifications(userEmail);
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

    @RequestMapping(value = "/setPartnerNotificationReaded", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage setPartnerNotificationReaded(@RequestBody PartnerNotificationModel partnerNotificationModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.setPartnerNotificationReaded(userEmail, partnerNotificationModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

//    @RequestMapping(value = "/setPartnersMark", method = RequestMethod.POST)
//    public @ResponseBody
//    AnswerMessage setPartnersMark(@RequestBody PartnerMarkModel partnerMarkModel, HttpServletRequest request) {
//        try {
//            Principal userPrincipal = request.getUserPrincipal();
//            if (userPrincipal == null) {
//                return AnswerMessage.getFailMessage("Вы не авторизованны");
//            }
//
//            String userEmail = userPrincipal.getName();
//            companyService.setPartnersMark(userEmail, partnerMarkModel);
//            return AnswerMessage.getSuccessMessage();
//        } catch (Exception e) {
//            logger.error(Arrays.toString(e.getStackTrace()));
//            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
//        }
//    }

    @RequestMapping(value = "/addCompanyToFavorite", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage addCompanyToFavorite(@RequestBody FavoriteCompanyModel favoriteCompanyModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.addCompanyToFavorite(userEmail, favoriteCompanyModel);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

    @RequestMapping(value = "/removeCompanyFromFavorite", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage removeCompanyFromFavorite(@RequestBody FavoriteCompanyModel model, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            companyService.removeCompanyFromFavorite(userEmail, model);
            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось сохранить данные");
        }
    }

    @RequestMapping(value = "/getFavorites", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getFavorites(@RequestBody PartnerWithCompanyModel partnerWithCompanyModel, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (userPrincipal == null) {
                return AnswerMessage.getFailMessage("Вы не авторизованны");
            }

            String userEmail = userPrincipal.getName();
            List<OutCompanyModel> model = companyService.getFavorites(userEmail);

            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return AnswerMessage.getFailMessage("Не удалось получить данные");
        }
    }

}
