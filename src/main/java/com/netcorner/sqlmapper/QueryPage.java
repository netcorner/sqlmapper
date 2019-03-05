package com.netcorner.sqlmapper;
import java.util.Map;

public class QueryPage  {
    private String order = null;
    private int way = -1;
    private int current = 1;
    private int size = 20;
    private int[] pageIndexList;
    private Map<String,Object> countResult;
    private int pageTotal;
    private int total;
    private int pageListTotal = 10;
    private Map<String,Object> form;
    private int[] showPage = { 20, 30, 50, 100 };
	private int rigor=-1;
    public int getRigor() {
		return rigor;
	}
	public void setRigor(int rigor) {
		this.rigor = rigor;
	}
	/**
     * 页大小列表.
     * @return
     */
    public int[] getShowPage() {
		return showPage;
	}
    /**
     * 页大小列表.
     * @param showPage
     */
	public void setShowPage(int[] showPage) {
		this.showPage = showPage;
	}
	/**
     * 得到排序字段
     * @return
     */
	public String getOrder() {
		return order;
	}
	/**
	 * 设置排序字段
	 * @param order
	 */
	public void setOrder(String order) {
		this.order = order;
	}
    /**
     * 得到排序方向
     * @return
     */
	public int getWay() {
		return way;
	}
	/**
	 * 设置排序方向
	 * @param way
	 */
	public void setWay(int way) {
		this.way = way;
	}
    /**
     * 得到当前页码
     * @return
     */
	public int getCurrent() {
		return current;
	}
	/**
	 * 设置当前页码
	 * @param way
	 */
	public void setCurrent(int current) {
		this.current = current;
	}
    /**
     * 得到页尺寸
     * @return
     */
	public int getSize() {
		return size;
	}
    /**
     * 设置页尺寸
     * @param way
     */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * 传输的表单项
	 * @return
	 */
	public Map<String, Object> getForm() {
		return form;
	}
	/**
	 * 设置传输的表单项
	 * @param form
	 */
	public void setForm(Map<String, Object> form) {
		this.form = form;
	}
	/**
	 * 得到分页大小列表,数据处理后返回
	 * @return
	 */
	public int[] getPageIndexList() {
        if (pageIndexList == null)
        {
            int half = getPageListTotal() / 2;
            int pageLast;//最后一页的页码
            if (getCurrent() < half)
            {
                //1,2...10(PageListTotal)
                if (getPageTotal() > getPageListTotal())
                {
                    pageLast = getPageListTotal();
                }
                else
                {
                    pageLast = getPageTotal();
                }
            }
            else
            {
                pageLast = getCurrent() + half;//2,3,...11(Current + half)
                if (getPageTotal() < pageLast)//最多页不能超过总页数PageTotal
                {
                    pageLast = getPageTotal();
                }
            }
            int first = pageLast - getPageListTotal();
            if (first <= 0) first = 1;
            int len = pageLast - first + 1;
            pageIndexList = new int[len];

            for (int index = 0; index < len; index++)
            {
                pageIndexList[index] = first + index;
            }
        }
		
		return pageIndexList;
	}
	/**
	 * 返回分页后的统计结果记录集，数据处理后返回
	 * @return
	 */
	public Map<String, Object> getCountResult() {
		return countResult;
	}
	/**
	 * 设置分页后的统计结果记录集，数据处理后返回
	 * @param countResult
	 */
	public void setCountResult(Map<String, Object> countResult) {
		this.countResult = countResult;
	}
	/**
	 * 返回总页数，数据处理后返回
	 * @return
	 */
	public int getPageTotal() {
		return pageTotal;
	}
	/**
	 * 设置总页数，数据处理后返回
	 * @param pageTotal
	 */
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	/**
	 * 返回记录数，数据处理后返回
	 * @return
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * 设置记录数，数据处理后返回
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * 返回显示页列表总数，如显示到第几页时省略号，数据处理后返回
	 * @return
	 */
	public int getPageListTotal() {
		return pageListTotal;
	}
	/**
	 * 设置显示页列表总数，如显示到第几页时省略号，数据处理后返回
	 * @param pageListTotal
	 */
	public void setPageListTotal(int pageListTotal) {
		this.pageListTotal = pageListTotal;
	}
}
