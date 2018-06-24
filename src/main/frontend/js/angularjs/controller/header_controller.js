'use sctrict';

module.exports = function ($scope, CompanyService, AuthService) {
    var self = this;


    self.showUnreg = false;
    self.showReg = false;
    self.showBell = false;
    self.showRegCode = false;
    self.showHead = false;
    self.loadHead = loadHead;
    self.getTitle = getTitle;
    self.readNotification = readNotification;
    self.showLogin = showLogin;
    self.showReg = showReg;
    self.isCabinetLocation = isCabinetLocation;

    function loadHead() {
        AuthService.getUserInfo().then(
            function (accept) {
                CompanyService.getOthersCompanies().then(function (result) {
                    CompanyService.getPartnerNotifications().then(function (result) {
                        self.partnerNotifications = CompanyService.getNotifications();
                    });
                });
                self.showHead = true;
                self.showBell = self.showReg = AuthService.reg && !AuthService.regCode;
                self.showRegCode = self.showUnreg = AuthService.reg && AuthService.regCode;
            },
            function (reject) {
                self.showHead = true;
                self.showUnreg = !AuthService.reg && !AuthService.regCode;
                self.showBell = self.showReg = AuthService.reg && !AuthService.regCode;
                self.showRegCode = AuthService.reg && AuthService.regCode;
            }
        )
    }

    function getTitle(notif) {
        if (notif.status == 'WAIT') {
            return 'Новая заявка';
        }
        if (notif.status == 'ACTIVE') {
            return 'Успех';
        }
        if (notif.status == 'REJECTED') {
            return 'Отказ';
        }
    }

    function readNotification(notif) {
        CompanyService.setPartnerNotificationReaded(notif.partnersNotificationId).then(function (result) {
            CompanyService.getOthersCompanies().then(function (result) {
                CompanyService.getPartnerNotifications().then(function (result) {
                    self.partnerNotifications = CompanyService.getNotifications();
                });
            });
        });
    }

    function showLogin() {
        if (self.showRegCode) {
            $('#regPopupWrapper').fadeIn(300);
            $('#codePopup').fadeIn(300);
        } else {
            $('#regPopupWrapper').fadeIn(300);
            $('#entrancePopup').fadeIn(300);
        }
    }

    function showReg() {
        $('#regPopupWrapper').fadeIn(300);
        $('#regPopup').fadeIn(300);
    }

    function isCabinetLocation() {
        if (window.location.href.indexOf('cabinet') != -1) {
            return true;
        }
        return false;
    }

};