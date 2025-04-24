package saga.sec.api.controller.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import saga.sec.auth.config.security.SecurityUtil;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

	@GetMapping
	public void test() {
		log.info("auth : {}", SecurityUtil.currentUserId());
	}
}
