/**
 * 聊天业务类
 * @author guobingbing
 * @wechat t_gbinb
 * @constructor
 */
function Chat() {
    this.token = "";
    this.userId = "";
    this.nickName = "";
    this.avatar = "";
    this.country = "";
    this.chatUserId = "";
    this.friendMap = new Map();
    this.chatMap = new Map();
    this.messageContainer = $('#div_message_container');
    this.strangerMap = new Map(); //陌生人
    this.aliveTime = 0; //5秒后销毁
    this.httpUrl = "http://localhost:5001/chat/service/v1";
    this.uploadUrl = "http://localhost:5001/chat/file/upload";
    this.socketUrl = "ws://localhost:5002/chat";
    // this.httpUrl = "http://chat.fetosoft.cn:8000/chat/service/v1";
    // this.uploadUrl = "http://chat.fetosoft.cn:8000/chat/file/upload";
    // this.socketUrl = "ws://chat-api.fetosoft.cn:8000/chat";
    this.socket = null;
}

/**
 * 检查是否已经登录
 */
Chat.prototype.checkLogin = function(){
    let that = this;
    let token = getCookie('loginToken');
    if(token!==undefined && token!==''){
        that.token = token;
        let ly = layer.load(1, {shade: [0.1,'#000']});
        that.postData("refreshToken", {}, function (result, res) {
            layer.close(ly);
            if(result){
                if(res.code==='200'){
                    that.userId = res.data.userId;
                    that.nickName = res.data.nickName;
                    that.avatar = res.data.avatar;
                    that.country = res.data.country;
                    that.loginSwitch(true);
                }else{
                    layer.alert(res.message);
                    setCookie('loginToken', '', -1);
                }
            }else{
                console.log(res);
            }
        });
    }else{
        $('#modal-login').modal('show');
    }
};

/**
 * 发送验证码
 */
Chat.prototype.sendSmsCode = function(){
    let that = this;
    let mobile = $('#mobile').val();
    if(mobile===''){
        layer.alert("请输入手机号！");
        return;
    }
    let data = {"mobile": mobile};
    let ly = layer.load(1, {shade: [0.1,'#000']});
    let sendButton = $('#btn_getSmsCode');
    sendButton.attr('disabled', true);
    that.postData("sendSmsCode", data, function (result, res) {
        layer.close(ly);
        if(result){
            if(res.code==='200'){
                let total = 60;
                sendButton.val('(60)S');
                let interval = setInterval(function () {
                    total--;
                    if (total>0){
                        sendButton.val('('+total+')S');
                    }else{
                        clearInterval(interval);
                        sendButton.val('获取验证码');
                        sendButton.attr('disabled', false);
                    }
                },1000);
            }else{
                layer.alert(res.message);
                sendButton.attr('disabled', false);
            }
        }else{
            sendButton.attr('disabled', false);
            console.log(res);
        }
    });
};

/**
 * 验证手机号
 */
Chat.prototype.verifyMobile = function(){
    let that = this;
    $("#verifyMobileForm").validate({
        rules: {
            mobile:{
                required: true,
                minlength: 11,
                mobile: true
            },
            smsCode:{
                required: true,
                minlength: 6
            }
        },
        messages: {
            mobile:{
                required: "请输入11位手机号",
                minlength: "请输入11位手机号",
                mobile: "手机号格式不正确"
            },
            smsCode:{
                required: "请输入验证码",
                minlength: "验证码长度6位"
            }
        },
        submitHandler: function() {
            let ly = layer.load(1, {shade: [0.1,'#000']});
            let data = $('#verifyMobileForm').serializeJSONNoNull();
            that.postData("verifyMobile", data, function (result, res) {
                layer.close(ly);
                if(result){
                    if(res.code==='200'){
                        let authCode = res.data.authCode;
                        $('#authCode').val(authCode);
                        $('#div_verifyMobile').css('display','none');
                        $('#btn_nextStep').css('display', 'none');
                        $('#div_register').css('display', 'block');
                        $('#btn_register').css('display', 'block');
                    }else{
                        layer.alert(res.message);
                    }
                }else{
                    console.log(res);
                }
            });
        }
    });
};

/**
 * 登录
 */
Chat.prototype.login = function () {
    let that = this;
    $("#loginForm").validate({
        rules: {
            loginMobile:{
                required: true,
                minlength: 11,
                mobile: true
            },
            loginPassword:{
                required: true,
                minlength: 6
            }
        },
        messages: {
            loginMobile:{
                required: "请输入11位手机号",
                minlength: "请输入11位手机号",
                mobile: "手机号格式不正确"
            },
            loginPassword:{
                required: "请输入密码",
                minlength: "密码长度6-20位"
            }
        },
        submitHandler: function() {
            let ly = layer.load(1, {shade: [0.1,'#000']});
            let username = $('#loginMobile').val();
            let password = $('#loginPassword').val();
            let data = {"type":2, "mobile":username, "passWord":password};
            that.postData("userLogin", data, function (result, res) {
                layer.close(ly);
                if(result){
                    if(res.code==='200'){
                        that.userId = res.data.userId;
                        that.token = res.data.token;
                        that.nickName = res.data.nickName;
                        that.avatar = res.data.avatar;
                        that.country = res.data.country;
                        $('#loginMobile').val('');
                        $('#loginPassword').val('');

                        that.loginSwitch(true);
                    }else{
                        layer.alert(res.message);
                    }
                }else{
                    console.log(res);
                }
            });
        }
    });
};

/**
 * 登出
 */
Chat.prototype.logout = function (){
    let that = this;
    let ly = layer.confirm('确定要注销码？', {
        btn: ['是','否'] //按钮
    }, function(){
        setCookie('loginToken', '', -1);
        that.socket.close();
        layer.close(ly);
        $('#modal-login').modal('show');
    }, function(){
    });
};

/**
 * 登录切换
 */
Chat.prototype.loginSwitch = function(isLogin){
    let that = this;
    if(isLogin){
        $('#div_user_login').addClass('display-none');
        $('#div_user_profile').removeClass('display-none');
        $('#div_system_notify').removeClass('display-none');

        $('#img_user_avatar').attr('src', that.avatar);
        $('#span_nick_name').text(that.nickName);
        $('#modal-login').modal('hide');
        //设置token
        setCookie('loginToken', that.token, 30);
        let at = getCookie('settingsAt');
        if(at){
            that.aliveTime = parseInt(at);
        }else{
            that.aliveTime = 0;
            setCookie('settingsAt', 0);
        }
        $('#settings_at').val(that.aliveTime);

        that.getMyFriends();
        that.getApplyFriend();
    }else{
        this.socket = null;
        that.friendMap.clear();
        that.chatMap.clear();
        that.token = '';
        that.nickName = '';
        that.avatar = '';
        that.country = '';
        that.chatUserId = '';

        $('#div_user_login').removeClass('display-none');
        $('#div_user_profile').addClass('display-none');
        $('#div_system_notify').addClass('display-none');

        $('#img_user_avatar').attr('src', '');
        $('#span_nick_name').text('');
        $('#div_friends').empty();
        $('#div_message').empty();

        that.clearMessageContainer();
    }
};

/**
 * 连接socket
 */
Chat.prototype.connectWebSocket = function(){
    let that = this;
    that.socket = new WebSocket(that.socketUrl + '/' + that.token);
    that.socket.onopen = function () {
        console.log("连接建立成功...");
    };
    that.socket.onclose = function (e) {
        console.log("连接关闭...");
        console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean);
        setCookie('loginToken', '', -1);
        console.log(e);
        that.loginSwitch(false);
    };
    that.socket.onerror = function () {
        console.log("发生错误...");
    };
    that.socket.onmessage = function (e) {
        that.receiveMessage(e.data);
    };
};

/**
 * 获取ChatMessage对象
 * @param rid 消息接收者ID
 * @returns {any}
 */
Chat.prototype.getChatMessage = function(rid) {
    let that = this;
    let chatMessage = that.chatMap.get(rid);
    if(!chatMessage){
        chatMessage = new ChatMessage();
        that.chatMap.set(rid, chatMessage);
    }
    return chatMessage;
};

Chat.prototype.getMyFriends = function() {
    let that = this;
    that.postData('getMyFriends', {}, function (result, res) {
        if(result){
            let friends = res.data.list;
            let friendContainer = $('#div_friends');
            $.each(friends, function (index, obj) {
                let friend = {};
                friend.total = 0;
                friend.userId = obj.user.userId;
                friend.nickName = obj.alias===''?obj.user.nickName:obj.alias;
                friend.avatar = obj.user.avatar;
                friend.country = obj.user.country;
                friend.mobile = obj.user.mobile;
                friend.alias = obj.alias;
                that.friendMap.set(friend.userId, friend);
                friendContainer.append(that.buildFriend(friend));
            });
            that.getMyGroups();
        }
    });
};

/**
 * 创建朋友信息
 * @param friend
 */
Chat.prototype.buildFriend = function(friend){
    let sb = new StringBuilder();
    sb.append("<a id='friend_item_").append(friend.userId).append("' href='#' onclick=chat.openChat('").append(friend.userId).append("') class='media'>");
    sb.append("<div class='media-img-wrap'>");
    sb.append("<div class='avatar'>");
    sb.append("<img src='").append(friend.avatar).append("' alt='Avatar' class='avatar-img rounded-circle'>");
    sb.append("</div></div>");
    sb.append("<div class='media-body'>");
    sb.append("<div>");
    sb.append("<div id='friend_name_").append(friend.userId).append("' class='user-name' style='text-align: left'>").append(friend.nickName).append("</div>");
    if(friend.userId.indexOf('G')===0){
        sb.append("<div class='user-last-chat' style='text-align: left'>").append(friend.total).append(" 位成员</div>");
    }else{
        sb.append("<div class='user-last-chat' style='text-align: left'>").append(friend.mobile).append("</div>");
    }
    sb.append("</div>");
    sb.append("<div>");
    sb.append("<div id='message_total_").append(friend.userId).append("' class='badge badge-success badge-pill display-none'>0</div>");
    sb.append("</div></div>");
    sb.append("</a>");
    return sb.toString();
};

/**
 * 获取群信息
 */
Chat.prototype.getMyGroups = function() {
    let that = this;
    that.postData('getMyGroup', {}, function (result, res) {
        if(result){
            let groups = res.data.list;
            let friendContainer = $('#div_friends');
            $.each(groups, function (index, group) {
                let friend = {};
                friend.userId = group.groupId;
                friend.nickName = group.name;
                friend.avatar = group.avatar;
                friend.country = '';
                friend.mobile = '';
                friend.total = group.total;
                friend.ownerId = group.ownerId;
                that.friendMap.set(friend.userId, friend);
                friendContainer.append(that.buildFriend(friend));
            });

            that.connectWebSocket();
        }
    });
};

/**
 * 打开聊天对话框
 * @param userId
 */
Chat.prototype.openChat = function(userId) {
    let that = this;
    let friend = that.friendMap.get(userId);

    let chatMessage = that.getChatMessage(userId);
    if(that.chatUserId==='' || that.chatUserId!==userId){
        that.messageContainer.empty();
        chatMessage.popAllMessage().appendTo(that.messageContainer);
        that.messagePanelScrollTop(that.messageContainer);

        let messageTotal = $('#message_total_' + userId);
        messageTotal.text("0");
        messageTotal.addClass("display-none");
    }

    that.chatUserId = friend.userId;
    $('#chat_user_avatar').attr('src', friend.avatar);
    if(that.aliveTime>0){
        $('#chat_user_nickName').text(friend.nickName + ' -  开启阅后' + that.aliveTime + '秒自焚模式');
    }else{
        $('#chat_user_nickName').text(friend.nickName);
    }

    //添加菜单
    that.addContextMenu(friend);
};

/**
 * 添加好友操作菜单
 * @param friend
 */
Chat.prototype.addContextMenu = function(friend) {
    let that = this;
    let menuContainer = $('#contextMenu_container');
    menuContainer.empty();
    let menuProfile = $("<a href='javascript:void(0);' class='dropdown-item mt-2'>查看资料</a>");
    menuProfile.appendTo(menuContainer);
    if(friend.userId.indexOf('G')===0){
        menuProfile.unbind('click').bind('click', friend, function (e) {
            that.previewGroupProfile(e.data);
        });
        if(friend.ownerId === that.userId){
            let menuDismiss = $("<a href='javascript:void(0);' class='dropdown-item mt-2'>解散群聊</a>");
            menuDismiss.unbind('click').bind('click', friend, function (e) {
                that.quitGroup(e.data);
            });
            menuDismiss.appendTo(menuContainer);
        }else{
            let menuDismiss = $("<a href='javascript:void(0);' class='dropdown-item mt-2'>退出群聊</a>");
            menuDismiss.unbind('click').bind('click', friend, function (e) {
               that.quitGroup(e.data);
            });
            menuDismiss.appendTo(menuContainer);
        }
    }else{
        menuProfile.unbind('click').bind('click', friend, function (e) {
            that.previewFriendProfile(e.data);
        });
        let menuDelFriend = $("<a href='javascript:void(0);' class='dropdown-item mt-2'>删除好友</a>");
        menuDelFriend.unbind('click').bind('click', friend, function (e) {
            that.removeFriend(e.data);
        });
        menuDelFriend.appendTo(menuContainer);
    }
};

/**
 * 发送信息
 */
Chat.prototype.sendMessage = function(){
    let that = this;
    // 当websocket状态打开
    if(that.socket===null){
        layer.alert('请先登录！');
        return;
    }
    if(that.socket.readyState === WebSocket.OPEN){
        if(that.chatUserId===''){
            layer.alert('请选择一个聊天对象！');
            return;
        }
        let chatMessage = $('#text_message').val();
        if(chatMessage===''){
            layer.alert('消息不能为空！');
            return;
        }
        let chatMode = 1;
        if(that.chatUserId.indexOf('G')===0){
            chatMode = 2;
        }
        let message = {
            'mode': chatMode,
            'rt': new Date().getTime(),
            'chatMessage': {
                'type': 10,
                'mid': uuid(),
                'sid': that.userId,
                'rid': that.chatUserId,
                'at': that.aliveTime,
                'content': {
                    'text': chatMessage
                }
            }
        };
        this.socket.send(JSON.stringify(message));
        this.showSendMessage(message);
        $('#text_message').val('');
    }else{
        console.log("连接没有开启");
    }
};

/**
 * 接收信息
 */
Chat.prototype.receiveMessage = function(data){
    let that = this;
    let jsonArr =JSON.parse(data);
    $.each(jsonArr, function (index, chat) {
        if(chat.mode===1){
            that.receiveSingleChatMessage(chat);
        }else if(chat.mode===2){
            if(that.userId===chat.chatMessage.sid){
                return;
            }
            let friend = that.friendMap.get(chat.chatMessage.sid);
            if(!friend){
                friend = that.strangerMap.get(chat.chatMessage.sid);
                if(!friend){
                    that.postData("getProfile", {"targetUserId": chat.chatMessage.sid}, function (result, res) {
                        if(result && res.code === '200') {
                            let friend = {};
                            friend.total = 0;
                            friend.userId = res.data.userId;
                            friend.nickName = res.data.nickName;
                            friend.avatar = res.data.avatar;
                            friend.country = res.data.country;
                            friend.mobile = '';
                            that.strangerMap.set(friend.userId, friend);
                            that.receiveGroupChatMessage(chat, friend);
                        }
                    });
                }else{
                    that.receiveGroupChatMessage(chat, friend);
                }
            }else{
                that.receiveGroupChatMessage(chat, friend);
            }

        }else if(chat.mode===4 || chat.mode===5){
            that.receiveNotification(chat);
        }else if(chat.mode===6 || chat.mode===7){
            that.receiveNotification(chat);
        }
    });
};

/**
 * 接收单人聊天信息
 */
Chat.prototype.receiveSingleChatMessage = function(chat){
    let that = this;
    if(that.userId===chat.chatMessage.sid){
        if(chat.chatMessage.status===4){
            that.showSendMessage(chat);
        }
        return;
    }

    let friend = that.friendMap.get(chat.chatMessage.sid);
    let chatMessage = that.getChatMessage(chat.chatMessage.sid);

    if(that.chatUserId===chat.chatMessage.sid){
        that.messageContainer.empty();
        chatMessage.receiveMessage(friend, chat).appendTo(that.messageContainer);
        that.messagePanelScrollTop(that.messageContainer);
    }else{
        let msgTotal = $('#message_total_' + chat.chatMessage.sid);
        msgTotal.text(parseInt(msgTotal.text()) + 1);
        msgTotal.removeClass('display-none');
        //将消息缓存
        chatMessage.cacheMessage(friend, chat);
    }
};

/**
 * 接收群信息
 * @param chat
 * @param friend
 */
Chat.prototype.receiveGroupChatMessage = function(chat, friend){
    let that = this;
    let chatMessage = that.getChatMessage(chat.chatMessage.rid);
    if(that.chatUserId===chat.chatMessage.rid){
        that.messageContainer.empty();
        chatMessage.receiveMessage(friend, chat).appendTo(that.messageContainer);
        that.messagePanelScrollTop(that.messageContainer);
    }else{
        let msgTotal = $('#message_total_' + chat.chatMessage.rid);
        msgTotal.text(parseInt(msgTotal.text()) + 1);
        msgTotal.removeClass('display-none');
        chatMessage.cacheMessage(friend, chat);
    }
};

/**
 * 接收通知信息
 * @param chat
 */
Chat.prototype.receiveNotification = function(chat) {
    let that = this;
    if(that.userId===chat.chatMessage.sid){
        return;
    }
    if(chat.mode===4){
        that.getApplyFriend();
    }else if(chat.mode===5){
        that.applyFriendResult(chat);
    }else if(chat.mode===6){
        that.acceptGroupInvite(chat);
    }else if(chat.mode===7){
        that.acceptGroupInvite(chat);
    }
};

/**
 * 显示发送的信息
 */
Chat.prototype.showSendMessage = function(chat){
    let that = this;
    let chatMessage = that.getChatMessage(chat.chatMessage.rid);
    if(that.chatUserId===chat.chatMessage.rid){
        that.messageContainer.empty();
        let sender = {
            "userId": that.userId,
            "nickName": that.nickName,
            "avatar": that.avatar,
            "country": that.country,
            "total": 0
        };
        chatMessage.sendMessage(sender, chat).appendTo(that.messageContainer);
        that.messagePanelScrollTop(that.messageContainer);
    }
};

/**
 * 消息面板滚动
 */
Chat.prototype.messagePanelScrollTop = function(messageContainer){
    let maxHeight = parseInt(messageContainer.css('max-height'));
    let dynamicHeight = parseInt(messageContainer.css('height'));
    let scrollTop = messageContainer.scrollTop();
    if(dynamicHeight===maxHeight){
        messageContainer.animate({"scrollTop": (dynamicHeight+scrollTop) + 'px'}, 1000);
    }
};

/**
 * Post json数据
 */
Chat.prototype.postData = function (action, data, callbackFun){
    let that = this;
    let jsonData = {
        "action": action,
        "token": that.token,
        "data": data
    };
    $.ajax({
        type: 'POST',
        url: that.httpUrl,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(jsonData),
        dataType: "json",
        success: function (message) {
            callbackFun(true, message);
        },
        error: function (message) {
            callbackFun(false, message);
        }
    });
};

/**
 * 注册
 */
Chat.prototype.register = function() {
    let that = this;
    $("#registForm").validate({
        rules: {
            nickName:{
                required: true,
                minlength: 1
            },
            password:{
                required: true,
                minlength: 6
            },
            confirmPassword:{
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            nickName:{
                required: "请输入昵称",
                minlength: "至少一位字符"
            },
            password:{
                required: "请输入密码",
                minlength: "密码长度6-20位"
            },
            confirmPassword:{
                required: "请输入确认密码",
                equalTo: "两次输入的密码不一致"
            }
        },
        submitHandler: function() {
            let ly = layer.load(1, {
                shade: [0.1,'#000']
            });
            let body = $('#registForm').serializeJSONNoNull();
            that.postData("userRegister", body, function (result, res) {
                layer.close(ly);
                if(result){
                    console.log(res);
                    if(res.code==='200'){
                        $('#modal-register').modal('hide');
                        $('#verifyMobileForm').clearForm();
                        $('#registForm').clearForm();
                        setTimeout(function () {
                            $('#modal-login').modal('show');
                        },500);
                    }else{
                        layer.alert(res.message);
                    }
                }else{
                    layer.alert("注册失败！");
                }
            });
        }
    });
};

/**
 * 下载
 */
Chat.prototype.download = function () {
    let that = this;
    if(that.token===''){
        alert("请先登录！");
        return;
    }
    let fileId = $('#fileId').val();
    if(fileId===''){
        alert("请输入文件ID！");
        return;
    }
    let jsonData = {
        "action": 'download',
        "token": that.token,
        "data": {
            "fileId": fileId
        }
    };
    $.ajax({
        type: 'POST',
        url: that.httpUrl + '/file/download',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(jsonData),
        dataType: '',
        success: function (message) {
        },
        error: function (message) {
        }
    });
};

/**
 * 搜索好友
 */
Chat.prototype.searchUser = function () {
    let that = this;
    if(that.token===''){
        layer.alert('请先登录！');
        return;
    }
    let mobile = $('#input_search').val();
    if(mobile==='' || mobile.length!==11){
        return;
    }
    let data = {"mobile":mobile};
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.postData("searchUser", data, function (result, res) {
        layer.close(ly);
        if(result){
            if(res.code==='200'){
                if(res.data.list.length>0){
                    let user = res.data.list[0];
                    let friend = that.friendMap.get(user.userId);
                    if(friend){
                        layer.alert(friend.nickName + "已经是您的好友了");
                    }else{
                        $('#modal-addFriend').modal('show');
                        $('#search_user_id').val(user.userId);
                        $('#search_user_avatar').attr('src', user.avatar);
                        $('#search_user_name').text(user.nickName);
                        $('#search_user_country').text(user.country);
                    }
                }else{
                    layer.alert("此手机号用户不存在");
                }
            }else{
                layer.alert(res.message);
            }
        }else{
            console.log(res);
        }
    });
};

/**
 * 申请加好友
 */
Chat.prototype.applyAddFriend = function () {
    let that = this;
    let targetUserId = $('#search_user_id').val();
    let leaveMsg = $('#search_leaveMsg').val();
    if(leaveMsg===''){
        layer.alert('验证信息不能为空');
        return;
    }
    let data = {"targetUserId":targetUserId, "leaveMsg":leaveMsg};
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.postData("applyAddFriend", data, function (result, res) {
        layer.close(ly);
        if(result){
            if(res.code==='200'){
                let message = {
                    'mode': 4,
                    'rt': new Date().getTime(),
                    'chatMessage': {
                        'type': 10,
                        'mid': 'T001',
                        'rid': targetUserId,
                        'message': leaveMsg
                    }
                };
                that.socket.send(JSON.stringify(message));
                layer.alert("验证已发送");
            }else{
                layer.alert(res.message);
            }
        }else{
            console.log(res);
        }
    });
};

/**
 * 查询加好友信息
 */
Chat.prototype.getApplyFriend = function () {
    let that = this;
    this.postData("getApplyFriend", {"status":"0"}, function (result, res) {
        if(result){
            if(res.code==='200'){
                let array = res.data.list;
                if(array.length>0){
                    that.addNotify(true, array.length);
                    let notifyContainer = $('#notify_message');
                    notifyContainer.empty();
                    $.each(array, function (index, obj) {
                        obj.avatar = obj.user.avatar;
                        let oli = $(that.createNotifyMessage(obj)).unbind('click').bind('click', obj, function (e) {
                            that.notifyHandler(e.data);
                        });
                        oli.appendTo(notifyContainer);
                    });
                }
            }
        }else{
            console.log(res);
        }
    });
};

/**
 * 接受群聊邀请
 * @param chat
 */
Chat.prototype.acceptGroupInvite = function(chat) {
    let that = this;
    let obj = {
        "applyId": chat.chatMessage.mid,
        "avatar": 'http://119.45.139.102:9000/chat/notice.png',
        "leaveMsg": chat.chatMessage.message,
        "applyTime": chat.rt
    };
    let oli = $(that.createNotifyMessage(obj)).unbind('click').bind('click', obj, function (e) {
        $('#notice_message_container').text(e.data.leaveMsg);
        $('#modal-notice').modal('show');
        that.clearNotify(e.data.applyId);
    });
    let notifyContainer = $('#notify_message');
    oli.appendTo(notifyContainer);
    this.addNotify(false, 1);
    //解散群
    if(chat.chatMessage.detailType===3 || chat.chatMessage.detailType===5){
        $('#friend_item_' + chat.chatMessage.rid).remove();
    }else{
        that.postData("getGroupProfile", {"groupId":chat.chatMessage.mid,"type":1}, function (result, res) {
            if(result && res.code==='200'){
                let group = res.data;
                let friend = {};
                friend.userId = group.groupId;
                friend.nickName = group.name;
                friend.avatar = group.avatar;
                friend.country = '';
                friend.mobile = '';
                friend.total = group.total;
                friend.ownerId = group.ownerId;
                that.friendMap.set(friend.userId, friend);
                $('#div_friends').append(that.buildFriend(friend));
            }
        });
    }
};

/**
 * 创建通知消息
 * @param obj
 * @returns {*}
 */
Chat.prototype.createNotifyMessage = function(obj) {
    let sb = new StringBuilder();
    sb.append("<li id='li_notify_").append(obj.applyId).append("' class='notification-message'>");
    sb.append("<a href='javascript:void(0)'>");
    sb.append("<div class='media'>");
    sb.append("<span class='avatar avatar-sm'>");
    sb.append("<img alt='' src='").append(obj.avatar).append("'>");
    sb.append("</span>");
    sb.append("<div class='media-body'>");
    let msg = obj.leaveMsg;
    if(obj.user){
        msg = obj.user.nickName + '：' + obj.leaveMsg;
    }
    sb.append("<p class='noti-details'><span class='noti-title'>").append(msg).append("</span></p>");
    sb.append("<p class='noti-time'><span class='notification-time'>").append(dateFormat(obj.applyTime)).append("</span></p>");
    sb.append("</div>");
    sb.append("</div></a></li>");
    return sb.toString();
};

/**
 * 处理好友申请结果
 */
Chat.prototype.applyFriendResult = function (chat) {
    let that = this;
    let obj = {};
    obj.applyId = chat.chatMessage.mid;
    obj.sid = chat.chatMessage.sid;
    obj.avatar = 'http://119.45.139.102:9000/chat/notice.png';
    obj.leaveMsg = chat.chatMessage.message;
    obj.applyTime = chat.rt;
    obj.applyResult = chat.chatMessage.applyResult;

    //添加好友到列表
    if(obj.applyResult===1){
        that.postData("getProfile", {"targetUserId": obj.sid}, function (result, res) {
            if(result && res.code === '200') {
                let friend = {};
                friend.total = 0;
                friend.userId = res.data.userId;
                friend.nickName = res.data.nickName;
                friend.avatar = res.data.avatar;
                friend.country = res.data.country;
                friend.mobile = res.data.mobile;
                that.friendMap.set(friend.userId, friend);
                $('#div_friends').prepend(that.buildFriend(friend));
            }
        });
    }

    let oli = $(that.createNotifyMessage(obj)).unbind('click').bind('click', obj, function (e) {
        $('#notice_message_container').text(e.data.leaveMsg);
        $('#modal-notice').modal('show');
        that.clearNotify(e.data.applyId);
    });
    let notifyContainer = $('#notify_message');
    oli.appendTo(notifyContainer);
    this.addNotify(false, 1);
};

/**
 * 删除好友
 * @param friend
 */
Chat.prototype.removeFriend = function(friend) {
    let that = this;
    let ly = layer.confirm('确定要删除好友吗？', {
        btn: ['是','否'] //按钮
    }, function(){
        layer.close(ly);
        ly = layer.load(1, {shade: [0.1,'#000']});
        let data = {"friendId": friend.userId};
        that.postData('removeFriend', data, function (result, res) {
            layer.close(ly);
            if(result){
                if(res.code==='200'){
                    that.friendMap.delete(friend.userId);
                    that.chatMap.delete(friend.userId);
                    if(that.chatUserId===friend.userId){
                        $('#friend_item_' + friend.userId).remove();
                        that.clearMessageContainer();
                    }
                }else{
                    layer.alert(res.message);
                }
            }
        });
    }, function(){
    });
};

/**
 * 清空Message对话框
 */
Chat.prototype.clearMessageContainer = function(){
    $('#div_message_container').empty();
    $('#chat_user_avatar').removeAttr('src');
    $('#chat_user_avatar').attr('alt','');
    $('#chat_user_nickName').text('');
    $('#contextMenu_container').empty();
};

/**
 * 通知处理
 * @param msgObj
 */
Chat.prototype.notifyHandler = function (msgObj) {
    let that = this;
    $('#modal-verifyFriend').modal('show');
    $('#verify_apply_id').val(msgObj.applyId);
    $('#verify_friend_leaveMsg').text(msgObj.leaveMsg);

    let user = msgObj.user;
    $('#verify_friend_avatar').attr('src', user.avatar);
    $('#verify_friend_name').text(user.nickName);
    $('#verify_friend_country').text(user.country);
    $('#btn_verify_refuse').unbind('click').bind('click', msgObj, function(event){
        that.acceptFriend(event.data, '2');
    });
    $('#btn_verify_agree').unbind('click').bind('click', msgObj, function(event){
        that.acceptFriend(event.data, '1');
    });
};

/**
 * 是否接受好友
 * @param pass
 */
Chat.prototype.acceptFriend = function (applyObj, status) {
    let that = this;
    let data = {"applyId":applyObj.applyId, "status": status};
    that.postData("applyFriendHandler", data, function (result, res) {
        if (result) {
            if (res.code === '200') {
                let userObj = applyObj.user;
                if(status==='1'){
                    let isFriend = that.friendMap.get(userObj.userId);
                    if (!isFriend) {
                        let friend = {};
                        friend.total = 0;
                        friend.userId = res.data.userId;
                        friend.nickName = res.data.nickName;
                        friend.avatar = res.data.avatar;
                        friend.country = res.data.country;
                        friend.mobile = res.data.mobile;
                        that.friendMap.set(friend.userId, friend);
                        $('#div_friends').prepend(that.buildFriend(friend));
                    }
                }
                that.clearNotify(applyObj.applyId);
                let message = {
                    'mode': 5,
                    'rt': new Date().valueOf(),
                    'chatMessage': {
                        'mid': new Date().valueOf(),
                        'sid': '',
                        'rid': userObj.userId,
                        'applyResult': ((status==='1')?1:2),
                        'message': ((status==='1')?that.nickName+ '通过了你的好友申请':that.nickName + '拒绝了你的好友申请')
                    }
                };
                that.socket.send(JSON.stringify(message));
            }else{
                layer.alert(res.message);
            }
        }else{
            layer.alert(res);
        }
    });
};

/**
 * 删除通知
 * @param mid
 */
Chat.prototype.clearNotify = function(mid) {
    $('#li_notify_' + mid).remove();
    let notifyTotal = $('#notify_total');
    let total = parseInt(notifyTotal.text());
    if(total>0){
        total -= 1;
    }
    notifyTotal.text(total);
    if(total<=0){
        notifyTotal.css('display', 'none');
    }
};

/**
 * 清空通知
 */
Chat.prototype.clearAllNotify = function() {
    let notifyContainer = $('#notify_message');
    notifyContainer.empty();
};

/**
 * 添加通知
 */
Chat.prototype.addNotify = function(reset, number) {
    if(number>0){
        let notifyTotal = $('#notify_total');
        let total = parseInt(notifyTotal.text());
        if(reset){
            total = number;
        }else{
            total += number;
        }
        notifyTotal.text(total);
        notifyTotal.css('display', 'block');
    }
};

/**
 * 显示建群窗口
 */
Chat.prototype.showGroupModal = function () {
    let that = this;
    if(that.token===''){
        layer.alert('请先登录！');
        return;
    }
    $('#modal-createGroup').modal('show');
    let map = that.friendMap;
    if(map.size>0){
        let groupFriendsContainer = $('#create_group_friends');
        groupFriendsContainer.empty();
        let sb = new StringBuilder();
        for(let userId of map.keys()){
            if(userId.indexOf('U')===0){
                let friend = map.get(userId);
                sb.append("<label class='form-selectgroup-item flex-fill'>");
                sb.append("<input type='checkbox' name='friend' value='").append(userId).append("' class='form-selectgroup-input'>");
                sb.append("<div class='form-selectgroup-label d-flex align-items-center p-3'>");
                sb.append("<div class='mr-3'>");
                sb.append("<span class='form-selectgroup-check'></span>");
                sb.append("</div>");
                sb.append("<div class='form-selectgroup-label-content d-flex align-items-center'>");
                sb.append("<img src='").append(friend.avatar).append("' align='avatar' class='avatar mr-3' />");
                sb.append("<div class='lh-sm'>");
                sb.append("<div class='strong'>").append(friend.nickName).append("</div>");
                sb.append("<div class='text-muted'>").append(friend.country).append("</div>");
                sb.append("</div>");
                sb.append("</div></div></label>");
            }
        }
        groupFriendsContainer.append(sb.toString());
    }
};

/**
 * 创建群组
 */
Chat.prototype.createGroupChat = function () {
    let that = this;
    let formJson = $('#form_createGroup').serializeJSONNoNull();
    let friends = formJson.friend.join(",");
    if(friends.length>0){
        that.postData("createGroup", {"members":friends}, function (result, res) {
            if(result) {
                if (res.code === '200') {
                    let group = res.data;
                    let friendContainer = $('#div_friends');
                    let friend = {};
                    friend.total = 0;
                    friend.userId = group.groupId;
                    friend.nickName = group.name;
                    friend.avatar = group.avatar;
                    friend.country = '';
                    friend.mobile = '';
                    friend.total = group.total;
                    friend.ownerId = group.ownerId;
                    that.friendMap.set(friend.userId, friend);
                    friendContainer.append(that.buildFriend(friend));
                    let message = {
                        'mode': 6,
                        'rt': new Date().getTime(),
                        'chatMessage': {
                            'mid': group.groupId,
                            'rid': group.groupId,
                            'message': that.nickName + '邀请你加入群聊'
                        }
                    };
                    that.socket.send(JSON.stringify(message));
                }else{
                    layer.alert(res.message);
                }
            }else{
                layer.alert(res);
            }
        });
    }
};

/**
 * 查看资料
 */
Chat.prototype.previewProfile = function () {
    let that = this;
    that.postData("getProfile", {}, function (result, res) {
        if(result) {
            console.log(res);
            if (res.code === '200') {
                $('#modal-profile').modal('show');
                $('#hid_avatar').val(res.data.avatar);
                $('#profile_avatar').attr('src', res.data.avatar);
                $('#profile_nickName').val(res.data.nickName);
                $('#profile_country').val(res.data.country);
                $('#profile_signature').val(res.data.signature);
            }else{
                layer.alert(res.message);
            }
        }else{
            layer.alert(res);
        }
    });
};

/**
 * 上传头像
 */
Chat.prototype.uploadAvatar = function () {
    let that = this;
    let file = $("#profileFile")[0].files[0];
    if(file){
        that.uploadFile(file, 'url', function (data) {
            $('#profile_avatar').attr('src', data.url);
            $('#hid_avatar').val(data.url);
        });
    }
};

/**
 * 上传文件
 * @param file
 * @param downType
 * @param successCallback
 */
Chat.prototype.uploadFile = function(file, downType, successCallback){
    let that = this;
    let formData = new FormData();
    formData.append("token", that.token);
    formData.append("downType", downType);
    formData.append("file", file);
    $.ajax({
        type: 'POST',
        url: that.uploadUrl,
        contentType: false,
        processData: false,
        mimeType: 'multipart/form-data',
        enctype: 'multipart/form-data',
        data: formData,
        dataType: "json",
        success: function (result) {
            if(result.code==='200'){
                let data = result.data;
                successCallback(data);
            }else{
                console.log(result.message);
            }
        },
        error: function (result) {
            console.log(result);
        }
    });
};

/**
 * 修改个人信息
 */
Chat.prototype.modifyProfile = function () {
    let that = this;
    let body = $('#form_profile').serializeJSONNoNull();
    that.postData("modifyProfile", body, function (result, res) {
        if(result) {
            if (res.code === '200') {
                layer.alert('保存成功!');
                $('#img_user_avatar').attr('src', $('#hid_avatar').val());
                $('#span_nick_name').text($('#profile_nickName').val());
                $('#modal-profile').modal('hide');
            }else{
                layer.alert(res.message);
            }
        }else{
            layer.alert(res);
        }
    });
};

/**
 * 查看好友资料
 */
Chat.prototype.previewFriendProfile = function (friend) {
    let that = this;
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.postData("getFriendProfile", {"friendId":friend.userId}, function (result, res) {
        layer.close(ly);
        if(result){
            if(res.code==='200'){
                let data = res.data;
                $('#modal-friendProfile').modal('show');
                $('#hid_friend_userId').val(data.user.userId);
                $('#friend_profile_avatar').attr('src', data.user.avatar);
                $('#friend_profile_alias').val(data.alias);
                $('#friend_profile_tag').val(data.tag);
                $('#friend_profile_nickName').val(data.user.nickName);
                $('#friend_profile_country').val(data.user.country);
                $('#friend_profile_signature').val(data.user.signature);
            }else{
                layer.alert(res.message);
            }
        }
    });
};

/**
 * 修改好友资料
 */
Chat.prototype.modifyFriendProfile = function() {
    let that = this;
    let body = $('#form_friend_profile').serializeJSONNoNull();
    that.postData("modifyFriend", body, function (result, res) {
        if(result) {
            if (res.code === '200') {
                layer.alert('保存成功!');
                $('#modal-friendProfile').modal('hide');
            }else{
                layer.alert(res.message);
            }
        }else{
            layer.alert(res);
        }
    });
};

/**
 * 查看群信息
 * @param group
 */
Chat.prototype.previewGroupProfile = function (friend) {
    let that = this;
    let memberContainer = $('#group_member_container');
    memberContainer.empty();
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.postData('getGroupProfile', {"groupId":friend.userId,"type":2}, function (result, res) {
        layer.close(ly);
        if(result && res.code==='200'){
            let group = res.data;
            $('#modal-groupInfo').modal('show');
            let sb = new StringBuilder();
            $.each(group.list, function (index, m) {
                let user = m.user;
                let nickName = user.nickName;
                if(m.alias!==''){
                    nickName = m.alias;
                }
                sb.append("<div class='card' style='width: auto;'>");
                sb.append("<div class='card-body text-center'>");
                sb.append("<div><img class='avatar' src='").append(user.avatar).append("' /></div>");
                sb.append("<div class='text-muted'>").append(nickName).append("</div>");
                sb.append("</div></div>");
            });
            memberContainer.append(sb.toString());

            $('#group_leaderUid').val(group.owner.userId);
            $('#group_groupId').val(group.groupId);
            $('#group_leaderName').val(group.owner.nickName);
            $('#group_total').val(group.total);
            let groupName = $('#group_name');
            let groupTopic = $('#group_topic');
            if(group.owner.userId===that.userId){
                groupName.attr('readonly', false);
                groupTopic.attr('readonly', false);
            }else{
                groupName.attr('readonly', true);
                groupTopic.attr('readonly', true);
            }
            groupName.val(group.name);
            groupTopic.val(group.topic);
        }
    });
};

/**
 * 修改群消息
 */
Chat.prototype.modifyGroupProfile = function () {
    let that = this;
    $('#modal-groupInfo').modal('hide');
    let leaderUid = $('#group_leaderUid').val();
    if(leaderUid!=='' && leaderUid===that.userId){
        let groupId = $('#group_groupId').val();
        let data = $('#form_group').serializeJSONNoNull();
        let ly = layer.load(1, {shade: [0.1,'#000']});
        that.postData("modifyGroup", data, function (result, res) {
            layer.close(ly);
            if(result && res.code==='200'){
                $('#friend_name_' + groupId).text($('#group_name').val());
            }
        });
    }
};

/**
 * 退群
 */
Chat.prototype.quitGroup = function (group) {
    let that = this;
    let tip = '确定要退出此群吗？';
    if(group.ownerId===that.userId){
        tip = '确定要解散群吗？';
    }
    let ly = layer.confirm(tip, {
        btn: ['是','否'] //按钮
    }, function(){
        layer.close(ly);
        let data = {"groupId":group.userId};
        let action = '';
        let detailType = 5;
        let sendMsg = '';
        let rid = group.userId;
        if(group.ownerId===that.userId){
            action = "dismissGroup";
            detailType = 3;
            sendMsg = that.nickName + "解散了群";
        }else{
            action = "quitGroup";
            detailType = 4;
            sendMsg = that.nickName + "退出了群";
            rid = group.ownerId;
        }
        that.postData(action, data, function (result, res) {
            if(result && res.code==='200'){
                let message = {
                    'mode': 7,
                    'rt': new Date().getTime(),
                    'chatMessage': {
                        'mid': uuid(),
                        'rid': rid,
                        'detailType': detailType,
                        'message': sendMsg
                    }
                };
                that.socket.send(JSON.stringify(message));

                $('#friend_item_' + group.userId).remove();
                that.friendMap.delete(group.userId);
                that.chatMap.delete(group.userId);
                that.clearMessageContainer();
            }
        });
    }, function(){
    });
};

/**
 * 发送图片信息
 */
Chat.prototype.sendImageMessage = function () {
    let that = this;
    if(that.token===''){
        layer.alert('请先登录！');
        return;
    }
    let file = $("#chat_message_file")[0].files[0];
    if(file){
        if(that.chatUserId===''){
            layer.alert('请选择一个聊天对象！');
            return;
        }
        //file.type.match('image/png') || file.type.match('image/jpeg') || file.type.match('image/gif')
        let size = file.size/1024/1024;
        console.log(size);
        let reader = new FileReader();
        reader.readAsDataURL(file);
        let mode = 1;
        if(that.chatUserId.indexOf('G')===0){
            mode = 2;
        }
        let chat = {
            'mode': mode,
            'rt': new Date().getTime(),
            'chatMessage': {
                'type': 20,
                'mid': uuid(),
                'sid': that.userId,
                'rid': that.chatUserId,
                'at': that.aliveTime,
                'content': {
                    'image': {
                        'fileId': '',
                        'url': '',
                        'oriName': ''
                    }
                }
            }
        };
        reader.onloadend = function (event) {
            chat.chatMessage.content.image.url = event.currentTarget.result;
            chat.chatMessage.content.image.oriName = file.name;
            let sender = {
                "userId": that.userId,
                "nickName": that.nickName,
                "avatar": that.avatar,
                "country": that.country,
                "total": 0
            };

            let chatMessage = that.chatMap.get(that.chatUserId);
            chatMessage.sendMessage(sender, chat).appendTo(that.messageContainer);
            that.messagePanelScrollTop(that.messageContainer);
        };
        $("#chat_message_file").val('');
        that.uploadFile(file, 'url', function (data) {
            chat.chatMessage.content.image.fileId = data.fileId;
            chat.chatMessage.content.image.url = data.url;
            chat.chatMessage.content.image.oriName = data.name;
            that.socket.send(JSON.stringify(chat));
        });
    }
};

/**
 * 保存系统时间
 */
Chat.prototype.saveSettings = function () {
    let that = this;
    let atObj = $('#settings_at');
    let at = atObj.val();
    if(parseInt(at)>=0){
        that.aliveTime = at;
        setCookie('settingsAt', at);
        $('#modal-settings').modal('hide');
    }else{
        let cat = getCookie('settingsAt');
        if(cat){
            atObj.val(cat);
        }
    }
};