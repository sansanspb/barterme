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
                    $('.chat-scroll').mCustomScrollbar("scrollTo", "bottom");
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