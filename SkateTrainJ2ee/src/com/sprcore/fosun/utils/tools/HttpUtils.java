package com.sprcore.fosun.utils.tools;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
    private static Log logger = LogFactory.getLog(HttpUtils.class);
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        String url = "http://192.168.200.40:13721/UMAPIService.svc/apis/AddToADGroup/?email=chihl@fosun.com&groupname=保险集团共享";
        RModel rModel = getHttpGetReturn(url);
        System.out.println(url+"\n     "+rModel.getHtml());

    }



    /**
     * DingDing的http get请求
     * @param url
     * @return
     * @throws Exception
     */
    public static RModel getHttpGetReturn(String url) throws Exception {
    	RModel rModel = new HttpUtils.RModel();
        logger.info("[DingDing]Call-sp:" + url);
        String ret = null;
        SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf).build();

        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ret = EntityUtils.toString(entity, "UTF-8");
//                logger.info("Response content: "
//                        + ret);
            }
//            System.out.println("status code="+response.getStatusLine().getStatusCode());
            rModel.setHtml(ret);
            rModel.setResponseCode(response.getStatusLine().getStatusCode());
        } finally {
            response.close();
        }
        
        logger.info("[DingDing]Call-sr:" + ret);
        return rModel;
    }
    /**
     * DingDing的http post请求
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    private static RModel getHttpPostReturn(String url,String json) throws Exception {
        RModel rModel = new RModel();
    	logger.info("[DingDing]Call-sp:" + url + "  "+json);
        String ret = null;
        SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf).build();

        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json");
        httppost.setEntity(new StringEntity(json, "UTF-8"));

        CloseableHttpResponse response = httpclient.execute(httppost);
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ret = EntityUtils.toString(entity, "UTF-8");            
            }
            rModel.setHtml(ret);
            rModel.setResponseCode(response.getStatusLine().getStatusCode());
        } finally {
            response.close();
        }
        logger.info("[DingDing]Call-sr:" + ret);
        return rModel;
    } 




    private static RModel getHttpPostReturn(String url,Map param) throws Exception {
        RModel rModel = new RModel();
    	logger.info("[DingDing]Call-sp:" + url + "  "+param);
        String ret = null;
        SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf).build();

        HttpPost httppost = new HttpPost(url);


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator<String> it = param.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            nameValuePairs.add(new BasicNameValuePair(key, (String) param
                    .get(key)));
        }
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

        CloseableHttpResponse response = httpclient.execute(httppost);
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ret = EntityUtils.toString(entity, "UTF-8");            
            }
            rModel.setHtml(ret);
            rModel.setResponseCode(response.getStatusLine().getStatusCode());
        } finally {
            response.close();
        }
        logger.info("[DingDing]Call-sr:" + ret);
        return rModel;
    }     

    
   public static class RModel{
	   private String html;
	   private int responseCode;
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	   
	   
   }
}

