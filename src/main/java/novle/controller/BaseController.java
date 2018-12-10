package novle.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import constants.Constants;
import novle.model.PageResultDto;
import novle.model.SearchCommonDto;
import util.CustomizedPropertyPlaceholderConfigurer;
import util.FileTransportUtil;
import util.StringUtil;

public class BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	protected final String picRealPath =  (String) Constants.getProperty("picRealPath");
//	protected final String picUrl = (String) Constants.getProperty("picUrl");
	protected final String tmp = (String) CustomizedPropertyPlaceholderConfigurer.getContextProperty("tmp");
	protected final String PATH = (String)CustomizedPropertyPlaceholderConfigurer.getContextProperty("claimsUploadUrl");
	protected final String tmpPicPath = (String)CustomizedPropertyPlaceholderConfigurer.getContextProperty("tmpPicPath");
	protected final String customerFilePath = (String)CustomizedPropertyPlaceholderConfigurer.getContextProperty("customerFilePath");
	protected final String contractFilePath = (String)CustomizedPropertyPlaceholderConfigurer.getContextProperty("contractFilePath");
	protected static List<String> type1 = new ArrayList<String>();
	protected static List<String> type2 = new ArrayList<String>();
	static{
		//类型list
		type1.add("int");
		type1.add("java.lang.String");
		type1.add("boolean");
		type1.add("char");
		type1.add("float");
		type1.add("double");
		type1.add("long");
		type1.add("short");
		type1.add("byte");
		
		type2.add("Integer");
		type2.add("java.lang.String");
		type2.add("java.lang.Boolean");
		type2.add("java.lang.Character");
		type2.add("java.lang.Float");
		type2.add("java.lang.Double");
		type2.add("java.lang.Long");
		type2.add("java.lang.Short");
		type2.add("java.lang.Bytes");	
	}

	
	/*@Autowired
	DataDictService dataDictService;*/
	/*@Autowired
	FunManageService funManageService;*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void putFun(HttpServletRequest request, String showName){
		/*List list = new ArrayList();
		List<SelectItemDto> dtoList = funManageService.getAll();
		JSONObject obj = new JSONObject();
		if(dtoList != null && dtoList.size() > 0){
			for (int i = 0; i < dtoList.size(); i++) {
				obj.put(dtoList.get(i).getValue(), dtoList.get(i).getLable());
			}
			list.add(obj);
		}
		request.setAttribute(showName, list);*/
	}

	
	/**
	 * list页面显示数据字典中的信息
	 * @param request
	 * @param type
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void prepare(HttpServletRequest request,String type,String showName) {
		/*List list = new ArrayList();
		List<SelectItemDto> dtoList = dataDictService.getListByDataType(type);
		JSONObject obj = new JSONObject();
		if(dtoList != null && dtoList.size() > 0){
			for (int i = 0; i < dtoList.size(); i++) {
				obj.put(dtoList.get(i).getValue(), dtoList.get(i).getLable());
			}
			list.add(obj);
		}
		request.setAttribute(showName, list);*/
	}
	
	/**
	 * 编辑页面用到select的options
	 * @param request
	 * @param type
	 */
	protected void prepare1(HttpServletRequest request,String type, String showName) {
		/*List<SelectItemDto> list = dataDictService.getListByDataType(type);
		request.setAttribute(showName, CommonUtil.createSelectOptions(list));*/
	}
	
	protected void setParameters(PageResultDto resultDto , SearchCommonDto dto  ){
		resultDto.setPageSize(dto.getPageSize());
	}
	
	public String upLoadFile(HttpServletRequest request,MultipartFile file){
		JSONObject obj = new JSONObject();
		String targetName = FileTransportUtil.saveFile(PATH, file);
		if(StringUtil.isNotNull(targetName)){
			obj.put("statusCode", 200);
			obj.put("message", "上传成功");
		}else{
			obj.put("statusCode", 800);
			obj.put("message", "上传失败");
		}
		return targetName;
	}
	
	public void filedownload(String path, HttpServletResponse response) throws IOException{
		 try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            //response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	
}
