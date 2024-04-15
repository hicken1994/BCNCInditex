package com.example;


import com.example.ecommerce.api.dto.PriceDto;
import com.example.ecommerce.infrastructure.InditexAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.Constant.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InditexAdapter.class)
class PriceControllerTests {

    @LocalServerPort
    private int port;
    private String baseUrl;
    private static RestTemplate templateRest;

    @BeforeAll
    public static void init() {
        templateRest = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = String.format("http://localhost:%d/inditex/api/", port);
    }

    private void generateTestRequest(LocalDateTime startDate, LocalDateTime endDate, BigDecimal expectedPrice,
                                    String testUrlSuffix, int tariff, boolean isSuccessfulTestExpected) {
        PriceDto expectedDto = new PriceDto(ID_PRODUCT, ID_BRAND, tariff, startDate, endDate, expectedPrice);
        PriceDto response = templateRest.getForObject(baseUrl + testUrlSuffix, PriceDto.class);
        if (isSuccessfulTestExpected)
            assertEquals(response, expectedDto);
        else
            assertNotEquals(response, expectedDto);
    }
    @Test
    void test1_successful() {
        generateTestRequest(
                LocalDateTime.parse("2020-06-14-00.00.00", dateFormats),
                LocalDateTime.parse("2020-12-31-23.59.59", dateFormats),
                new BigDecimal("35.50"),
                String.format(URL_TEMPLATE, "2020-06-14T10:00:00Z"),
                RATE_TARIFF_1, true);
    }


    @Test
    void test1_fail() {
        generateTestRequest(
                LocalDateTime.parse("2020-06-14-00.00.00", dateFormats),
                LocalDateTime.parse("2020-12-31-23.59.59", dateFormats),
                new BigDecimal("37.50"),
                String.format(URL_TEMPLATE, "2020-06-14T10:00:00Z"),
                RATE_TARIFF_1, false);
    }


    @Test
    void test2_successful() {
        generateTestRequest(
                LocalDateTime.parse("2020-06-14-15.00.00", dateFormats),
                LocalDateTime.parse("2020-06-14-18.30.00", dateFormats),
                new BigDecimal("25.45"),
                String.format(URL_TEMPLATE, "2020-06-14T16:00:00Z"),
                RATE_TARIFF_2,true);
    }

    @Test
    void test3_successful() {
        generateTestRequest(
                LocalDateTime.parse("2020-06-14-00.00.00", dateFormats),
                LocalDateTime.parse("2020-12-31-23.59.59", dateFormats),
                new BigDecimal("35.50"),
                String.format(URL_TEMPLATE, "2020-06-14T21:00:00Z"),
                RATE_TARIFF_1,true);
    }

    @Test
    void test4_successful() {
        generateTestRequest(
                LocalDateTime.parse("2020-06-15-00.00.00", dateFormats),
                LocalDateTime.parse("2020-06-15-11.00.00", dateFormats),
                new BigDecimal("30.50"),
                String.format(URL_TEMPLATE, "2020-06-15T10:00:00Z"),
                RATE_TARIFF_3, true);
    }

    @Test
    void test5_successful() {
        generateTestRequest(
                LocalDateTime.parse("2020-06-15-16.00.00", dateFormats),
                LocalDateTime.parse("2020-12-31-23.59.59", dateFormats),
                new BigDecimal("38.95"),
                String.format(URL_TEMPLATE, "2020-06-16T21:00:00Z"),
                RATE_TARIFF_4,true);
    }

    @Test
    void test6_inexistentPricesFails() {
        // Call and Verify
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            templateRest.getForObject(baseUrl.concat(TEST6_URL_NO_ENCONTRADA), PriceDto.class);
        });
    }
}
