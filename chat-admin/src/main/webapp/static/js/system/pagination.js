/**
 * 翻页控制
 * @constructor
 */
function Pagination(pageClick) {
    this.pageIndex = 1;
    this.beginIndex = 1;
    this.historyIndex = 1;
    this.recordTotal = 0;
    this.pageTotal = 0;
    this.pageMap = new Map();
    this.pageFooter = {};
    this.isPagination = true;
    this.pageCallback = pageClick;
}

/**
 * 设置页面数据
 * @param recordTotal
 * @param pageTotal
 */
Pagination.prototype.initPageData = function(recordTotal, pageTotal, pageFooter) {
    this.recordTotal = recordTotal;
    this.pageTotal = pageTotal;
    this.pageFooter = pageFooter;
    this.createPagination();
};

/**
 * 初始化翻页按钮
 * @param pageIndex
 * @param recordTotal
 * @param pageTotal
 */
Pagination.prototype.createPagination = function() {
    let that = this;
    if(!that.isPagination){
        return;
    }
    that.pageFooter.empty();
    let pageContainer = $("<div class='col-12 d-flex align-items-center'></div>");
    let recordBuilder = new StringBuilder();
    recordBuilder.append("<p class='m-0 text-muted'>共<span>").append(that.recordTotal).append("</span>条记录&nbsp;&nbsp;第<span>");
    recordBuilder.append(that.pageIndex).append("</span>页/共<span>").append(that.pageTotal).append("</span>页</p>");
    let record = $(recordBuilder.toString());
    record.appendTo(pageContainer);
    //上一页
    let itemContainer = $("<ul class='pagination m-0 ml-auto'></ul>");
    let prevBtn = $("<li class='page-item'><a class='page-link' href='javascript:void(0)'>上一页</a></li>");
    prevBtn.unbind('click').bind('click', 'prev', function (e) {
        that.pageClick(e);
    });
    prevBtn.appendTo(itemContainer);

    let maxPage = that.beginIndex + 9;
    if(maxPage>that.pageTotal){
        maxPage = that.pageTotal;
    }
    let pageArray = new Array();
    that.pageMap.clear();
    let count = 0;
    for(let i=maxPage; i>0; i--){
        if(count>9){
            break;
        }
        let pageItem = $("<li class='page-item'><a class='page-link' href='javascript:void(0)'>" + i + "</a></li>");
        if(i===that.pageIndex){
            pageItem.addClass('active');
        }
        pageItem.unbind('click').bind('click', i, function (e) {
            that.pageClick(e);
        });
        pageArray.push(pageItem);
        that.pageMap.set(i, pageItem);
        count++;
    }
    while(pageArray.length>0){
        let pageItem = pageArray.pop();
        pageItem.appendTo(itemContainer);
    }
    //下一页
    let nextBtn = $("<li class='page-item'><a class='page-link' href='javascript:void(0)'>下一页</a></li>");
    nextBtn.unbind('click').bind('click', 'next', function (e) {
        that.pageClick(e);
    });
    nextBtn.appendTo(itemContainer);

    itemContainer.appendTo(pageContainer);
    pageContainer.appendTo(that.pageFooter);
    return pageContainer;
};

/**
 * 创建分页按钮
 */
Pagination.prototype.pageClick = function(e) {
    let that = this;
    let prevBtn = that.pageMap.get(that.historyIndex);
    if(prevBtn){
        $(prevBtn).removeClass('active');
    }

    if(e.data==='prev'){
        if(that.pageIndex>1){
            that.pageIndex = that.pageIndex - 1;
        }else{
            that.pageIndex = 1;
        }
    }else if(e.data==='next'){
        if(that.pageIndex<that.pageTotal){
            that.pageIndex = that.pageIndex + 1;
        }else{
            that.pageIndex = that.pageTotal;
        }
    }else{
        that.pageIndex = e.data;
    }
    let pageBtn = that.pageMap.get(that.pageIndex);
    if(pageBtn){
        $(pageBtn).addClass('active');
        that.isPagination = false;
    }else{
        if(e.data==='prev'){
            that.beginIndex -= 10;
        }else if(e.data==='next'){
            that.beginIndex += 10;
        }
        that.isPagination = true;
        that.createPagination();
    }
    that.historyIndex = that.pageIndex;
    that.pageCallback(that.pageIndex);
};

/**
 * 获取当前页
 * @returns {number|*}
 */
Pagination.prototype.getPageIndex = function () {
    return this.pageIndex;
};
