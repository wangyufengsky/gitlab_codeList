package service.interFace.impl;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import service.interFace.HttpFactory;


@Builder
public class GetFactory extends HttpFactory {

    @Getter
    private final String url;

    public GetFactory(String url) {
        super(url);
        this.url=url;
    }

    @Override
    public GetSender produce() {
        CloseableHttpClient httpClients =super.getHttpClients();
        HttpGet httpGet = new HttpGet(super.getUrl());
        return new GetSender(httpGet,httpClients);
    }

}
