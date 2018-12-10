package novle.model;

import java.io.Serializable;

import novle.annotation.DtoFieldType;

public class TestDemoFilterDto extends SearchCommonDto implements Serializable{
	
	private static final long serialVersionUID = -5943468173144410514L;
	
	@DtoFieldType(name="Integer")
	private String[] id;
	@DtoFieldType(name="String")
	private String[] demoCode;
	@DtoFieldType(name="String")
	private String[] demoName;
	@DtoFieldType(name="Date")
	private String[] demoTime;
	
	public String[] getId() {
		return id;
	}
	public void setId(String[] id) {
		this.id = id;
	}
	public String[] getDemoCode() {
		return demoCode;
	}
	public void setDemoCode(String[] demoCode) {
		this.demoCode = demoCode;
	}
	public String[] getDemoName() {
		return demoName;
	}
	public void setDemoName(String[] demoName) {
		this.demoName = demoName;
	}
	public String[] getDemoTime() {
		return demoTime;
	}
	public void setDemoTime(String[] demoTime) {
		this.demoTime = demoTime;
	}

}
