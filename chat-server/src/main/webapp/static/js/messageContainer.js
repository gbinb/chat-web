/**
 * 消息容器
 * @constructor
 */
function MessageContainer() {
    this.userMap = new Map();
}

/**
 * 存储消息
 * @param chat
 */
MessageContainer.prototype.add = function (userId, chat) {
    let that = this;
    let chatList = that.userMap.get(userId);
    if(!chatList){
        chatList = new Array();
        chatList.push(chat);
        that.userMap.set(userId, chatList);
    }else{
        chatList.push(chat);
    }
};

/**
 * 获取消息列表
 * @param userId
 */
MessageContainer.prototype.getList = function (userId) {
    return this.userMap.get(userId);
};