/**
 * 用户信息相关
 * @param main
 * @constructor
 */
function User(main) {
    this.main = main;
    let that = this;
    this.userPagination = new Pagination(function (pageIndex) {
        that.findUserList(pageIndex);
    });
    this.groupPagination = new Pagination(function (pageIndex) {
        that.findGroupList(pageIndex);
    });
}

/**
 * 查询用户信息
 */
User.prototype.findUserList = function (pageIndex) {
    let that = this;
    let data = {"page":pageIndex,"rows":10,"mobile":$('#qform_user_mobile').val()};
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.main.postData('queryUsers', data, function (result, res) {
        layer.close(ly);
        if(result){
           if(res.code==='200'){
               let pageFooter = $('#footer_page_user');
               that.userPagination.initPageData(res.data.recordTotal, res.data.pageTotal, pageFooter);
               let userArray = res.data.list;
               let table = $('#tb_user tbody');
               table.empty();
               $.each(userArray, function (index, user) {
                   let row = that.createUserTable(user);
                   row.appendTo(table);
               });
           }else{
               layer.alert(res.message);
           }
        }
    });
};

/**
 * 创建行
 * @param item
 */
User.prototype.createUserTable = function (user) {
    let that = this;
    let row = $("<tr></tr>");

    let avatarCel = that.createAvatarColumn({
        "avatar": user.avatar,
        "title": user.nickName,
        "id": user.userId
    });
    avatarCel.appendTo(row);

    let cellMobile = that.createTitleColumn(user.mobile);
    cellMobile.appendTo(row);

    let cellGender = that.createTitleColumn(user.genderText);
    cellGender.appendTo(row);

    let cellCountry = that.createTitleColumn(user.country);
    cellCountry.appendTo(row);

    let cellStatus = that.createTitleColumn(user.statusText);
    cellStatus.appendTo(row);

    let cellCreateTime = that.createTitleColumn(user.createTime);
    cellCreateTime.appendTo(row);

    let cellIp = that.createTitleColumn(user.lastLoginIp);
    cellIp.appendTo(row);

    let cellLoginTime = that.createTitleColumn(user.lastLoginTime);
    cellLoginTime.appendTo(row);

    let cellBtn = that.createButtonColumn([{
        "text": "查看好友"
    }]);
    cellBtn.appendTo(row);
    return row;
};

/**
 * 创建头像列
 * @param user
 * @returns {jQuery|HTMLElement}
 */
User.prototype.createAvatarColumn = function(options){
    let col1 = new StringBuilder();
    col1.append("<td data-label='Name'>");
    col1.append("<div class='d-flex lh-sm py-1 align-items-center'>");
    col1.append("<span class='avatar mr-2' style='background-image: url(").append(options.avatar).append(")'></span>");
    col1.append("<div class='flex-fill'>");
    col1.append("<div class='strong'>").append(options.title).append("</div>");
    col1.append("<div class='text-muted text-h5'>").append(options.id).append("</div>");
    col1.append("</div></div>");
    col1.append("</td>");
    return $(col1.toString());
};

/**
 * 创建普通的文字列
 * @param text
 * @returns {jQuery|HTMLElement}
 */
User.prototype.createTitleColumn = function (text) {
    let sb = new StringBuilder();
    sb.append("<td data-label='Title'><div>").append(text).append("</div></td>");
    return $(sb.toString());
};

/**
 * 创建按钮列
 * @param user
 */
User.prototype.createButtonColumn = function (options) {
    let cell = $("<td></td>");
    let btnContainter = $("<div class='btn-list flex-nowrap'></div>");
    $.each(options, function (index, opt) {
        let button = $("<a href='#' class='btn btn-secondary'>").append(opt.text).append("</a>");
        button.unbind('click').bind('click', opt, function (e) {
            if(opt.click){
                opt.click();
            }
        });
        button.appendTo(btnContainter);
    });
    btnContainter.appendTo(cell);
    return cell;
};

/**
 * 查询群信息
 * @param group
 */
User.prototype.findGroupList = function (pageIndex) {
    let that = this;
    let data = {"page":pageIndex,"rows":10,"groupId":$('#qform_group_groupId').val()};
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.main.postData('queryGroup', data, function (result, res) {
        layer.close(ly);
        if(result){
            if(res.code==='200'){
                let pageFooter = $('#footer_page_group');
                that.groupPagination.initPageData(res.data.recordTotal, res.data.pageTotal, pageFooter);
                let groupArray = res.data.list;
                let table = $('#tb_group tbody');
                table.empty();
                $.each(groupArray, function (index, group) {
                    let row = that.createGroupTable(group);
                    row.appendTo(table);
                });
            }else{
                layer.alert(res.message);
            }
        }
    });
};

/**
 *
 * @param group
 */
User.prototype.createGroupTable = function (group) {
    let that = this;
    let row = $("<tr></tr>");

    let avatarCel = that.createAvatarColumn({
        "avatar": group.avatar,
        "title": group.name,
        "id": group.groupId
    });
    avatarCel.appendTo(row);

    let cellMobile = that.createTitleColumn(group.ownerNickName);
    cellMobile.appendTo(row);

    let cellGender = that.createTitleColumn(group.members);
    cellGender.appendTo(row);

    let cellCountry = that.createTitleColumn(group.createTime);
    cellCountry.appendTo(row);

    let cellStatus = that.createTitleColumn(group.topic);
    cellStatus.appendTo(row);

    let cellBtn = that.createButtonColumn([{
        "text": "查看成员",
        "click": function () {
            $('#modal-groupMember').modal('show');
            that.getGroupMembers(group);
        }
    },{
        "text": "解散群"
    }]);
    cellBtn.appendTo(row);
    return row;
};

/**
 * 查询群成员信息
 * @param group
 */
User.prototype.getGroupMembers = function (group) {
    let that = this;
    let data = {"groupId": group.groupId};
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.main.postData('queryGroupMember', data, function (result, res) {
        layer.close(ly);
        if(result){
            if(res.code==='200'){
                let members = res.data.list;
                let table = $('#tb_groupMember tbody');
                table.empty();
                $.each(members, function (index, m) {
                    let user = m.user;
                    let row = $("<tr></tr>");
                    let avatarCel = that.createAvatarColumn({
                        "avatar": user.avatar,
                        "title": user.nickName,
                        "id": user.userId
                    });
                    avatarCel.appendTo(row);

                    let cellMobile = that.createTitleColumn(user.mobile);
                    cellMobile.appendTo(row);

                    let cellGender = that.createTitleColumn(user.country);
                    cellGender.appendTo(row);

                    let cellCountry = that.createTitleColumn(user.signature);
                    cellCountry.appendTo(row);

                    row.appendTo(table);
                });
            }else{
                layer.alert(res.message);
            }
        }
    });
};