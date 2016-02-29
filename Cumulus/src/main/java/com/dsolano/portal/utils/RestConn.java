package com.dsolano.portal.utils;

import com.dsolano.portal.Configurations;
import com.dsolano.portal.authentication.TokenAuthenticationService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author odelarosa
 */
public class RestConn {
    private String APIurl;
    private String servletUrl;
    private Properties properties = new Properties();
    
    public RestConn(){
        String propPath = Configurations.PATH + "Settings.properties";
        
        try(FileInputStream fis =  new FileInputStream(propPath)){
            properties.load(fis);
        }catch (FileNotFoundException ex){
            Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.APIurl = properties.getProperty("Cumulus_API");
        this.servletUrl = properties.getProperty("Ecaresoft_URL");
        
            
    }
    
    
    public String getRestResponse(String restUrl, List<NameValuePair> params) {
        String resp = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
             if(params != null){
                 String getParams = URLEncodedUtils.format(params, "utf-8");
                 restUrl = restUrl.concat("/?").concat(getParams);
             }
            HttpGet httpget = new HttpGet(APIurl+restUrl);
            httpget.addHeader("Authorization", "Token ".concat(TokenAuthenticationService.getToken()));
           
            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = (final HttpResponse response) -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            resp = httpclient.execute(httpget, responseHandler);
        } catch (IOException ex) {
            Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp;
    }
    
    public String postRestResponse(String restUrl, List<NameValuePair> params){
        String json = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
             HttpPost httppost = new HttpPost(APIurl+restUrl);
             System.out.println("Executing request " + httppost.getRequestLine());

             if(TokenAuthenticationService.getToken() != null){
                httppost.addHeader("Authorization", "Token ".concat(TokenAuthenticationService.getToken()));
             }
             
             httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
             
             ResponseHandler<String> responseHandler = (final HttpResponse response) -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            json = httpclient.execute(httppost, responseHandler);

        } catch (IOException ex) {
            Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return json;
    }
    
    public byte[] getServletAttatchment(String uuid){
        byte[] attatchment = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(servletUrl+"file?uuid="+uuid);
            System.out.println("Executing request " + httpget.getRequestLine());

            ResponseHandler<byte[]> responseHandler = (final HttpResponse response) -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toByteArray(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            attatchment = httpclient.execute(httpget, responseHandler);
        } catch (IOException ex) {
            Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return attatchment;
        
    }
    
}
