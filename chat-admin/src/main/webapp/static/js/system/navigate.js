/**
 * 导航菜单
 * @constructor
 */
function Navigate(main) {
    //main函数
    this.main = main;
    //菜单项
    this.itemList = new Array();
}

/**
 * 创建菜单项
 */
Navigate.prototype.buildItem = function (json) {
    if(json.length>0){
        $.each(json, function (index, item) {

        });
    }
    let oli = $("<li class='nav-item'></li>");
    let sb = new StringBuilder();
    sb.append("<a class='nav-link'>");
    sb.append("<i class='nav-icon fas fa-tachometer-alt'></i>");
    sb.append("<p>").append(json.name).append("</p>");
    sb.append("</a>");
    let link = $(sb.toString());
};