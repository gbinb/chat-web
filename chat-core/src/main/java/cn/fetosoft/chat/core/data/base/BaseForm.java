package cn.fetosoft.chat.core.data.base;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author guobingbing
 * @create 2020-02-27 10:23
 */
public class BaseForm {

	/**
	 * 超始页
	 */
	@JSONField(serialize = false)
	private int page = 1;
	/**
	 * 每页记录数，默认0为查询全部
	 */
	@JSONField(serialize = false)
	private int rows = 10;
	/**
	 * 起始记录
	 */
	@JSONField(serialize = false)
	private int startRecord;
	/**
	 * 总记录数
	 */
	@JSONField(serialize = false)
	private int recordCount;
	/**
	 * 页数
	 */
	@JSONField(serialize = false)
	private int pageCount;
	/**
	 * 降序字段
	 */
	@JSONField(serialize = false)
	private String descField;
	/**
	 * 升序字段
	 */
	@JSONField(serialize = false)
	private String ascField;

	public int getPage() {
		if(page==0)
			page = 1;
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getStartRecord() {
		if(this.getPage()>0)
			startRecord = (this.getPage()-1) * this.getRows();
		else
			startRecord = 0;
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		if(recordCount>0 && getRows()>0)
		{
			if(recordCount%getRows()>0)
				pageCount = recordCount/getRows()+1;
			else
				pageCount = recordCount/getRows();
		}
		else
		{
			pageCount = 1;
		}
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getDescField() {
		return descField;
	}

	public void setDescField(String descField) {
		this.descField = descField;
	}

	public String getAscField() {
		return ascField;
	}

	public void setAscField(String ascField) {
		this.ascField = ascField;
	}
}
