package service.interFace.impl;

import lombok.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import service.interFace.Sender;
import service.interFace.SenderFactory;


@Setter
@Getter
@ToString
public class GetSender  extends SenderFactory {

    public GetSender(HttpUriRequest request, CloseableHttpClient httpClient) {
        super(request, httpClient);
    }

    @Override
    public String Send() {
        HttpGet httpGet = (HttpGet)super.getRequest();
        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        httpGet.setConfig(defaultConfig);
        String result="";
        try(CloseableHttpResponse response = super.getHttpClient().execute(httpGet)){
            HttpEntity httpEntity = response.getEntity();
            result= EntityUtils.toString(httpEntity,"utf-8");
            EntityUtils.consume(httpEntity);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
