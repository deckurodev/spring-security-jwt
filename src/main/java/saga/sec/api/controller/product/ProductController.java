package saga.sec.api.controller.product;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import saga.sec.auth.config.security.SecurityUtil;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

	@GetMapping
	public void look() {
		log.info("look : {}", SecurityUtil.currentUserId());
	}

	@PreAuthorize(value = "hasRole('ADMIN')")
	@PostMapping
	public void create() {
		log.info("create : {}", SecurityUtil.currentUserId());
	}
}
