package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.config.security.TokenService;
import br.com.digitalbooking.carros.repository.CityRepository;
import br.com.digitalbooking.carros.repository.ProductRepository;
import br.com.digitalbooking.carros.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.digitalbooking.carros.TestData.*;

@SpringBootTest
public class BookinkServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private TokenService tokenService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resetProductData();
    }

    /*
    @Test
    public void

     */

}
