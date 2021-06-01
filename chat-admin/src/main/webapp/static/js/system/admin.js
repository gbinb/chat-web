/**
 * 管理员
 * @constructor
 */
function Admin() {
    this.token = '';
    this.username = '';
    this.name = '';
    this.avatar = '';
}

/**
 * 获取验证码
 */
Admin.prototype.obtainVerifyCode = function(){
    let that = this;
    that.postData("obtainCaptcha", {}, function (result, res) {
        if(result){
            if(res.code==='200'){
                $('#img_code').attr('src','data:image/png;base64,' + res.data.captcha);
            }else{
                layer.alert(res.message);
            }
        }
    });
};

/**
 * 验证token
 */
Admin.prototype.verifyToken = function(){
    let that = this;
    that.token = getCookie('adminToken');
    if(that.token!==''){
        that.postData("verifyToken", {}, function (result, res) {
            if(result && res.code==='200'){
                location.href = '/index.html';
            }
        });
    }
};

/**
 * 登录
 */
Admin.prototype.login = function () {
    let that = this;
    $("#loginForm").validate({
        rules: {
            username:{
                required: true,
                minlength: 4
            },
            password:{
                required: true,
                minlength: 6
            },
            verifyCode:{
                required: true,
                minlength: 4
            }
        },
        messages: {
            username:{
                required: "请输入用户名",
                minlength: "用户名由4-20位且由数字字母下划线组成"
            },
            password:{
                required: "请输入密码",
                minlength: "密码长度6-20位"
            },
            verifyCode:{
                required: "请输入验证码",
                minlength: "验证码格式不正确"
            }
        },
        submitHandler: function() {
            let ly = layer.load(1, {shade: [0.1,'#000']});
            let body = $('#loginForm').serializeJSONNoNull();
            that.postData("adminLogin", body, function (result, res) {
                layer.close(ly);
                if(result){
                    if(res.code==='200'){
                        let data = res.data;
                        that.token = data.token;
                        that.username = data.username;
                        that.name = data.name;
                        that.avatar = data.avatar;
                        if(body.saveCookie){
                            setCookie('adminToken', data.token, 7);
                        }else{
                            setCookie('adminToken', data.token, 0);
                        }
                        location.href = '/index.html';
                    }else{
                        that.obtainVerifyCode();
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
 * post数据
 * @param action
 * @param data
 * @param callbackFun
 */
Admin.prototype.postData = function (action, data, callbackFun){
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