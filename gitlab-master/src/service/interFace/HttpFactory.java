package service.interFace;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public abstract class HttpFactory implements BaseFactory {
    @Getter @Setter
    private String url;
    @Getter
    private final CloseableHttpClient httpClients = HttpClients.createDefault();

    public HttpFactory(String url) {
        this.url = url;
    }

    @Override
    public abstract SenderFactory produce();


}
