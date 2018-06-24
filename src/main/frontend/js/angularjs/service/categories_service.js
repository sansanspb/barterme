'use strict';

module.exports = function ($http, $q) {

    var SERVICE_URI = {
            getAll: 'categories/getAll'
        },
        SERVICE_CFG = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };

    var service = {
        getAll: getAll
    };

    function getAll() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getAll)
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