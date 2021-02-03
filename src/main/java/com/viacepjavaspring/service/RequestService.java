package com.viacepjavaspring.service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.viacepjavaspring.entity.EnderecoEntity;
import com.viacepjavaspring.utils.NullX509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

    public void post(final String url, HashMap<String, String> headers, EnderecoEntity entity) {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            TrustManager[] trustManagerArray = {new NullX509TrustManager()};
            context.init(null, trustManagerArray, null);

            HttpPost post = new HttpPost(url);

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    post.addHeader(key, value);
                }
            }

            // add request parameter, form parameters
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("filename", entity.getBairro()));
            urlParameters.add(new BasicNameValuePair("status", entity.getCep()));
            urlParameters.add(new BasicNameValuePair("order", entity.getLocalidade()));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpClientBuilder clientBuilder = HttpClients.custom();
            HttpClientBuilder httpClientBuilder = clientBuilder.setSSLContext(context);
            CloseableHttpClient httpClient = httpClientBuilder.build();

            CloseableHttpResponse response = httpClient.execute(post);

            if(response.getStatusLine().getStatusCode() != 201){
                throw new Exception("Problem to make a request");
            }

            HttpEntity entityResponse = response.getEntity();
            if (entityResponse != null) {
                int length = EntityUtils.toByteArray(entityResponse).length;
                System.out.println("Length response: " + length);
            }
        } catch (Exception e) {
            return;
        }
    }
}
