package com.yks.urc.fw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtility {

	private static Logger LOG = LoggerFactory.getLogger(HttpUtility.class);

	public static String httpGet(String url) {
		CloseableHttpClient httpCilent = HttpClients.createDefault();// Creates CloseableHttpClient instance with default configuration.
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpCilent.execute(httpGet);
			return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
		} catch (IOException e) {
			LOG.error(String.format("httpGet:%s", httpGet), e);
		} finally {
			try {
				httpCilent.close();// 释放资源
			} catch (IOException e) {
				LOG.error("释放资源出错:[%s]",e);
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String sendPost(String url, String param) throws Exception {
		return doPost(url, param, "utf-8");
	}

	/**
	 * 向指定 URL 发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String sendGet(String url, String param) throws Exception {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url;
			if (param != null && !"".equals(param)) {
				urlName += "?" + param;
			}
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setConnectTimeout(4000);
			conn.connect();// 建立实际的连接
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));// 定义BufferedReader输入流来读取URL的响应
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			LOG.error("发送get请求出错,message={}", e.getMessage());
			throw new Exception(e.getMessage());
		} finally {// 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				LOG.error("关闭流出错,message={}", ex.getMessage());
				throw new Exception(ex.getMessage());
			}
		}
		return result;
	}


	/**
	 *  httpclient post请求
	 * @param url
	 * @param paramBody
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, String paramBody, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数

			StringEntity se = new StringEntity(paramBody);
			httpPost.setEntity(se);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000)
						.setSocketTimeout(120 * 1000).build();
			httpPost.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			LOG.error("发送post请求出错:[%s]",ex);
		}
		return result;
	}

	/**
	 *  发送带有请求头的Get请求
	 * @param
	 * @return
	 * @Author lwx
	 * @Date 2018/8/13 12:06
	 */
	public static String getHasHeaders(String url, Map<String,String> headMap) {
		// Creates CloseableHttpClient instance with default configuration.
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			if (headMap != null && headMap.size() >0){
				//遍历map
				for (Map.Entry str :headMap.entrySet() ){
					httpGet.setHeader(str.getKey().toString(),str.getValue().toString());
				}
			}
			HttpResponse httpResponse = httpCilent.execute(httpGet);
			return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
		} catch (IOException e) {
			LOG.error(String.format("httpGet:%s", httpGet), e);
		} finally {
			try {
				httpCilent.close();// 释放资源
			} catch (IOException e) {
				LOG.error("释放资源出错:[%s]",e);
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 *  http post 请求, 带请求头
	 * @param
	 * @return
	 * @Author lwx
	 * @Date 2018/8/13 14:08
	 */
	public static String postHasHeaders(String url, Map<String,String> headMap, String paramBody, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			//设置头部
			if (headMap != null && headMap.size() >0){
				//遍历map
				for (Map.Entry str :headMap.entrySet() ){
					httpPost.setHeader(str.getKey().toString(),str.getValue().toString());
				}
			}
			// 设置参数
			StringEntity se = new StringEntity(paramBody);
			httpPost.setEntity(se);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000)
					.setSocketTimeout(120 * 1000).build();
			httpPost.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			LOG.error("发送post请求出错:[%s]",ex);
		}
		return result;
	}

}
