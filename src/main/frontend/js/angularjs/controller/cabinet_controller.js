'use sctrict';

module.exports = function ($scope, CompanyService, AuthService, CategoriesService, MarketingChannelsService, CustomService, ReviewService, ChatService) {
    var $scope = self = this;

    self.VOC = ['А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Э', 'Ю', 'Я'];
    self.VOC1 = ['А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'К', 'Л', 'М', 'Н', 'О'];
    self.VOC2 = ['П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Э', 'Ю', 'Я'];
    self.VOC3 = ['Ф', 'Х', 'Ц', 'Ч', 'Ш'];
    self.VOC4 = ['Щ', 'Э', 'Ю', 'Я'];
    self.ratings = {
        '1': 'one',
        '2': 'two',
        '3': 'three',
        '4': 'four',
        '5': 'five'
    };

    self.getFavorites = getFavorites;
    self.getInfo = getInfo;
    self.getCategories = getCategories;
    self.getMarketingChannels = getMarketingChannels;
    self.getCompanyReviews = getCompanyReviews;
    self.getRaiting = getRaiting;
    self.getRegions = getRegions;
    self.getRegionById = getRegionById;
    self.setRegion = setRegion;
    self.isCategoryChecked = isCategoryChecked;
    self.isMarketingChannelChecked = isMarketingChannelChecked;
    self.pushCategory = pushCategory;
    self.pushMarketingChannel = pushMarketingChannel;
    self.getCategoryNameById = getCategoryNameById;
    self.saveCompany = saveCompany;
    self.switchGender = switchGender;
    self.getPartners = getPartners;
    self.receivePartner = receivePartner;
    self.preCompletePartner = preCompletePartner;
    self.completePartner = completePartner;
    self.preRejectPartner = preRejectPartner;
    self.cancelRejectPartner = cancelRejectPartner;
    self.rejectPartner = rejectPartner;
    self.getOtherCompanies = getOtherCompanies;
    self.getCompanyById = getCompanyById;
    self.removeCompanyFromFavorite = removeCompanyFromFavorite;
    self.uploadImage = uploadImage;
    self.openChat = openChat;

    self.getInfo();
    self.getRegions();

    function getFavorites() {
        CompanyService.getFavorites(self.company.companyId).then(function (result) {
            self.favorites = result;
        });
    }

    function getInfo() {
        CompanyService.getInfo().then(function (result) {
            self.company = result;
            self.getCategories();
            self.getMarketingChannels();
            self.getOtherCompanies();
            self.getCompanyReviews();
            self.getFavorites();
        });
        CategoriesService.getAll().then(function (result) {
            self.categories = result;
            self.categoriesTree = CustomService.mapTree(result);
        });
        MarketingChannelsService.getAll().then(function (result) {
            self.marketingChannels = result;
        });
        MarketingChannelsService.getAll().then(function (result) {
            self.marketingChannels = result;
        });
    }

    function getCategories() {
        CompanyService.getCategories().then(function (result) {
            self.company.categories = result;
        });
    }

    function getMarketingChannels() {
        CompanyService.getMarketingChannels().then(function (result) {
            self.company.marketingChannels = result;
        });
    }

    function getCompanyReviews() {
        ReviewService.getCompanyReviews(self.company.companyId).then(function (result) {
            self.company.reviews = result;
        });
    }

    function getRaiting(raiting) {
        return self.ratings[Math.round(raiting)];

    }

    function getCategoryNameById(categoryId) {
        if (!self.categories) return '';
        for (var i = 0; i < self.categories.length; i++) {
            if (self.categories[i].categoryId == categoryId) {
                return self.categories[i].title;
            }
        }
    }

    function getRegions() {
        CompanyService.getRegions().then(function (result) {
            self.regions = result;
            // console.log(result);
        });
    }

    function getRegionById() {
        if (!self.company || !self.regions) return '';
        for (var i = 0; i < self.regions.length; i++) {
            if (self.company.regionId === self.regions[i].regionId) {
                return self.regions[i].title;
            }
        }
        return 'Выберите геолокацию';
    }

    function setRegion(regionId) {
        self.company.regionId = regionId;
        $('.popup-wrapper, .popup').fadeOut(200);
    }

    function isCategoryChecked(categoryId, type) {
        if (!self.company || !self.company.categories) return false;
        switch (type) {
            case 1:
                for (var i = 0; i < self.company.categories.offerCategories.length; i++) {
                    if (self.company.categories.offerCategories[i] == categoryId) {
                        return true;
                    }
                }
                break;
            case 2:
                for (var i = 0; i < self.company.categories.searchCategories.length; i++) {
                    if (self.company.categories.searchCategories[i] == categoryId) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    function isMarketingChannelChecked(marketingChannelId) {
        if (!self.company || !self.company.marketingChannels) return false;
        for (var i = 0; i < self.company.marketingChannels.length; i++) {
            if (self.company.marketingChannels[i].marketingChannelId == marketingChannelId) {
                return true;
            }
        }
        return false;
    }

    function pushCategory(categoryId, type) {
        switch (type) {
            case 1:
                if (!self.company.categories.offerCategories) {
                    self.company.categories.offerCategories = [];
                }
                for (var i = 0; i < self.company.categories.offerCategories.length; i++) {
                    if (self.company.categories.offerCategories[i] == categoryId) {
                        break;
                    }
                }
                if (i == self.company.categories.offerCategories.length) {
                    self.company.categories.offerCategories.push(categoryId);
                } else {
                    self.company.categories.offerCategories.splice(i, 1);
                }
                break;
            case 2:
                if (!self.company.categories.searchCategories) {
                    self.company.categories.searchCategories = [];
                }
                for (var i = 0; i < self.company.categories.searchCategories.length; i++) {
                    if (self.company.categories.searchCategories[i] == categoryId) {
                        break;
                    }
                }
                if (i == self.company.categories.searchCategories.length) {
                    self.company.categories.searchCategories.push(categoryId);
                } else {
                    self.company.categories.searchCategories.splice(i, 1);
                }
                break;
        }
    }

    function pushMarketingChannel(marketingChannel) {
        if (!self.company.marketingChannels) {
            self.company.marketingChannels = [];
        }
        for (var i = 0; i < self.company.marketingChannels.length; i++) {
            if (self.company.marketingChannels[i].marketingChannelId == marketingChannel.marketingChannelId) {
                break;
            }
        }
        if (i == self.company.marketingChannels.length) {
            self.company.marketingChannels.push(marketingChannel);
        } else {
            self.company.marketingChannels.splice(i, 1);
        }
    }

    function saveCompany() {
        CompanyService.save(self.company).then(
            function (access) {
                var one = CompanyService.setCategories(self.company.categories).then(function (result) {

                    }),
                    two = CompanyService.setMarketingChannels(self.company.marketingChannels).then(function (result) {

                    });
                Promise.all([one, two]).then(
                    function (success) {
                        window.location.href = "cabinet";
                    },
                    function (reject) {
                        console.log(reject);
                    }
                );
            },
            function (reject) {
                alert('что то пошло не так ;-)');
            });
    }

    function switchGender(type) {
        switch (type) {
            case 1:
                self.company.genderMale = true;
                self.company.genderFemale = false;
                break;
            case 2:
                self.company.genderMale = false;
                self.company.genderFemale = true;
                break;
            case 3:
                self.company.genderMale = true;
                self.company.genderFemale = true;
                break;
        }
    }

    function getPartners() {
        CompanyService.getPartners().then(function (result) {
            self.company.partners = result;
            for (var i = 0; i < self.company.partners.length; i++) {
                var partner = self.company.partners[i],
                    companyId = partner.receiverId == self.company.companyId ? partner.senderId : partner.receiverId,
                    company = self.getCompanyById(companyId);
                partner.company = company;
            }
        });
    }

    function receivePartner(partner) {
        CompanyService.receivePartner(partner.senderId).then(function (result) {
            self.getPartners();
            // TODO: show chat fragment here
        });
    }

    function preCompletePartner($finishPartner, partner) {
        self.currentPartner = partner;
        $finishPartner.preventDefault();
        $finishPartner.stopPropagation();
        $('#finishPartnerWrapper').fadeIn(300);
        $('#finishPartnerPopup').fadeIn(300);
    }

    function completePartner() {
        var partner = self.currentPartner;
        var companyId = partner.senderId == self.company.companyId ? partner.receiverId : partner.senderId;
        var isGood = self.raiting > 2;
        CompanyService.completePartnerWithMark(partner.partnersId, parseInt(self.raiting)).then(function (result) {
            ReviewService.sendReview(companyId, self.raitingMessage, isGood).then(function (result) {
                self.getPartners();
            });
        });
    }

    function preRejectPartner($partner, partner) {
        $partner.preventDefault();
        $('#btns' + partner.partnersId).fadeOut(100);
        $('#btnAlerts' + partner.partnersId).delay(300).fadeIn(300);
    }

    function cancelRejectPartner($partner, partner) {
        $partner.preventDefault();
        $('#btnAlerts' + partner.partnersId).fadeOut(100);
        $('#btns' + partner.partnersId).delay(300).fadeIn(300);
    }

    function rejectPartner(partner) {
        CompanyService.rejectPartner(partner.senderId).then(function (result) {
            self.getPartners();
        });
    }

    function getOtherCompanies() {
        CompanyService.getOthersCompanies().then(function (result) {
            self.companies = result;
            self.getPartners();
        });
    }

    function getCompanyById(companyId) {
        if (!self.companies) return '';
        for (var i = 0; i < self.companies.length; i++) {
            if (self.companies[i].companyId == companyId) {
                return self.companies[i];
            }
        }
    }

    function removeCompanyFromFavorite(companyId) {
        CompanyService.removeCompanyFromFavorite(companyId).then(function (result) {
            self.getFavorites();
        });
    }

    function uploadImage() {
        var inp = $("#ava");
        inp.unbind();
        inp.change(function () {
            var file_name;
            if (inp[0].files[0] != undefined) {
                file_name = inp[0].files[0].name;
                CompanyService.upload(inp[0].files[0]).then(function (result) {
                    self.company.photoId = result.photoId;
                    self.company.photoPath = result.photoPath;
                });
            }
        });
        inp.click();
    }

    function openChat(partner){

        var newPartner = {
            company : partner.company,
            senderId : partner.senderId,
            receiverId : partner.receiverId
        }
        if (partner.receiverId == self.company.companyId){
            newPartner.receiverId = partner.senderId;
            newPartner.senderId = self.company.companyId;
        }
        ChatService.setCurrentChatPartner(newPartner);
    }
};