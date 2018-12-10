package novle.model;

import java.util.List;

public class ResultDto implements java.io.Serializable {

	private static final long serialVersionUID = -8068012405300032835L;
	
	String statusCode;

	String resultType;
	
	String message;
	
	List<? extends Object> data;


    public List<? extends Object> getData() {
		return data;
	}

	public void setData(List<? extends Object> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
