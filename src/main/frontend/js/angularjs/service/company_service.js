'use strict';

module.exports = function ($http, $q, PreloaderService) {

    //private section

    var SERVICE_URI = {
            save: 'company/save',
            getInfo: 'company/getInfo',
            getRegions: 'company/getRegions',
            getCategories: 'company/getCategories',
            getOtherCompanyCategories: 'company/getOtherCompanyCategories',
            getMarketingChannels: 'company/getChannels',
            setCategories: 'company/setCategories',
            setMarketingChannels: 'company/setChannels',
            getCompaniesBySubCategory: 'company/getCompaniesBySubCategory',
            sendPartner: 'company/sendPartner',
            getPartners: 'company/getPartners',
            receivePartner: 'company/receivePartner',
            completePartnerWithMark: 'company/completePartnerWithMark',
            rejectPartner: 'company/rejectPartner',
            getOthersCompanies: 'company/getOthersCompanies',
            addCompanyToFavorite: 'company/addCompanyToFavorite',
            removeCompanyFromFavorite: 'company/removeCompanyFromFavorite',
            getFavorites: 'company/getFavorites',
            upload: 'image/upload',
            getPartnerNotifications: 'company/getPartnerNotifications',
            setPartnerNotificationReaded: 'company/setPartnerNotificationReaded'
        },
        SERVICE_CFG = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        },
        container = {
            company: {},
            otherCompanies: [],
            partners: [],
            partnerNotifications: [],

            //methods
            getCompanyById: getCompanyById
        };

    function getCompanyById(companyId) {
        for (var i = 0; i < container.companies.length; i++) {
            if (container.companies[i].companyId == companyId) {
                return container.companies[i];
            }
        }
    }

    var service = {
        getCompany: getCompany,
        getNotifications: getNotifications,

        save: save,
        getInfo: getInfo,
        getRegions: getRegions,
        getCategories: getCategories,
        getOtherCompanyCategories: getOtherCompanyCategories,
        getMarketingChannels: getMarketingChannels,
        setCategories: setCategories,
        setMarketingChannels: setMarketingChannels,
        getCompaniesBySubCategory: getCompaniesBySubCategory,
        sendPartner: sendPartner,
        getPartners: getPartners,
        receivePartner: receivePartner,
        completePartnerWithMark: completePartnerWithMark,
        rejectPartner: rejectPartner,
        getOthersCompanies: getOthersCompanies,
        addCompanyToFavorite: addCompanyToFavorite,
        removeCompanyFromFavorite: removeCompanyFromFavorite,
        getFavorites: getFavorites,
        upload: upload,
        getPartnerNotifications: getPartnerNotifications,
        setPartnerNotificationReaded: setPartnerNotificationReaded
    };

    function getCompany() {
        return container.company;
    }

    function getNotifications() {
        return container.partnerNotifications;
    }

    function getInfo() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getInfo)
            .then(
                function (response) {
                    if (response.data.success) {
                        container.company = response.data.data;
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function save(company) {
        var deferred = $q.defer(),
            data = JSON.stringify(company);

        $http.post(SERVICE_URI.save, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while creating Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getRegions() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getRegions)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getCategories() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getCategories)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getOtherCompanyCategories(companyId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: companyId});
        $http.post(SERVICE_URI.getOtherCompanyCategories, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getMarketingChannels() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getMarketingChannels)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function setCategories(categories) {
        var deferred = $q.defer(),
            data = JSON.stringify(categories);

        $http.post(SERVICE_URI.setCategories, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while creating Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function setMarketingChannels(marketingChannels) {
        var deferred = $q.defer(),
            arr = marketingChannels.map(function (elem, index, inparr) {
                return elem.marketingChannelId;
            }),
            data = JSON.stringify({channelsIds: arr});

        $http.post(SERVICE_URI.setMarketingChannels, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while creating Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getCompaniesBySubCategory(subCategoryId) {
        var deferred = $q.defer(),
            data = JSON.stringify({categoryId: subCategoryId});
        PreloaderService.sendRequest(SERVICE_URI.getCompaniesBySubCategory, data).then(
            function (result) {
                deferred.resolve(result);
            }
        );
        $http.post(SERVICE_URI.getCompaniesBySubCategory, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function sendPartner(companyId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: companyId});
        $http.post(SERVICE_URI.sendPartner, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getPartners() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getPartners)
            .then(
                function (response) {
                    if (response.data.success) {
                        container.partners = response.data.data;
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function receivePartner(partnerId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: partnerId});
        $http.post(SERVICE_URI.receivePartner, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function completePartnerWithMark(partnerId, mark) {
        var deferred = $q.defer(),
            data = JSON.stringify({partnerId: partnerId, mark: mark});
        $http.post(SERVICE_URI.completePartnerWithMark, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function rejectPartner(companyId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: companyId});
        $http.post(SERVICE_URI.rejectPartner, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getOthersCompanies() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getOthersCompanies)
            .then(
                function (response) {
                    if (response.data.success) {
                        container.companies = response.data.data;
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function addCompanyToFavorite(companyId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: companyId});
        $http.post(SERVICE_URI.addCompanyToFavorite, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function removeCompanyFromFavorite(companyId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: companyId});
        $http.post(SERVICE_URI.removeCompanyFromFavorite, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getFavorites(companyId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: companyId});
        $http.post(SERVICE_URI.getFavorites, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function upload(data) {
        var deferred = $q.defer(),
            fd = new FormData();
        fd.append('uploadimage', data);
        $http.post(SERVICE_URI.upload, fd, {headers: {'Content-Type': undefined}})
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getPartnerNotifications() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getPartnerNotifications)
            .then(
                function (response) {
                    if (response.data.success) {
                        container.partnerNotifications = response.data.data;
                        for (var i = 0; i < container.partnerNotifications.length; i++) {
                            var partner = container.partnerNotifications[i],
                                company = container.getCompanyById(partner.senderId);
                            partner.company = company;
                            partner.dateCreate = new Date(partner.dateCreate);
                        }
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function setPartnerNotificationReaded(partnersNotificationId) {
        var deferred = $q.defer(),
            data = JSON.stringify({partnersNotificationId: partnersNotificationId});
        $http.post(SERVICE_URI.setPartnerNotificationReaded, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting Orders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    return service;
};