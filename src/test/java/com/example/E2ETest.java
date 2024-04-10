package com.example;

import com.example.ecommerce.api.Dto.PriceDto;
import com.example.ecommerce.boot.InditexAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.Constant.URL_TEMPLATE;
import static com.example.Constant.URL_TEMPLATE2;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InditexAdapter.class)
public class E2ETest {
    @LocalServerPort
    public int port;
    private String baseUrl = "http://localhost";

    @Autowired
    private static TestRestTemplate restTemplate;
    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();

    }
    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl + ":" + port + "/inditex/api/";
    }
    @Test
    public void testGetPricePerProduct() {

        // Startup
        Integer productId = 35455;
        Integer brandId = 1;

        // Call EndPoint ApiPrices
        ResponseEntity<PriceDto> responseEntity = restTemplate
                .getForEntity(baseUrl.concat(String.format(URL_TEMPLATE, "2020-06-14T10:00:00Z")), PriceDto.class, productId, brandId);

        // Return getStatusCode, and a PriceDto.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getProductId()).isEqualTo(productId);
    }
    @Test
    public void testGetPricePerProductNotFound() {

        Integer productId = 99999;
        Integer brandId = 1;
        String priceDate = "2020-06-14T10:00:00Z";

        String fullUrl = URL_TEMPLATE2.replace("brandId=?", "brandId=" + brandId)
                .replace("productId=?", "productId=" + productId).replace("priceDate=?", "priceDate=" + priceDate);
        ResponseEntity<PriceDto> responseEntity = restTemplate
                .getForEntity(baseUrl.concat(fullUrl), PriceDto.class, productId, brandId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
    }



    @Test
    public void testGetPricebyProductBadRequest() {

        Integer productId = 35455;
        Integer brandId = 1;
        String dateString = "2020-06-14T10:00:00Z";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime priceDate = LocalDateTime.parse(dateString, formatter);
        HttpEntity<PriceDto> request = new HttpEntity<>(new PriceDto());

        String fullUrl = URL_TEMPLATE2.replace("brandId=?", "brandId=" + brandId)
                .replace("productId=?", "productId=" + productId).replace("priceDate=?", "priceDate=" + priceDate);
        ResponseEntity<PriceDto> responseEntity = restTemplate.postForEntity(baseUrl.concat(fullUrl), request, PriceDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
    }
    @Test
    public void testGetPricePerProductByBrand() {

        Integer productId = 35455;
        Integer brandId = 1;
        String priceDate = "2020-06-14T10:00:00Z";

        String fullUrl = URL_TEMPLATE2.replace("brandId=?", "brandId=" + brandId)
                .replace("productId=?", "productId=" + productId).replace("priceDate=?", "priceDate=" + priceDate);
        ResponseEntity<PriceDto> responseEntity = restTemplate
                .getForEntity(baseUrl.concat(fullUrl), PriceDto.class, productId, brandId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getProductId()).isEqualTo(productId);
    }

    @Test
    public void testGetPricePerProductByBrandNotFound() {

        Integer productId = 99999;
        Integer brandId = 1;
        String priceDate = "2020-06-14T10:00:00Z";

        String fullUrl = URL_TEMPLATE2.replace("brandId=?", "brandId=" + brandId)
                .replace("productId=?", "productId=" + productId).replace("priceDate=?", "priceDate=" + priceDate);
        ResponseEntity<PriceDto> responseEntity = restTemplate
                .getForEntity(baseUrl.concat(fullUrl), PriceDto.class, productId, brandId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
    }





}