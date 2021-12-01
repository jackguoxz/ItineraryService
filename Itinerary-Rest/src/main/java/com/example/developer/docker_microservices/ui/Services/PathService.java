package com.example.developer.docker_microservices.ui.Services;

import com.example.developer.docker_microservices.ui.auth.Auth;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Auth
@Service
public class PathService {
    @Resource
    private RestTemplate restTemplate;

    public void PathServcie()
    {

    }

    public List<String> listItinerary(String url,String id)
    {
        ResponseEntity<List<String>> listOfString= restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<String>>() {});
        List<String> result = listOfString.getBody();
        return result;
    }
}
