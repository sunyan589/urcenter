package com.yks.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.yks.urc.exception.ErrorCode;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;
import com.yks.urc.motan.service.api.IUrcService;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.UserVO;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MotanUserController {

//    @MotanReferer
    private IUrcService urcService;
    private static final String ApiUrlPrefix = "/urc/motan/service/api/IUrcService/";

    @RequestMapping(ApiUrlPrefix + "{method}")
    public String urcService(@RequestBody String body, @PathVariable("method") String method, HttpServletRequest request)
            throws Exception {
        System.out.println(body);
        System.out.println(method);
//        System.out.println(method);

        ResultVO rslt = null;
        if (StringUtility.stringEqualsIgnoreCase("login", method)) {
            // login接口，直接调用login rpc
            Map<String, String> mapArg = new HashMap<>();
            String[] arrKeyValue = URLDecoder.decode(body, "utf-8").split("&");
            for (String kv : arrKeyValue) {
                String[] arrKv = kv.split("=");
                mapArg.put(arrKv[0], arrKv[1]);
            }
            mapArg.put("ip", IpUtils.getIpAddr(request));
            rslt = urcService.login(mapArg);
        } else {
            // 先调 funcPermitValidate 校验权限
            JSONObject jBody = StringUtility.parseString(body);
            Map<String, String> mapArg = new HashMap<>();
            mapArg.put(StringConstant.ticket, jBody.getString(StringConstant.ticket));
            mapArg.put(StringConstant.operator, jBody.getString(StringConstant.operator));
            mapArg.put(StringConstant.funcVersion, jBody.getString(StringConstant.funcVersion));
            //mapArg.put(StringConstant.ip, jBody.getString(StringConstant.ip));
            mapArg.put("ip", IpUtils.getIpAddr(request));
            mapArg.put(StringConstant.apiUrl, String.format("%s%s", ApiUrlPrefix, method));

            rslt = urcService.funcPermitValidate(mapArg);
            if (rslt != null && StringUtility.stringEqualsIgnoreCase(ErrorCode.E_100006.getState(),
                    rslt.state)) {
                // 权限ok
                try {
                    rslt = (ResultVO) ReflectionTestUtils.invokeMethod(urcService, method, body);
                } catch (Exception ex) {
                    rslt = (ResultVO) ReflectionTestUtils.invokeMethod(urcService, method);
                }
            } else {
                // 权限不ok
            }
        }
        return StringUtility.toJSONString_NoException(rslt);
    }

    @RequestMapping("/sayHello/{msg}")
    public ResultVO sayHello(@PathVariable String msg) {
        // ResultVO rslt = userService.syncUserInfo();
        // return "110";
        UserVO curUser = new UserVO();
        curUser.userName = "py";
        curUser.userName = "py_" + UUID.randomUUID().toString();
        return null;//urcService.syncUserInfo(curUser);// .sayHello(msg);
    }

	/*
     * @RequestMapping("/sayHello2/{msg}") public String sayHello2(@PathVariable
	 * String msg) { // ResultVO rslt = userService.syncUserInfo(); // return "110";
	 * return urcService.sayHello2(msg);// .sayHello(msg); }
	 */

    @RequestMapping("/login/{userName}/{pwd}")
    public ResultVO login(@PathVariable String userName, @PathVariable String pwd) {
        return loginTest(userName, pwd);
    }

    private ResultVO loginTest(String userName, String pwd) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("pwd", pwd);
        map.put("ip", "127.0.0.1");
        return urcService.login(map);
    }

    private ExecutorService service = Executors.newCachedThreadPool(); // 创建一个线程池
    private AtomicInteger number = new AtomicInteger(0);

    @RequestMapping("/startRequest")
    public ResultVO startRequest(@RequestParam("c") int c) {
        ResultVO rslt = new ResultVO<>();
        UserLoginRunnable.init();
        String strUserName = String.format("%s", StringUtility.dt2Str(new Date(), StringUtility.DtFormatString_yyyyMMddHHmmss));
        rslt.msg = String.format("并发数:%s userName:%s pwd:%s", c, strUserName, strUserName);

        number.set(0);
        for (int i = 0; i < c; i++) {
            number.getAndIncrement();
            service.execute(new UserLoginRunnable(String.format("%s-%s", strUserName, number.get()), strUserName, urcService));
        }
        return rslt;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/stopRequest")
    public ResultVO stopRequest() {
        UserLoginRunnable.isStop = true;
        ResultVO rslt = new ResultVO<>();
        try {
            rslt.msg = String.format("%s", UserLoginRunnable.getStatStr());
        } catch (Exception ex) {
            logger.error("stopRequest", ex);
            rslt.msg = ex.getMessage();
        }
        return rslt;
    }
}
