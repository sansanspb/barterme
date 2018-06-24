'use strict';


angular.module('barterme')
    .filter('regionsFilter', function () {
        return function (values, char) {
            var newvalues = [];
            if (!values) return newvalues;
            for (var i = 0; i < values.length; i++) {
                if (values[i].title[0] === char) {
                    newvalues.push(values[i]);
                }
            }
            return newvalues;
        };
    })
    .filter('withoutCompany', function () {
        return function (values, companyId) {
            var newvalues = [];
            if (!values) return newvalues;
            for (var i = 0; i < values.length; i++) {
                if (values[i].companyId != companyId) {
                    newvalues.push(values[i]);
                }
            }
            return newvalues;
        };
    })
    .filter('partnersFilter', function () {
        return function (values, companyId) {
            var newvalues = [];
            if (!values) return newvalues;
            for (var i = 0; i < values.length; i++) {
                if (values[i].receiverId == companyId || values[i].senderId == companyId) {
                    newvalues.push(values[i]);
                }
            }
            return newvalues;
        };
    })
    .filter('reviewFilter', function () {
        return function (values) {
            var newvalues = [];
            if (!values) return newvalues;
            for (var i = 3; i < values.length; i++) {
                newvalues.push(values[i]);
            }
            return newvalues;
        };
    })
    .filter('marketingChannels', function () {
        return function (values, type) {
            if (!values) return [];
            var newvalues = [],
                middle = Math.floor(values.length / 2);

            if (type == 1) {
                for (var i = 0; i < middle; i++) {
                    newvalues.push(values[i]);
                }
            } else {
                for (var i = middle + 1; i < values.length; i++) {
                    newvalues.push(values[i]);
                }
            }
            return newvalues;
        };
    });