package com.viacepjavaspring.service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.util.HashMap;
import java.util.Map;

import com.viacepjavaspring.utils.NullX509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    public HttpEntity get(final String url, HashMap<String, String> params) throws Exception {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            TrustManager[] trustManagerArray = {new NullX509TrustManager()};
            context.init(null, trustManagerArray, null);

            URIBuilder uriBuilder = new URIBuilder(url);

            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    uriBuilder.setParameter(key, value);
                }
            }
            HttpGet get = new HttpGet(uriBuilder.build());

            HttpClientBuilder clientBuilder = HttpClients.custom();
            HttpClientBuilder httpClientBuilder = clientBuilder.setSSLContext(context);
            CloseableHttpClient httpClient = httpClientBuilder.build();

            CloseableHttpResponse response = httpClient.execute(get);

            if(response.getStatusLine().getStatusCode() != 200){
                throw new Exception("Problem to make a request");
            }

            return response.getEntity();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
