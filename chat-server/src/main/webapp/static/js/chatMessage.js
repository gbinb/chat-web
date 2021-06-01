/**
 * 聊天信息对象
 * @author guobingbing
 * @wechat t_gbinb
 * @constructor
 */
function ChatMessage() {
    this.userId = ''; //用户ID
    this.panel = $("<ul class='list-unstyled'></ul>"); //用户消息面板
    this.cacheList = new Array(); //消息列表
}

/**
 * 接收信息
 * @param sender 发送者
 * @param chat 消息体
 */
ChatMessage.prototype.receiveMessage = function (sender, chat) {
    let that = this;
    let receiveBox = that.createMessageBox(1, sender, chat);
    receiveBox.appendTo(that.panel);
    if(chat.chatMessage.at>0){
        setTimeout(function () {
            receiveBox.remove();
        }, chat.chatMessage.at*1000);
    }
    return that.panel;
};

/**
 * 创建消息气泡
 * @param type 1-收信 2-发信
 * @param sender
 * @param chat
 */
ChatMessage.prototype.createMessageBox = function(type, sender, chat){
    let sb = new StringBuilder();
    let cssBox = (type===1?"received":"sent");
    sb.append("<li class='media ").append(cssBox).append("' id='").append(chat.chatMessage.mid).append("'>");
    if(type===1){
        sb.append("<div class='avatar'>");
        sb.append("<img src='").append(sender.avatar).append("' alt='avatar' class='avatar-img rounded-circle'>");
        sb.append("</div>");
    }
    sb.append("<div class='media-body'>");
    sb.append("<div class='msg-box'>");
    sb.append("<div>");
    if(chat.chatMessage.status===4){
        sb.append("<p class='text-red'>当前已不是好友</p>");
    }else{
        if(chat.chatMessage.type===10){
            sb.append("<p>").append(chat.chatMessage.content.text).append("</p>");
        }else if(chat.chatMessage.type===20){
            sb.append("<div class='chat-msg-attachments'>");
            sb.append("<div class='chat-attachment'>");
            sb.append("<img src='").append(chat.chatMessage.content.image.url);
            sb.append("' alt='").append(chat.chatMessage.content.image.oriName).append("'>");
            sb.append("</div></div>");
        }
    }
    sb.append("<ul class='chat-msg-info'>");
    sb.append("<li><div class='chat-time'><span>");
    if(chat.chatMessage.rid.indexOf('G')===0){
        sb.append(sender.nickName).append(" &middot; ");
    }
    sb.append(dateFormat(chat.rt)).append("</span></div></li>");
    sb.append("</ul>");
    sb.append("</div>");
    sb.append("</div></div></li>");
    return $(sb.toString());
};

/**
 * 缓存消息
 * @param sender
 * @param chat
 */
ChatMessage.prototype.cacheMessage = function (sender, chat) {
    let that = this;
    let receiveBox = that.createMessageBox(1, sender, chat);
    receiveBox.chat = chat;
    that.cacheList.push(receiveBox);
};

/**
 * 弹出所有消息并显示
 */
ChatMessage.prototype.popAllMessage = function () {
    let that = this;
    while(that.cacheList.length>0){
        let receiveBox = that.cacheList.shift();
        receiveBox.appendTo(that.panel);
        let chat = receiveBox.chat;
        if(chat.chatMessage.at>0){
            setTimeout(function () {
                receiveBox.remove();
            }, chat.chatMessage.at*1000);
        }
    }
    return that.panel;
};

/**
 * 发送消息
 * @param sender
 * @param chat
 */
ChatMessage.prototype.sendMessage = function (sender, chat) {
    let that = this;
    let sendBox = that.createMessageBox(2, sender, chat);
    sendBox.appendTo(that.panel);
    if(chat.chatMessage.at>0){
        setTimeout(function () {
            sendBox.remove();
        }, chat.chatMessage.at*1000);
    }
    return that.panel;
};
