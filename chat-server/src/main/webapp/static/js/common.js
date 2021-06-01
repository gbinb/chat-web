/**
 * 字符串连接
 * 用法：var sb = new StringBuilder();
 *      sb.append('');
 * @constructor
 */
function StringBuilder(){
    this.data = [];
}

StringBuilder.prototype.append = function(){
    this.data.push(arguments[0]);
    return this;
};

StringBuilder.prototype.toString = function(){
    return this.data.join("");
};

/**
 * 表单转json
 * author Guobingbing
 * description 使用方法 var jsonObj = $('#form').serializeJSON();
 * @returns {{}}
 */
$.fn.serializeJSON = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
/**
 * 表单转json
 * author zhangshuw
 * description 使用方法 var jsonObj = $('#form').serializeJSONNoNull();
 * @returns {{}}
 */
$.fn.serializeJSONNoNull = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if(this.value){
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value);
            } else {
                o[this.name] = this.value;
            }
        }
    });
    return o;
};

/**
 * 获取cookie值
 * @param cname
 * @returns {string}
 */
function getCookie(cname){
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name)===0) {
            return c.substring(name.length,c.length);
        }
    }
    return "";
}

/**
 * 设置cookie值
 * @param cname
 * @param cvalue
 * @param exdays
 */
function setCookie(cname,cvalue,exdays) {
    var d = new Date();
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    var expires = "expires="+d.toGMTString();
    document.cookie = cname+"="+cvalue+"; "+expires+";path=/";
}

/**
 * 删除cookie
 * @param cookieName
 */
function deleteCookie(cookieName) {
    var d = new Date();
    d.setTime(d.getTime()+(-1*24*60*60*1000));
    var expires = "expires="+d.toGMTString();
    document.cookie = cookieName+"=;"+expires+";path=/";
}

/**
 * 验证手机号码
 */
$.validator.addMethod("mobile",function(value,element,params){
    var checkMobile = /^1[3456789]\d{9}$/;
    return this.optional(element)||(checkMobile.test(value));
},"请输入正确的手机号码！");

/**
 * 获取url中的参数
 */
function getUrlParam(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/**
 * 日期格式化
 * @param time
 * @returns {string}
 */
function dateFormat(time) {
    const t = new Date(time)
    // 日期格式
    const format = 'Y-m-d h:i:s'
    const year = t.getFullYear()
    // 由于 getMonth 返回值会比正常月份小 1
    const month = t.getMonth() + 1
    const day = t.getDate()
    const hours = t.getHours()
    const minutes = t.getMinutes()
    const seconds = t.getSeconds()
    const hash = {
        'Y': year,
        'm': month,
        'd': day,
        'h': hours,
        'i': minutes,
        's': seconds
    }
    return format.replace(/\w/g, o => {
        return hash[o]
    })
}

/**
 * 生成UUID
 * @returns {string}
 */
function uuid() {
    let s = [];
    let hexDigits = "0123456789abcdef";
    for (let i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
    let uuid = s.join("");
    return uuid;
}

/**
 * 脱敏处理
 * @param mobile
 * @returns {*}
 */
function filterMobile(mobile) {
    if(mobile.length===11){
        mobile = mobile.substring(0, 3) + "****" + mobile.substring(8, 4);
    }
    return mobile;
}

/**
 * 清空form
 */
$.fn.clearForm = function () {
    let inputArr = this.find('[name]');
    $.each(inputArr, function (index, input) {
        let type = $(input).prop('type');
        if(type==='hidden' || type==='text' || type==='textarea'){
            $(input).val('');
        }else if(type==='checkbox'){
            $(input).prop('checked', false);
        }else if(type.startsWith('select')){
            $(input).val('');
        }
    });
};