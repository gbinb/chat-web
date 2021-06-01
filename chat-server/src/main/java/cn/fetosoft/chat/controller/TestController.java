package cn.fetosoft.chat.controller;

import cn.fetosoft.chat.core.data.entity.MsgFile;
import cn.fetosoft.chat.core.data.form.MsgFileForm;
import cn.fetosoft.chat.core.data.service.MsgFileService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用于测试
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/22 9:48
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private MsgFileService msgFileService;

	/**
	 * 获取文件信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFiles")
	@ResponseBody
	public String getFiles(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String fileId = request.getParameter("fileId");
		MsgFileForm form = new MsgFileForm();
		form.setPage(page);
		form.setRows(rows);
		form.setFileId(fileId);
		form.setAscField("id");
		int total = msgFileService.selectCountByForm(form);
		jsonObject.put("recordTotal", total);
		jsonObject.put("page", page);
		jsonObject.put("rows", rows);
		if(total>0){
			form.setRecordCount(total);
			jsonObject.put("pageTotal", form.getPageCount());
			List<MsgFile> list = msgFileService.selectListByForm(form);
			JSONArray array = new JSONArray();
			for(MsgFile file : list){
				JSONObject json = new JSONObject();
				json.put("id", file.getFileId());
				json.put("name", file.getFileName());
				json.put("url", file.getFileUrl());
				array.add(json);
			}
			jsonObject.put("list", array);
		}
		return jsonObject.toJSONString();
	}
}
