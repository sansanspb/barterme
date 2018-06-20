'use strict';

module.exports = function($rootScope, $log, $http, $q){

    //private section

    var stateContainer = [],
        addToContainer = addToContainer,
        removeFromContainer = removeFromContainer;

    function addToContainer(handle){
        if (stateContainer.length == 0){
            $rootScope.showLoader = true;
            $log.log('Start preLoader');
        }
        stateContainer.push(handle);
        $log.log('Add request:' + handle);
    }
    function removeFromContainer(handle){
        for (var i = 0; i < stateContainer.length; i++){
            if (handle == stateContainer[i]){
                stateContainer.splice(i, 1);
                break;
            }
        }
        if (stateContainer.length == 0){
            $rootScope.showLoader = false;
            $log.log('End preLoader');
        }
        $log.log('Remove request:' + handle);
    }

    //public section

    var service = {
        sendRequest : sendRequest
    }

    function sendRequest(url, data){
        var deferred = $q.defer();
        if (!data){
            data = {};
        }
        var handle =
        $http.post(url, data)
            .then(
                function (response) {
                    if (response.data.success){
                        deferred.resolve(response.data.data);
                        removeFromContainer(handle)
                    } else {
                        console.error(response.data.message);
                        deferred.reject(response);
                        removeFromContainer(handle)
                    }
                },
                function(errResponse){
                    console.error('Error while getting UserInfo');
                    deferred.reject(errResponse);
                }
            );
        addToContainer(handle);
        return deferred.promise;
    }

    return service;
}