'use sctrict';

module.exports = function ($scope, ChatService, $interval) {
    var self = this;

    self.messages = [];
    self.firstload = true;
    self.onEnter = false;
    self.sendMsg = sendMsg;
    self.getMsg = getMsg;
    self.disconnectChat = disconnectChat;
    self.chat = $interval(self.getMsg, 1000);

    function sendMsg(){
        ChatService.sendMsg(self.currentChatPartner.senderId, self.currentChatPartner.receiverId, self.chatMessage).then(function (result) {
            self.onEnter = true;
           self.getMsg();
        });
    }

    function getMsg(){
        self.currentChatPartner = ChatService.getCurrentChatPartner();
        if (self.currentChatPartner){
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
                    $('.chat-wrapper').fadeIn();
                }
                self.firstload = false;
                self.onEnter = false;

            });


        }
    }

    function disconnectChat(){
        $('.chat-wrapper').fadeOut(200);
    }
};