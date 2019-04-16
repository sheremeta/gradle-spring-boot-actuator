package com.poc.fare.infra;

import com.poc.fare.Fare;
import com.poc.fare.FareJacksonModule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Collections.singletonList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ControllerBaseTest {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();
    {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        mappingJackson2HttpMessageConverter.setObjectMapper(new Jackson2ObjectMapperBuilder()
            .failOnUnknownProperties(false)
            .serializationInclusion(NON_NULL)
            .modules(new FareJacksonModule())
            .build());

        restTemplate.setMessageConverters(singletonList(mappingJackson2HttpMessageConverter));
    }

    protected String url(String path) {
        return "http://localhost:" + port + "" + contextPath + path;
    }

    protected ResponseEntity<Fare> get(String url) {
        try {
            return restTemplate.getForEntity(url, Fare.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }
}
