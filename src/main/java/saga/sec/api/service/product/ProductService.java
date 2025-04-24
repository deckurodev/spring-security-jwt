package saga.sec.api.service.product;

import java.math.BigDecimal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saga.sec.auth.config.security.SecurityUtil;
import saga.sec.domain.product.Product;
import saga.sec.domain.product.ProductRepository;

@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {
	private final ProductRepository productRepository;

	public void createProduct(final String name, final BigDecimal price) {
		Product product = Product.builder()
			.name(name)
			.price(price)
			.memberId(SecurityUtil.currentUserId())
			.build();
		productRepository.save(product);
	}
}
