'use sctrict';

module.exports = function($scope, CompanyService, CategoriesService, ReviewService){
    var $scope = self = this;

    self.ratings = {
        '1' : 'one',
        '2' : 'two',
        '3' : 'three',
        '4' : 'four',
        '5' : 'five'
    }

    self.currentCategId = 0;
    self.currentParentCatId = 0;

    self.sendPartner = sendPartner;
    self.getCompaniesBySubCategory = getCompaniesBySubCategory;
    self.getCompany = getCompany;
    self.getRaiting = getRaiting;
    self.getCompanyReviews = getCompanyReviews;
    self.getRegions = getRegions;
    self.getCategories = getCategories;
    self.getCategoryNameById = getCategoryNameById;
    self.getFavorites = getFavorites;
    self.favoriteClick = favoriteClick;
    self.addCompanyToFavorite = addCompanyToFavorite;
    self.removeCompanyFromFavorite = removeCompanyFromFavorite;
    self.moveCard = moveCard;

    self.getCompany();

    function sendPartner(){
        CompanyService.sendPartner($('#companyId').val()).then(function (result) {
        });
    }

    function getCompaniesBySubCategory(subCategoryId){
        CompanyService.getCompaniesBySubCategory($('#subCatId').val()).then(function (result) {
            self.companies = result;
        });
    }

    function getCompany(){
        var compId = $('#companyId').val();
        self.currentCategId = $('#subCatId').val();
        self.currentParentCatId = $('#parentId').val();
        if (compId){
            CompanyService.getOthersCompanies().then(function(result){
                for (var i = 0; i < result.length; i++){
                    if (compId == result[i].companyId){
                        self.company = result[i];
                        self.getCategories(compId);
                        self.getFavorites();
                        self.getRegions();
                        self.getCompanyReviews();
                        CategoriesService.getAll().then(function(result) {
                            self.categories = result;
                        });
                        break;
                    }
                }
            });
        }
        self.getCompaniesBySubCategory();
    }

    function getRaiting(raiting){
        return self.ratings[Math.round(raiting)];
    }

    function getCompanyReviews(){
        ReviewService.getCompanyReviews(self.company.companyId).then(function (result) {
            self.company.reviews = result;
        });
    }

    function getRegions(){
        CompanyService.getRegions().then(function(result){
            self.regions = result;
            for (var i = 0; i < result.length; i++){
                if (result[i].regionId == self.company.regionId){
                    self.company.regionString = result[i].title;
                }
            }
        });
    }

    function getCategoryNameById(categoryId) {
        if (!self.categories) return '';
        for (var i = 0; i < self.categories.length; i++){
            if (self.categories[i].categoryId == categoryId){
                return self.categories[i].title;
            }
        }
    }

    function getCategories(companyId){
        CompanyService.getOtherCompanyCategories(companyId).then(function(result){
            self.company.categories = result;
        });
    }

    function getFavorites(){
        CompanyService.getFavorites(self.company.companyId).then(function(result){
            self.favorites = result;
            for (var i = 0; i < self.favorites.length; i++){
                if (self.favorites[i].companyId == self.company.companyId){
                    break;
                }
            }
            self.company.isFavorite = i != self.favorites.length;
        });
    }

    function favoriteClick(){
        for (var i = 0; i < self.favorites.length; i++){
            if (self.favorites[i].companyId == self.company.companyId){
                break;
            }
        }
        if (i != self.favorites.length){
            self.removeCompanyFromFavorite();
        } else {
            self.addCompanyToFavorite();
        }
    }

    function addCompanyToFavorite(){
        CompanyService.addCompanyToFavorite(self.company.companyId).then(function(result){
            self.company.isFavorite = true;
            self.getFavorites();
        });
    }

    function removeCompanyFromFavorite(){
        CompanyService.removeCompanyFromFavorite(self.company.companyId).then(function(result){
            self.company.isFavorite = false;
            self.getFavorites();
        });
    }

    function moveCard(flag){
            if (!self.companies || !self.company) return '#';
            for (var i = 0; i < self.companies.length; i++){
                if (self.companies[i].companyId == self.company.companyId){
                    var id = 0;
                    if (flag){
                        id = i == 0 ? self.companies[self.companies.length - 1].companyId : self.companies[i - 1].companyId;
                    } else {
                        id = i == self.companies.length - 1 ? self.companies[0].companyId : self.companies[i + 1].companyId;
                    }
                    return "card?parentId=" + self.currentParentCatId + "&subId=" + self.currentCategId + "&companyId=" + id;
                }
            }
            return '#';
    }

}