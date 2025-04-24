package saga.sec.api.service.product;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import saga.sec.domain.product.Product;
import saga.sec.domain.product.ProductRepository;

@DisplayName("상품 권한 테스트")
@SpringBootTest
class ProductServiceTest {
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Test
	@WithMockUser(username = "10000", roles = "USER") // SecurityContext에 mock 유저 ID를 넣음
	void 상품_생성_성공() {
		// given
		String name = "테스트 상품";
		BigDecimal price = new BigDecimal("12345");

		// when
		productService.createProduct(name, price);

		// then
		Product product = productRepository.findAll().get(0);
		assertThat(product.getName()).isEqualTo(name);
		assertThat(product.getPrice().intValue()).isEqualTo(12345);
	}

	@Test
	@WithMockUser(username = "10000", roles = "GUEST")
	void 권한_없는_사용자_상품_생성_실패() {
		// given
		String name = "허용되지 않은 상품";
		BigDecimal price = new BigDecimal("5000");

		// when & then
		assertThrows(AccessDeniedException.class, () -> {
			productService.createProduct(name, price);
		});
	}
}