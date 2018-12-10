package novle.model;

import java.util.ArrayList;
import java.util.List;

public class PageResultDto implements java.io.Serializable {

	private static final long serialVersionUID = -7842868912739182226L;
	Integer total = 0;
    Integer pageCurrent = 1;
    //每页的数量
    Integer pageSize = 20;
    //每页的数量
    Integer totalPage = 0;
	@SuppressWarnings("rawtypes")
    List list = new ArrayList();

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}
    
    public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
    public Integer getTotalPage() {
		return this.total%this.pageSize == 0?this.total/this.pageSize:this.total/this.pageSize+1;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	//普通表格排序
    private String orderField;
    private String orderDirection;
    private String startTime;
    private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	
	private String deptName;
	private String deptNo;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
}
