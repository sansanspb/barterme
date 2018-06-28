'use strict';

module.exports = function ($http, $q) {

    var SERVICE_URI = {
            getUserInfo: 'auth/getUserInfo',
            changePassword: 'auth/changePassword'
        },
        SERVICE_CFG = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };

    var service = {
        regCode: false,
        reg: false,
        userInfo: {},
        getUserInfo: getUserInfo,
        changePassword: changePassword
    };

    function getUserInfo() {
        var deferred = $q.defer();
        $http.post(SERVICE_URI.getUserInfo)
            .then(
                function (response) {
                    if (response.data.success) {
                        service.userInfo = response.data.data;
                        var roles = response.data.data.roles;
                        for (var i = 0; i < roles.length; i++) {
                            if (roles[i] == 'ROLE_CONFIRMED_USER') {
                                service.reg = true;
                                service.regCode = false;
                                break;
                            }
                            if (roles[i] == 'ROLE_USER') {
                                service.reg = true;
                                service.regCode = true;
                            }
                        }
                        deferred.resolve(response.data.data);
                    } else {
                        service.reg = false;
                        service.regCode = false;
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

    function changePassword(password, newPassword){
        var deferred = $q.defer(),
            data = JSON.stringify({
                email : service.userInfo.email,
                password : password,
                newPassword : newPassword
            });
        $http.post(SERVICE_URI.changePassword, data)
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