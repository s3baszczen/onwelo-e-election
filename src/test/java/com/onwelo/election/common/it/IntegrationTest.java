package com.onwelo.election.common.it;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = PostgresTestContainer.class)
public @interface IntegrationTest {
}
