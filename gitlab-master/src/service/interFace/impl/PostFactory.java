package service.interFace.impl;

import lombok.Builder;
import lombok.Getter;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import service.interFace.HttpFactory;

@Builder
public class PostFactory extends HttpFactory {

    @Getter
    private final String url;

    public PostFactory(String url) {
        super(url);
        this.url=url;
    }

    @Override
    public PostSender produce() {
        CloseableHttpClient httpClients =super.getHttpClients();
        HttpGet httpGet = new HttpGet(super.getUrl());
        return new PostSender(httpGet,httpClients);
    }
}
