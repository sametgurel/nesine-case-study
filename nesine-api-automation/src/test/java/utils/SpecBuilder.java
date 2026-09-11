package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.lang.reflect.Type;

/**
 * REST Assured specifications factory.
 * Configures base URL, HTTP timeouts, Jackson ObjectMapper, and Allure logging filter.
 */
public final class SpecBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    private SpecBuilder() {
        // Utility class
    }

    public static RequestSpecification getRequestSpec() {
        int timeoutMs = ConfigManager.getTimeoutMs();

        RestAssuredConfig config = RestAssuredConfig.config()
                .decoderConfig(io.restassured.config.DecoderConfig.decoderConfig().noContentDecoders())
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeoutMs)
                        .setParam("http.socket.timeout", timeoutMs)
                        .setParam("http.connection-manager.timeout", (long) timeoutMs))
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .jackson2ObjectMapperFactory(new Jackson2ObjectMapperFactory() {
                            @Override
                            public ObjectMapper create(Type type, String s) {
                                return OBJECT_MAPPER;
                            }
                        }));

        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUrl())
                .setRelaxedHTTPSValidation()
                .setAccept(ContentType.ANY)
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Encoding", "identity")
                .addHeader("User-Agent", "Nesine-Api-Automation-Suite/1.0")
                .setConfig(config)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();
    }

    public static ResponseSpecification getResponseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .build();
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
