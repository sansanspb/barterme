'use sctrict';

module.exports = function ($scope, OrderService) {
    var self = this;

    self.order = {};
    self.categories = [
        {
            title: 'Организация PR-мероприятия',
            check: false
        },
        {
            title: 'Рассылка материалов',
            check: false
        },
        {
            title: 'Призы для конкурсов',
            check: false
        },
        {
            title: 'Реализация продукции',
            check: false
        },
        {
            title: 'Партнерства между магазинами',
            check: false
        },
        {
            title: 'Партнерство в соцсетях',
            check: false
        },
        {
            title: 'Имиджевое продвижение',
            check: false
        }
    ];

    self.sendOrder = sendOrder;

    function sendOrder() {
        var string = '';
        for (var i = 0; i < self.categories.length; i++) {
            if (self.categories[i].check) {
                string += self.categories[i].title;
                if (i != self.categories.length - 1) {
                    string += ', ';
                }
            }
        }
        self.order.categories = string.slice(0, string.length - 2);
        OrderService.send(self.order).then(function (result) {
            $('#submitRequestPopup').fadeIn(300);
            $('#submitRequestPopupWrapper').fadeIn(300);
        });
    }
};