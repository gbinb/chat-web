package cn.fetosoft.chat.adapter.res.chat;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/23 9:30
 */
public class PageResponse extends BaseResponse {

	private int recordTotal;

	private int pageTotal;

	public int getRecordTotal() {
		return recordTotal;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	/**
	 * 计算总页数
	 * @param recordTotal
	 * @return
	 */
	public void setRecordTotal(int recordTotal, int rows){
		this.recordTotal = recordTotal;
		if(recordTotal>0){
			pageTotal = recordTotal/rows;
			int mod = recordTotal%rows;
			if(mod>0){
				pageTotal += 1;
			}
		}
	}
}
