package com.sdw.soft;


import com.sdw.soft.gateway.GatewayApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GatewayApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
public class AppTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() throws Exception{
        stubFor(get(urlEqualTo("/get"))
                .willReturn(aResponse()
                        .withBody("{'headers':{'hello':'world'}}")
                        .withHeader("Content-Type","application/json")));
        stubFor(get(urlEqualTo("/delay/3"))
                .willReturn(aResponse()
                        .withBody("no fallback")
                        .withFixedDelay(3000)));


        webTestClient.get().uri("/get")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.headers.hello").isEqualTo("world");

        webTestClient
                .get().uri("/delay/3")
                .header("Host", "www.hystrix.com")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        response -> assertThat(response.getResponseBody()).isEqualTo("fallback".getBytes()));
    }
}
