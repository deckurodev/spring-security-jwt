package saga.sec.api.controller.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saga.sec.api.controller.member.dto.request.MemberRequest;
import saga.sec.api.service.member.MemberService;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public void registerNewMember(@RequestBody MemberRequest request) {
		memberService.register(request.email(), request.password(), request.role());
	}

	@PutMapping("/auth/add")
	public void registerNewAuth(@RequestBody MemberRequest request) {
		memberService.register(request.email(), request.password(), request.role());
	}
}
