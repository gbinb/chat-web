/**
 *
 * @constructor
 */
function Main() {
    this.token = '';
    this.name = '';
    this.username = '';
    this.verifySuccess = false;
}

/**
 * 验证token
 */
Main.prototype.verifyToken = function(callbackFun){
    let that = this;
    that.token = getCookie('adminToken');
    if(that.token!==''){
        that.postData("verifyToken", {}, function (result, res) {
            if(result && res.code==='200'){
                that.name = res.data.name;
                that.username = res.data.username;
                that.verifySuccess = true;
                if(callbackFun){
                    callbackFun();
                }
            }else{
                location.href = '/login.html';
            }
        });
    }else{
        location.href = '/login.html';
    }
};

/**
 * post数据
 * @param action
 * @param data
 * @param callbackFun
 */
Main.prototype.postData = function (action, data, callbackFun){
    let that = this;
    let jsonData = {
        "action": action,
        "token": that.token,
        "data": data
    };
    $.ajax({
        type: 'POST',
        url: httpUrl,
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
 * 导航
 */
Main.prototype.menuClick = function (e) {
    let that = this;
    let menuArray = $('.nav-link');
    let curMenuId = $(e).data("id");
    $.each(menuArray, function (index, menu) {
        let isMenu = $(menu).data("menu");
        if(isMenu && isMenu===1){
            let menuUrl = $(menu).data("url");
            if(menuUrl!==''){
                let menuId = $(menu).data("id");
                if(curMenuId===menuId){
                    $(menu).addClass('active');
                    that.menuLoadHtml(menuUrl);
                }else{
                    $(menu).removeClass('active');
                }
            }
        }
    });
};

/**
 * 加载内容
 */
Main.prototype.menuLoadHtml = function (pageUrl) {
    if(main.verifySuccess){
        let pageContent = $('#pageContent');
        pageContent.empty();
        pageContent.load(pageUrl);
    }else{
        location.href = "/login.html";
    }
};