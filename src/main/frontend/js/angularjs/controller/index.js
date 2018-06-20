'use strict';


    angular.module('barterme')
        .controller('CabinetController', require('./cabinet_controller.js'))
        .controller('CompanyController', require('./company_controller.js'))
        .controller('HeaderController', require('./header_controller.js'))
        .controller('OrderController', require('./order_controller.js'));
