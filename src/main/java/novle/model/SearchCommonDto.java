package novle.model;


public class SearchCommonDto implements java.io.Serializable {

	private static final long serialVersionUID = -1735318809404892161L;

	private String sortBy;

    private String sortDir;

    private Integer start = 0;

    private Integer limit = 10;
    
    private Integer pageCurrent = 1;
    
    private Integer pageSize = 20;
    
    //存放多条件筛选的条件and还是or
    private String[] andOrList;
    
    //存放筛选条件类型
    private String[] list;
    
    //普通表格排序
    private String orderField;
    private String orderDirection;
    
    //增加或者修改判断标识
    private Boolean addFlag;

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

	public String[] getAndOrList() {
		return andOrList;
	}

	public void setAndOrList(String[] andOrList) {
		this.andOrList = andOrList;
	}

	public String[] getList() {
		return list;
	}

	public void setList(String[] list) {
		this.list = list;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDir() {
		return sortDir;
	}

	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Boolean getAddFlag() {
		return addFlag;
	}

	public void setAddFlag(Boolean addFlag) {
		this.addFlag = addFlag;
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
	
}
