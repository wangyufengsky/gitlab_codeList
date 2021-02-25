package service.interFace.impl;

import lombok.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import service.interFace.SenderFactory;

@Setter
@Getter
@ToString
public class PostSender extends SenderFactory {

    public PostSender(HttpUriRequest request, CloseableHttpClient httpClient) {
        super(request,httpClient);
    }

    @Override
    public String Send() {
        HttpPost httpPost = (HttpPost)super.getRequest();
        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        httpPost.setConfig(defaultConfig);
        String result="";
        try(CloseableHttpResponse response = super.getHttpClient().execute(httpPost)){
            HttpEntity httpEntity = response.getEntity();
            result= EntityUtils.toString(httpEntity,"utf-8");
            EntityUtils.consume(httpEntity);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
