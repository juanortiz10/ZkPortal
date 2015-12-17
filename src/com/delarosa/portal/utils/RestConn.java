package com.delarosa.portal.utils;

import com.delarosa.portal.authentication.TokenAuthenticationService;
import java.io.IOException;
import java.util.List;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author odelarosa
 */
public class RestConn {

    public static String getRestResponse(String restUrl) {
        String test = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(restUrl+".json");
            httpget.addHeader("Authorization", "Token ".concat(TokenAuthenticationService.getToken()));

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = (final HttpResponse response) -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            test = httpclient.execute(httpget, responseHandler);
        } catch (IOException ex) {
            Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(RestConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return test;
    }
    
    public static String postRestResponse(String restUrl, List<NameValuePair> params){
        String json = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
             HttpPost httppost = new HttpPost(restUrl);
             
             if(TokenAuthenticationService.getToken() != null){
                httppost.addHeader("Authorization", "Token ".concat(TokenAuthenticationService.getToken()));
             }
             
             httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
             
             ResponseHandler<String> responseHandler = (final HttpResponse response) -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
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
    
}
