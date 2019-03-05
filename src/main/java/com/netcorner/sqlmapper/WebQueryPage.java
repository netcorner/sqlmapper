package com.netcorner.sqlmapper;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



public class WebQueryPage  extends QueryPage {
	private HttpServletRequest request;
	public WebQueryPage(){
		request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		this.setCurrent(-1);
		this.setSize(-1);
	}
	public WebQueryPage(HttpServletRequest request){
		this.request=request;
	}
	/**
     * 排序字段
     */
	@Override
	public String getOrder() {
        if (super.getOrder() == null)
        {
        	super.setOrder(request.getParameter("order"));
        }
    	return super.getOrder();
	}
    /**
     * 排序方向
     */
    @Override
    public int getWay() {
        if (super.getWay() == -1)
        {
            String tmp = request.getParameter("way");
            if(tmp==null){
            	super.setWay(0);
            }else{
	            if (tmp.equals("1")||tmp.equals("0"))
	            {
	            	super.setWay(Integer.parseInt(tmp));
	            }
	            else
	            {
	            	super.setWay(0);
	            }
            }
        }
    	return super.getWay();
	}

    /**
     * 当前页
     */
    @Override
    public int getCurrent() {
        if (super.getCurrent() == -1)
        {
            String tmp = request.getParameter("page")+"";
            if(tmp.equals("")) super.setCurrent( 1);
            else {
                if (tmp.replaceAll("\\d+", "").equals("")) {
                    super.setCurrent(Integer.parseInt(tmp));
                } else {
                    super.setCurrent(1);//1表示当前页
                }
            }
        }
        return super.getCurrent();
	}




    
    /**
     * 页大小
     */
    @Override
    public int getSize() {
        if (super.getSize() == -1)
        {
            String tmp = request.getParameter("size")+"";
            if (tmp.replaceAll("\\d+","").equals(""))
            {
            	setSize(Integer.parseInt(tmp));
            }
            else
            {
            	setSize(getShowPage()[0]);
            }
        }
        return super.getSize();
	}



    public int getRigor() {
        if (super.getRigor() == -1)
        {
            String tmp = request.getParameter("rigor");
            if (tmp.replaceAll("\\d+","").equals(""))
            {
            	super.setRigor(Integer.parseInt(tmp));
                if (super.getRigor() != 1)
                {
                	setRigor(0);
                }
            }
            else
            {
            	setRigor(0);
            }
        }
        return super.getRigor();
	}

    /**
     * 得到排序的响应的url
     * @param field
     * @return
     */
    public String queryOrderUrl(String field)
    {
        String currentUrl =replaceQueryUrl("order", field, getRequestURL());
        if (getOrder() == field)
        {
            if (getWay() == 1)
            {
                currentUrl = replaceQueryUrl("way", 0, currentUrl);
            }
            else
            {
                currentUrl = replaceQueryUrl("way", 1, currentUrl);
            }
        }
        else
        {
            currentUrl = replaceQueryUrl("way", 0, currentUrl);
        }
        return currentUrl;
    }


    /**
     * 得到分列表
     */
    public String GetPageListUrl(int index)
    {
        return replaceQueryUrl("page", 1, replaceQueryUrl("size", index, getRequestURL()));
    }
    
    private String requestURL=null;
    

   public String getRequestURL() {
	
       if (requestURL==null)
       {
           requestURL=request.getRequestURI().toString();
       }
       
       return requestURL; 
   }

   /**
    * 得到当前url
    */
    public String GetPageUrl(int index)
    {
        return replaceQueryUrl("page", index, getRequestURL());
    }
    /**
     * url参数替换
     * @param queryStr
     * @param value
     * @param currentUrl
     * @return
     */
    public static String replaceQueryUrl(String queryStr, Object value, Object currentUrl)
    {
        String url = currentUrl+"";
        StringBuilder query = new StringBuilder();
        String reg = queryStr + "=\\w+";
        Pattern oPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        String replaceStr = queryStr + "=" + value;
        if (oPattern.matcher(url).find())
        {
        	query.append(url.replaceAll(reg,replaceStr));
        }
        else
        {
            oPattern = Pattern.compile("[?]{1}");
            query.append(url);
            if (oPattern.matcher(url).find()) query.append("&"); else query.append("?");
            query.append(replaceStr);
        }
        return query.toString().replaceAll("&amp;", "&").replaceAll("&", "&amp;");
    }
}
