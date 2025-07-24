package com.test.productos.productos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ProductosApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
    void mainMethodRunsSuccessfully() {
		ProductosApplication.main(new String[]{});
    }
}
