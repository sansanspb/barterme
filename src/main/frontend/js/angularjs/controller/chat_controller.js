'use sctrict';

module.exports = function ($scope, ChatService, $interval, CompanyService) {
    var self = this;

    self.messages = [];
    self.firstload = true;
    self.onEnter = false;
    self.sendMsg = sendMsg;
    self.getMsg = getMsg;
    self.disconnectChat = disconnectChat;
    self.shrinkChat = shrinkChat;
    self.chat = $interval(self.getMsg, 3000);
    self.currentChatPartners = [];
    ChatService.getChats().then(function (value) {
        self.chats = value;
    });
    self.getChats = $interval(function(){
        ChatService.getChats().then(function (value) {
            self.chats = value;
        });
    }, 60000);


    function sendMsg(){
        ChatService.sendMsg(self.currentChatPartner.senderId, self.currentChatPartner.receiverId, self.chatMessage).then(function (result) {
            self.onEnter = true;
            self.chatMessage = '';
            self.getMsg();
        });
    }

    function getMsg(){
        var tmpChatPartner = ChatService.getCurrentChatPartner();
        if (!self.currentChatPartner || !tmpChatPartner){
            if (self.chats.collocutorIds.length != 0){
                CompanyService.getInfo().then(function (result) {
                        CompanyService.getOthersCompanies().then(function (result) {
                            self.currentChatPartners = [];
                            for (var i = 0; i < result.length; i++){
                                for (var j = 0; j < self.chats.collocutorIds.length; j++) {
                                    if (result[i].companyId == self.chats.collocutorIds[j]){
                                        self.currentChatPartners.push({
                                            senderId :  self.chats.id,
                                            receiverId : self.chats.collocutorIds[j],
                                            company : result[i]
                                        });
                                        break;
                                    }
                                }
                            }
                            if (window.localStorage.getItem('chatPartner') != null){
                                self.currentChatPartners[self.currentChatPartners.length-1].isClosed = JSON.parse(window.localStorage.getItem('chatPartner')).isClosed;
                                self.currentChatPartner = self.currentChatPartners[self.currentChatPartners.length-1];
                                ChatService.setCurrentChatPartner(self.currentChatPartner);
                                window.localStorage.setItem('chatPartner', JSON.stringify(self.currentChatPartner));
                            } else {
                                self.currentChatPartner = self.currentChatPartners[self.currentChatPartners.length-1];
                                ChatService.setCurrentChatPartner(self.currentChatPartner);
                                window.localStorage.setItem('chatPartner', JSON.stringify(self.currentChatPartner));
                            }
                        });
                    }
                );
            } else {
                self.currentChatPartner = tmpChatPartner;
            }
        }
        if (self.currentChatPartner){
            if (tmpChatPartner.receiverId != self.currentChatPartner.receiverId){
                self.firstload = true;
                self.messages = [];
                self.currentChatPartner = tmpChatPartner;
                window.localStorage.setItem('chatPartner', JSON.stringify(self.currentChatPartner));
            }
            Promise.all([
                ChatService.getMsg(self.currentChatPartner.senderId, self.currentChatPartner.receiverId, 0).then(function (result) {
                    for (var i = 0; i < result.length; i++){
                        for (var j = 0; j < self.messages.length; j++){
                            if (self.messages[j].chatHistoryId == result[i].chatHistoryId){
                                break;
                            }
                        }
                        if (j == self.messages.length){
                            self.messages.push(result[i]);
                        }
                    }
                }),
                ChatService.getMsg(self.currentChatPartner.receiverId, self.currentChatPartner.senderId, 0).then(function (result) {
                    for (var i = 0; i < result.length; i++){
                        for (var j = 0; j < self.messages.length; j++){
                            if (self.messages[j].chatHistoryId == result[i].chatHistoryId){
                                break;
                            }
                        }
                        if (j == self.messages.length){
                            self.messages.push(result[i]);
                        }
                    }
                })
            ]).then(function(good){
                if (self.onEnter || self.firstload) {
                    if (self.messages.length != 0) {
                        var startDate = new Date(self.messages[0].sendDate),
                            minI = 0;
                        for (var i = 0; i < self.messages.length; i++) {
                            for (var j = i + 1; j < self.messages.length; j++) {
                                if (+self.messages[j].sendDate < +self.messages[i].sendDate) {
                                    var buff = Object.assign({}, self.messages[j]);
                                    self.messages[j] = Object.assign({}, self.messages[i]);
                                    self.messages[i] = Object.assign({}, buff);
                                }
                            }
                        }
                        var showDate = new Date(self.messages[0].sendDate);
                        self.messages[0].showDate = true;
                        showDate.setSeconds(0);
                        showDate.setMinutes(0);
                        showDate.setHours(0);
                        for (var i = 1; i < self.messages.length; i++) {
                            var currDate = new Date(self.messages[i].sendDate);
                            currDate.setSeconds(0);
                            currDate.setMinutes(0);
                            currDate.setHours(0);
                            if (+showDate + 60 * 60 * 24 < +currDate) {
                                self.messages[i].showDate = true;
                                showDate = new Date(self.messages[i].sendDate);
                                showDate.setSeconds(0);
                                showDate.setMinutes(0);
                                showDate.setHours(0);
                            }
                        }
                    }
                    $('.chat-scroll').mCustomScrollbar("scrollTo", "bottom");
                }
                if (self.firstload){
                    if (window.localStorage.getItem('chatPartner') == null){
                        self.currentChatPartner.isClosed = false;
                        window.localStorage.setItem('chatPartner', JSON.stringify(self.currentChatPartner));
                    }
                }
                self.firstload = false;
                self.onEnter = false;
            });
        }
    }

    function disconnectChat(){
        window.localStorage.removeItem('chatPartner');
        self.currentChatPartner = undefined;
        ChatService.setCurrentChatPartner(self.currentChatPartner);
    }

    function shrinkChat(){
        self.currentChatPartner.isClosed = !self.currentChatPartner.isClosed;
        window.localStorage.setItem('chatPartner', JSON.stringify(self.currentChatPartner));
    }
};