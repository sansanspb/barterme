'use strict';

module.exports = function ($http, $q) {

    var SERVICE_URI = {
            send: 'searchOrder/send'
        },
        SERVICE_CFG = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };

    var service = {
        send: send
    };

    function send(orderModel) {
        var deferred = $q.defer(),
            data = JSON.stringify(orderModel);
        $http.post(SERVICE_URI.send, data)
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