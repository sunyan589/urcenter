package com.yks.urc.vo;

import java.io.Serializable;
 /**
  * 〈一句话功能简述〉 
  * 〈功能权限VO〉
  * @author lvcr
  * @version 1.0 
  * @see PermissionVO
  * @since JDK1.8
  * @date 2018/6/9 10:46
  */ 
public class PermissionVO implements Serializable {


    private static final long serialVersionUID = -1492882426135502560L;

    private String sysKey;

    private String sysName;

    private String sysContext;
    private String apiUrlPrefixJson;

     public String getSysKey() {
         return sysKey;
     }

     public void setSysKey(String sysKey) {
         this.sysKey = sysKey;
     }

     public String getSysContext() {
         return sysContext;
     }

     public void setSysContext(String sysContext) {
         this.sysContext = sysContext;
     }

     public String getSysName() {
         return sysName;
     }

     public void setSysName(String sysName) {
         this.sysName = sysName;
     }

	public String getApiUrlPrefixJson() {
		return apiUrlPrefixJson;
	}

	public void setApiUrlPrefixJson(String apiUrlPrefixJson) {
		this.apiUrlPrefixJson = apiUrlPrefixJson;
	}
 }
