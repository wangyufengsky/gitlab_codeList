package service.interFace;

import lombok.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

@Setter @Getter @ToString @AllArgsConstructor
public abstract class SenderFactory implements Sender {

    private HttpUriRequest request;
    private CloseableHttpClient httpClient;

    @Override
    public abstract String Send();
}
