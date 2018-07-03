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
    self.chat = $interval(self.getMsg, 30000);
    self.currentChatPartners = [];
    ChatService.getChats().then(function (value) {
        self.chats = value;
    });
    self.getChats = $interval(function(){
        ChatService.getChats().then(function (value) {
            if (self.newChat){
                value.collocutorIds.push(self.newChat.receiverId);
            }
            self.chats = value;
        });
    }, 60000);


    function sendMsg(chat){
        ChatService.sendMsg(chat.senderId, chat.receiverId, chat.sendMessage).then(function (result) {
            if (chat.receiverId == self.newChat.receiverId){
                ChatService.setCurrentChatPartner();
            }
            self.onEnter = true;
            chat.sendMessage = '';
            self.getMsg();
        });
    }

    function getMsg(){
        self.newChat = ChatService.getCurrentChatPartner();
        var companyReg;
        if (!self.chats || !self.chats.collocutorIds) return;
            if (self.chats.collocutorIds.length != 0){
                CompanyService.getInfo().then(function (result) {
                        CompanyService.getOthersCompanies().then(function (result) {
                            for (var i = 0; i < result.length; i++){
                                for (var j = 0; j < self.chats.collocutorIds.length; j++) {
                                    if (result[i].companyId == self.chats.collocutorIds[j]){
                                        for (var k = 0; k < self.currentChatPartners.length; k++){
                                            if (self.chats.collocutorIds[j] == self.currentChatPartners[k].receiverId){
                                                break;
                                            }
                                        }
                                        if (k == self.currentChatPartners.length) {
                                            self.currentChatPartners.push({
                                                senderId: self.chats.id,
                                                receiverId: self.chats.collocutorIds[j],
                                                company: result[i],
                                                messages: []
                                            });
                                        }
                                        break;
                                    }
                                }
                            }
                            if (window.localStorage.getItem('chatPartners') != null){
                                var tmpChatPartners = JSON.parse(window.localStorage.getItem('chatPartners'));
                                for (var i = 0; i < self.currentChatPartners.length; i++){
                                    for (var j = 0; j < tmpChatPartners.length; j++) {
                                        if (self.currentChatPartners[i].receiverId == tmpChatPartners[j].receiverId) {
                                            self.currentChatPartners[i].isClosed = tmpChatPartners[j].isClosed;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                window.localStorage.setItem('chatPartners', JSON.stringify(self.currentChatPartners));
                            }
                            var requests = [];
                            for (var i = 0; i < self.currentChatPartners.length; i++){
                                self.currentChatPartners[i].changed = false;
                                requests.push(
                                    ChatService.getMsg(self.currentChatPartners[i].senderId, self.currentChatPartners[i].receiverId, 0).then(function (result) {
                                        for (var l = 0; l < self.currentChatPartners.length; l++){
                                            for (var k = 0; k < result.length; k++){
                                                if (self.currentChatPartners[l].receiverId == result[k].receiverId) {
                                                    for (var j = 0; j < self.currentChatPartners[l].messages.length; j++) {
                                                        if (self.currentChatPartners[l].messages[j].chatHistoryId == result[k].chatHistoryId) {
                                                            break;
                                                        }
                                                    }
                                                    if (j == self.currentChatPartners[l].messages.length) {
                                                        self.currentChatPartners[l].messages.push(result[k]);
                                                        self.currentChatPartners[l].changed = true;
                                                    }
                                                }
                                            }
                                        }
                                    })
                                );
                                requests.push(
                                    ChatService.getMsg(self.currentChatPartners[i].receiverId, self.currentChatPartners[i].senderId, 0).then(function (result) {
                                        for (var l = 0; l < self.currentChatPartners.length; l++){
                                            for (var k = 0; k < result.length; k++){
                                                if (self.currentChatPartners[l].receiverId == result[k].senderId) {
                                                    for (var j = 0; j < self.currentChatPartners[l].messages.length; j++) {
                                                        if (self.currentChatPartners[l].messages[j].chatHistoryId == result[k].chatHistoryId) {
                                                            break;
                                                        }
                                                    }
                                                    if (j == self.currentChatPartners[l].messages.length) {
                                                        self.currentChatPartners[l].messages.push(result[k]);
                                                        self.currentChatPartners[l].changed = true;
                                                    }
                                                }
                                            }
                                        }
                                    })
                                );
                            }
                            Promise.all(requests).then(function(result){
                                $('.chat-scroll').mCustomScrollbar();
                                for (var k = 0; k < self.currentChatPartners.length; k++){
                                    if (self.currentChatPartners[k].changed){
                                        var messages = self.currentChatPartners[k].messages;
                                        if (messages.length != 0) {
                                            for (var i = 0; i < messages.length; i++) {
                                                for (var j = i + 1; j < messages.length; j++) {
                                                    if (+messages[j].sendDate < +messages[i].sendDate) {
                                                        var buff = Object.assign({}, messages[j]);
                                                        messages[j] = Object.assign({}, messages[i]);
                                                        messages[i] = Object.assign({}, buff);
                                                    }
                                                }
                                            }
                                            var showDate = new Date(messages[0].sendDate);
                                            messages[0].showDate = true;
                                            showDate.setSeconds(0);
                                            showDate.setMinutes(0);
                                            showDate.setHours(0);
                                            for (var i = 1; i < messages.length; i++) {
                                                var currDate = new Date(messages[i].sendDate);
                                                currDate.setSeconds(0);
                                                currDate.setMinutes(0);
                                                currDate.setHours(0);
                                                if (+showDate + 60 * 60 * 24 < +currDate) {
                                                    messages[i].showDate = true;
                                                    showDate = new Date(messages[i].sendDate);
                                                    showDate.setSeconds(0);
                                                    showDate.setMinutes(0);
                                                    showDate.setHours(0);
                                                }
                                            }
                                        }
                                        self.currentChatPartners[k].messages = messages;
                                        $('#' + self.currentChatPartners[k].receiverId).mCustomScrollbar("scrollTo", "bottom");
                                    }
                                }
                            });
                        })
                    }
                );
            }

        Promise.all([companyReg]).then(function (result) {
        });
        /*Promise.all(requests).then(function(good){

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
        if (!self.currentChatPartner || !tmpChatPartner){

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
        }*/
    }

    function disconnectChat(){
        window.localStorage.removeItem('chatPartner');
        self.currentChatPartner = undefined;
        ChatService.setCurrentChatPartner(self.currentChatPartner);
    }

    function shrinkChat(chat){
        chat.isClosed = !chat.isClosed;
        window.localStorage.setItem('chatPartners', JSON.stringify(self.currentChatPartners));
    }
};