'use strict';

module.exports = function ($http, $q) {

    var SERVICE_URI = {
            getCompanyReviews: 'reviews/getCompanyReviews',
            sendReview: 'reviews/sendReview'
        },
        SERVICE_CFG = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };

    var service = {
        getCompanyReviews: getCompanyReviews,
        sendReview: sendReview
    };

    function getCompanyReviews(companyId) {
        var deferred = $q.defer(),
            data = JSON.stringify({companyId: companyId});
        $http.post(SERVICE_URI.getCompanyReviews, data)
            .then(
                function (response) {
                    if (response.data.success) {
                        for (var i = 0; i < response.data.data.length; i++) {
                            response.data.data[i].createDate = new Date(response.data.data[i].createDate);
                        }
                        deferred.resolve(response.data.data);
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                    }
                },
                function (errResponse) {
                    console.error('Error while getting UserInfo');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function sendReview(companyId, message, isGood) {
        var deferred = $q.defer(),
            data = JSON.stringify({
                companyId: companyId,
                message: message,
                isGood: isGood
            });
        $http.post(SERVICE_URI.sendReview, data)
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
                    console.error('Error while getting UserInfo');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    return service;
};