package com.example;



import com.example.ecommerce.domain.PriceProduct;
import com.example.ecommerce.infrastructure.TestDataConfigService;
import com.example.ecommerce.ports.PriceProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TestDataConfigServiceTest {

    @Mock
    private PriceProductRepository priceProductRepository;

    private TestDataConfigService testDataConfigService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        this.testDataConfigService = new TestDataConfigService(priceProductRepository) {
            @Override
            protected List<PriceProduct> processInputFile(String inputFilePath) {
                return List.of(new PriceProduct());
            }
        };
    }

    @Test
     void testInsertDataTest() {
        this.testDataConfigService.insertDataTest();
        verify(priceProductRepository, times(1)).saveAll(List.of(new PriceProduct()));
    }
}
