package util;

import java.util.Iterator;
import java.util.List;

import novle.model.SelectItemDto;

public class CommonUtil {

	public static String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replace("\r\n", " ");
		str = str.replace("\n", " ");
		str = str.replace("\r", " ");
		return str;
	}
	
	public static String createSelectOptions(List<SelectItemDto> dtoList) {
		StringBuffer strbuf = new StringBuffer();
		if (dtoList != null) {
			for (Iterator<SelectItemDto> iterator = dtoList.iterator(); iterator.hasNext();) {
				SelectItemDto selectItemDto = iterator.next();
				strbuf.append("<option value='"+ selectItemDto.getValue() +"'>" + selectItemDto.getLable() + "</option>");
			}
		}
		return strbuf.toString();
	}
	
	public static String createSelectOptionsWithBlank(List<SelectItemDto> dtoList) {
		StringBuffer strbuf = new StringBuffer();
		if (dtoList != null) {
			strbuf.append("<option value=''>未选择</option>");
			for (Iterator<SelectItemDto> iterator = dtoList.iterator(); iterator.hasNext();) {
				SelectItemDto selectItemDto = iterator.next();
				strbuf.append("<option value='"+ selectItemDto.getValue() +"'>" + selectItemDto.getLable() + "</option>");
			}
		}
		return strbuf.toString();
	}
	
	public static void main(String[] args) {
//		CommonUtil commonUtil = new CommonUtil();
//		String str="<table>\r\n<tr><td>{title}</td></tr><tr><td>{content}</td></tr></table>";
//		String htmlspecialchars = commonUtil.htmlspecialchars(str);
//		logger.info(htmlspecialchars);
	}
}	
