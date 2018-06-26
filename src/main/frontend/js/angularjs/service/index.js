'use strict';


angular.module('barterme')
    .service('CompanyService', require('./company_service.js'))
    .service('AuthService', require('./auth_service.js'))
    .service('CategoriesService', require('./categories_service.js'))
    .service('MarketingChannelsService', require('./marketingChannels_service.js'))
    .service('CustomService', require('./custom_service.js'))
    .service('ReviewService', require('./review_service.js'))
    .service('PreloaderService', require('./preloader_service.js'))
    .service('OrderService', require('./order_service.js'))
    .service('ChatService', require('./chat_service.js'));
