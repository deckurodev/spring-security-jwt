package saga.sec.api.controller.member.dto.request;

import saga.sec.domain.role.RoleType;

public record MemberRequest(
	String email,
	String password,
	RoleType role
) {}
