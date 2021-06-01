/**
 * App升级
 * @constructor
 */
function AppUpgrade(main) {
    this.main = main;
    this.pageIndex = 1;
    this.dataMap = new Map();
    let that = this;
    this.pagination = new Pagination(function (pageIndex) {
        that.findList(pageIndex);
    });
}

/**
 * 查询数据列表
 * @param pageIndex
 */
AppUpgrade.prototype.findList = function (pageIndex) {
    let that = this;
    that.pageIndex = pageIndex;
    let data = $('#qform_appUpgrade').serializeJSONNoNull();
    data.page = pageIndex;
    data.rows = 10;
    let ly = layer.load(1, {shade: [0.1,'#000']});
    that.main.postData('queryAppUpgrade', JSON.stringify(data), function (result, res) {
        layer.close(ly);
        if(result){
            if(res.code==='200'){
                let pageFooter = $('#footer_page_appUpgrade');
                that.pagination.initPageData(res.data.recordTotal, res.data.pageTotal, pageFooter);
                let list = res.data.list;
                let table = $('#tb_appUpgrade tbody');
                table.empty();
                that.dataMap.clear();
                $.each(list, function (index, upgrade) {
                    let row = that.createTable(upgrade);
                    row.appendTo(table);
                    that.dataMap.set(upgrade.id, upgrade);
                });
            }else{
                layer.alert(res.message);
            }
        }
    });
}


AppUpgrade.prototype.createTable = function (upgrade) {
    let that = this;
    let row = $("<tr></tr>");

    let cellVersion = that.createTitleColumn(upgrade.version);
    cellVersion.appendTo(row);

    let cellversionType = that.createTitleColumn(upgrade.versionType);
    cellversionType.appendTo(row);

    let cellApkUrl = that.createTitleColumn(upgrade.apkUrl);
    cellApkUrl.appendTo(row);

    let cellNote = that.createTitleColumn(upgrade.note);
    cellNote.appendTo(row);

    let cellforceUpgrade = that.createTitleColumn(upgrade.forceUpgradeText);
    cellforceUpgrade.appendTo(row);

    let cellIsSkip = that.createTitleColumn(upgrade.isSkipText);
    cellIsSkip.appendTo(row);

    let cellCreateTime = that.createTitleColumn(upgrade.createTime);
    cellCreateTime.appendTo(row);

    let cellBtn = that.createButtonColumn([{
        "text": "编辑",
        "data": upgrade,
        "click": function (data) {
            that.update(data);
        }
    },{
        "text": "删除",
        "data": upgrade,
        "click": function (data) {
            that.delete(data.id);
        }
    }]);
    cellBtn.appendTo(row);
    return row;
}

/**
 * 创建普通的文字列
 * @param text
 * @returns {jQuery|HTMLElement}
 */
AppUpgrade.prototype.createTitleColumn = function (text) {
    let sb = new StringBuilder();
    sb.append("<td data-label='Title'><div>").append(text).append("</div></td>");
    return $(sb.toString());
};

/**
 * 创建按钮列
 * @param user
 */
AppUpgrade.prototype.createButtonColumn = function (options) {
    let cell = $("<td></td>");
    let btnContainter = $("<div class='btn-list flex-nowrap'></div>");
    $.each(options, function (index, opt) {
        let button = $("<a href='#' class='btn btn-primary'>").append(opt.text).append("</a>");
        button.unbind('click').bind('click', opt.data, function (e) {
            if(opt.click){
                opt.click(e.data);
            }
        });
        button.appendTo(btnContainter);
    });
    btnContainter.appendTo(cell);
    return cell;
};

/**
 * 创建
 */
AppUpgrade.prototype.create = function () {
    $('#dlg_appUpgrade_title').text('新增版本信息');
    $('#modal-appUpgrade').modal('show');
    $('#form_save_appUpgrade').clearForm();
};

/**
 * 编辑
 */
AppUpgrade.prototype.update = function (data){
    $('#dlg_appUpgrade_title').text('编辑版本信息');
    $('#form_save_appUpgrade').clearForm();
    $('#modal-appUpgrade').modal('show');
    $('#form_save_appUpgrade').loadForm(data);
};

/**
 * 保存
 */
AppUpgrade.prototype.save = function () {
    let that = this;
    let json = $('#form_save_appUpgrade').serializeJSONNoNull();
    if(json.forceUpgrade){
        json.forceUpgrade = '1';
    }else{
        json.forceUpgrade = '0';
    }
    if(json.isSkip){
        json.isSkip = '1';
    }else{
        json.isSkip = '0';
    }
    let ly = layer.load(1, {shade: [0.1,'#000']});
    let action = 'createAppUpgrade';
    if(json.id && parseInt(json.id)>0){
        action = "updateAppUpgrade";
    }
    that.main.postData(action, JSON.stringify(json), function (result, res) {
        layer.close(ly);
        if (result) {
            if (res.code === '200') {
                $('#modal-appUpgrade').modal('hide');
                layer.alert('保存成功！');
                that.findList(that.pageIndex);
            }else{
                layer.alert(res.message);
            }
        }else{
            layer.alert(res);
        }
    });
};

/**
 * 删除
 * @param id
 */
AppUpgrade.prototype.delete = function (id) {
    let that = this;
    let ly = layer.confirm('确定要注销码？', {
        btn: ['是','否'] //按钮
    }, function(){
        let array = new Array();
        array.push(id);
        ly = layer.load(1, {shade: [0.1,'#000']});
        that.main.postData('removeAppUpgrade', {'ids':array}, function (result, res) {
            layer.close(ly);
            if (result) {
                if (res.code === '200') {
                    layer.alert('删除成功！');
                    that.findList(that.pageIndex);
                }else{
                    layer.alert(res.message);
                }
            }else{
                layer.alert(res);
            }
        });
    }, function(){
    });
};