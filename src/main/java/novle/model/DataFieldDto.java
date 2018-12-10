package novle.model;

public class DataFieldDto extends SearchCommonDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4173312559431848862L;
	private Integer dataFieldSn;
	private Integer funSn;
	private String funName;
	private String filedName;
	private String filedValue;
	private String format;
	private String mark;
	// 是否显示被选中
	private Boolean checked = false;
	private String enable;
	
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public Integer getDataFieldSn() {
		return dataFieldSn;
	}
	public void setDataFieldSn(Integer dataFieldSn) {
		this.dataFieldSn = dataFieldSn;
	}
	public Integer getFunSn() {
		return funSn;
	}
	public void setFunSn(Integer funSn) {
		this.funSn = funSn;
	}
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getFiledValue() {
		return filedValue;
	}
	public void setFiledValue(String filedValue) {
		this.filedValue = filedValue;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
}
