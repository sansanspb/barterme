'use strict';

module.exports = function ($http, $q) {

    var SERVICE_URI = {
            getMsg: 'chat/getMsg',
            sendMsg: 'chat/sendMsg'
        },
        SERVICE_CFG = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };
    var currentChatPartner = undefined;

    var service = {
        getMsg: getMsg,
        sendMsg: sendMsg,
        setCurrentChatPartner: setCurrentChatPartner,
        getCurrentChatPartner: getCurrentChatPartner
    };

    function getMsg(senderId, receiverId, stage) {
        if (!stage) stage = 0;
        var deferred = $q.defer(),
            data = $.param({
                senderId : senderId,
                receiverId : receiverId,
                stage : stage
            });
        $http.get(SERVICE_URI.getMsg + '?' + data)
            .then(
                function (response) {
                    if (response.data.success) {
                        for (var i = 0; i < response.data.data.length; i++){
                            response.data.data[i].sendDate = new Date(response.data.data[i].sendDate);
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

    function sendMsg(senderId, receiverId, message) {
        var deferred = $q.defer(),
            data = $.param({
                senderId : senderId,
                receiverId : receiverId,
                message : message
            });
        $http.post(SERVICE_URI.sendMsg + '?' + data)
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

    function setCurrentChatPartner(partner) {
        currentChatPartner = partner;
    }

    function getCurrentChatPartner() {
        return currentChatPartner;
    }

    return service;
}