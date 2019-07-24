/**
 * 〈一句话功能简述〉<br>
 * 〈分页结果〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;


public class PageResultVO implements Serializable {
    private static final long serialVersionUID = 199412161535815664L;
    /**
     *
     */
    public List<?> lst;
    /**
     *
     */
    public int totalPage;
    /**
     *
     */
    public int pageSize;
    /**
     * 总条数
     */
    public long total;
    
    public PageResultVO() {
	}
    
    public PageResultVO(List<?> list, long total,String pageSize) {
    	
    	int pageData;
    	
    	if ("".equals(pageSize)||pageSize==null||!pageSize.matches("^\\d+$")){
    		pageData=20;
    	}else if(Integer.parseInt(pageSize)>3000) {
    		pageData=20;
    	}else{
    		pageData=Integer.parseInt(pageSize);
    	}
    		
		this.lst = list;
		this.total = total;
		this.pageSize= (pageData==0 ? 20 : pageData);
		this.totalPage= (int) ((this.total  +  this.pageSize  - 1) / this.pageSize);  
	}

    public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
