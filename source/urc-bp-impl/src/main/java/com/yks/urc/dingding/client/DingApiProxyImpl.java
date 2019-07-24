package com.yks.urc.dingding.client;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.dingding.client.vo.DingApiRespVO;
import com.yks.urc.dingding.client.vo.DingDeptVO;
import com.yks.urc.dingding.client.vo.DingUserVO;
import com.yks.urc.fw.HttpUtility;
import com.yks.urc.fw.StringUtility;

/**
 * 获取钉钉部门以及成员信息
 * @Author: wujianghui@youkeshu.com
 * @Date: 2018/6/6 8:59
 */

@Service
public class DingApiProxyImpl implements  DingApiProxy{
	private static Logger LOG = LoggerFactory.getLogger(DingApiProxyImpl.class);

    @Value("${yks.ding.cropid}")
    private String corpid;
    
    @Value("${yks.ding.cropidsecret}")
    private String corpsecret;
    
    @Value("${yks.ding.access_token_time}")
    private String accessTokeTime;
    
    @Value("${yks.ding.access_token_url}")
    private String accessTokenUrl;
    
    @Value("${yks.ding.department_url}")
    private String departmentUrl;
    
    @Value("${yks.ding.user_url}")
    private String userUrl;
    
    @Value("${yks.ding.parent_sub_dept_url}")
    private String parentSubDeptUrl;
	
	@Autowired
	ICacheBp  cacheBp;
	
	
	
    // 调整到1小时30分钟，钉钉保存2个小时，30分钟缓冲
    public static final long cacheTime = 1000 * 60 * 45 * 2;
	
	/**
	 * 获取钉钉accessToken
	 * @return
	 * @throws Exception
	 */
    @Transactional(rollbackFor=Exception.class)
    public  String getDingAccessToken() throws Exception {
    	   long curTime = System.currentTimeMillis();
    	   JSONObject accessTokenValue=JSONObject.parseObject(cacheBp.getDingAccessToken(accessTokeTime));
    	   String accToken = "";
           JSONObject jsontemp = new JSONObject();
          if (accessTokenValue == null || curTime - accessTokenValue.getLong("begin_time") >= cacheTime) {
        	   try {
        		   StringBuffer param=new StringBuffer();
        		   param.append("corpid=").append(corpid).append("&corpsecret=").append(corpsecret);
        		   LOG.info("调用钉钉获取accessTokenc参数,parama={}",param.toString());
        		   String strResp= HttpUtility.sendGet(accessTokenUrl, param.toString());
        		   LOG.info("钉钉accessTokenc响应,strResp={}",strResp);
        		   DingApiRespVO resp = StringUtility.parseObject(strResp, DingApiRespVO.class);
        		   if (resp != null) {
        			   if (resp.errcode == 0) {
        				   accToken=resp.access_token;
        			   }
        		   }
        		   jsontemp.clear();
                   jsontemp.put("access_token", accToken);
                   jsontemp.put("begin_time", curTime);
                   cacheBp.setDingAccessToken(accessTokeTime,jsontemp.toString());
        	   } catch (Exception e) {
        		   LOG.error("钉钉获取accessToken出错，message={}",e.getMessage());
        		   throw new Exception(e.getMessage());
        	   }
           }else{
        	   accToken=accessTokenValue.getString("access_token");
        	   LOG.info("钉钉accessToken缓存值,accessToken={}",accToken);
           }

        return accToken;
    }
    
    /**
     * 得到所有的部门
     * @return
     * @throws Exception
     */
    public  List<DingDeptVO> getDingAllDept() throws Exception {
    	List<DingDeptVO> department = null;
    	 try {
    		String accToken = getDingAccessToken();
         	StringBuffer param=new StringBuffer();
         	param.append("access_token=").append(accToken).append("&fetch_child=").append("true");
         	LOG.info("调用钉钉获取所有部门信息参数,parama={}",param.toString());
         	department=getDingInfo(department, param.toString());
         } catch (Exception e) {
         	LOG.error("钉钉获取所有部门出错，message={}",e.getMessage());
 			throw new Exception(e.getMessage());
         }
        return department;
    }
    
    /**
     * 得到部门下的直接子部门
     * @param departmentId
     * @return
     * @throws Exception
     */
    public  List<DingDeptVO> getDingSubDept(String departmentId) throws Exception {
    	List<DingDeptVO> department = null;
    	 try {
    		String accToken = getDingAccessToken();
         	StringBuffer param=new StringBuffer();
         	param.append("access_token=").append(accToken).append("&id=").append(departmentId);
         	LOG.info("调用钉钉获取所有部门信息参数,departmentId={},parama={}",departmentId,param.toString());
         	department=getDingInfo(department, param.toString());
         } catch (Exception e) {
         	LOG.error("钉钉获取所有部门出错，message={}",e.getMessage());
 			throw new Exception(e.getMessage());
         }
        return department;
    }
    
    /**
     * 得到部门下的所有部门
     * @param departmentId
     * @return
     * @throws Exception
     */
    public  List<DingDeptVO> getDingAllSubDept(String departmentId) throws Exception {
    	List<DingDeptVO> department = null;
    	 try {
    		String accToken = getDingAccessToken();
         	StringBuffer param=new StringBuffer();
         	param.append("access_token=").append(accToken).append("&fetch_child").append("true").append("&id=").append(departmentId);
         	LOG.info("调用钉钉获取所有子部门信息参数,departmentId={},parama={}",departmentId,param.toString());
         	department=getDingInfo(department, param.toString());
         } catch (Exception e) {
         	LOG.error("钉钉获取所有部门出错，message={}",e.getMessage());
 			throw new Exception(e.getMessage());
         }
        return department;
    }
    
    
    private   List<DingDeptVO> getDingInfo(List<DingDeptVO> department,String param) throws Exception{
     	String strResp;
		try {
			strResp = HttpUtility.sendGet(departmentUrl, param);
			LOG.info("钉钉所有部门响应,strResp={}",strResp);
			DingApiRespVO resp = StringUtility.parseObject(strResp, DingApiRespVO.class);
			if (resp != null && resp.errcode == 0) {
				department=resp.department;
			}
		} catch (Exception e) {
         	LOG.error("钉钉获取所有部门出错，message={}",e.getMessage());
 			throw new Exception(e.getMessage());
		}
		return department;
    }
    
    
    /**
     * 获取钉钉部门下的成员信息
     * @param departmentId 部门id
     * @return 
     * @throws Exception
     */
    public  List<DingUserVO> getDingMemberByDepId(String departmentId) throws Exception {
    	List<DingUserVO> userlist = null;
    	String KEY_BIRTHDAY = "生日";
    	String KEY_GENDER = "性别";
    	 try {
    		String accToken = getDingAccessToken();
         	StringBuffer param=new StringBuffer();
         	param.append("access_token=").append(accToken).append("&department_id=").append(departmentId).append("&order=").append("entry_desc");
         	LOG.info("调用钉钉获取部门下成员信息参数,departmentId={},parama={}",departmentId,param.toString());
         	String strResp= HttpUtility.sendGet(userUrl, param.toString());
         	LOG.info("钉钉部门下成员响应,departmentId={},strResp={}",departmentId,strResp);
     		DingApiRespVO resp = StringUtility.parseObject(strResp, DingApiRespVO.class);
			if (resp != null && resp.errcode == 0 && resp.userlist != null && resp.userlist.size() > 0) {
				for (DingUserVO u : resp.userlist) {
					u.joinDate = new Date(u.hiredDate);
					if (u.extattr != null) {
						// 生日、性别在扩展字段中
						if (u.extattr.containsKey(KEY_BIRTHDAY)) {
							u.birthday = StringUtility.convertToDate(u.extattr.get(KEY_BIRTHDAY), null);
						}
						if (u.extattr.containsKey(KEY_GENDER)) {
							u.gender = u.extattr.get(KEY_GENDER);
						}
					}
				}
				userlist=resp.userlist;
			}
         } catch (Exception e) {
         	LOG.error("钉钉获取部门下的成员出错，message={}",e.getMessage());
 			throw new Exception(e.getMessage());
         }
        return userlist;
    }
    

    /**
     * 得到部门到跟部门的ID [123,79789,1]
     * @param departmentId
     * @return
     * @throws Exception
     */
    public  JSONArray getDingParentDepts(String departmentId) throws Exception {
    	JSONArray array = null;
    	 try {
    		String accToken = getDingAccessToken();
         	StringBuffer param=new StringBuffer();
         	param.append("access_token=").append(accToken).append("&id=").append(departmentId);
         	LOG.info("调用钉钉获取所有子部门到跟部门的路径ID,departmentId={},parama={}",departmentId,param.toString());
			String strResp = HttpUtility.sendGet(parentSubDeptUrl, param.toString());
			LOG.info("调用钉钉获取所有子部门到跟部门的路径响应,strResp={}",strResp);
			JSONObject jsonObject= JSONObject.parseObject(strResp);
			Integer code=(Integer) jsonObject.get("errcode");
			if(code==0){
				array=  (JSONArray) jsonObject.get("parentIds");
			}
			
         } catch (Exception e) {
         	LOG.error("调用钉钉获取所有子部门到跟部门的路径ID，message={}",e.getMessage());
 			throw new Exception(e.getMessage());
         }
        return array;
    }
    
    

}
