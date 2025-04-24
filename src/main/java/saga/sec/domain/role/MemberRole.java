package saga.sec.domain.role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saga.sec.domain.member.Member;

@Getter
@NoArgsConstructor
@Entity
@IdClass(MemberRoleId.class)
public class MemberRole {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId", insertable = false, updatable = false)
	private Member member;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId", insertable = false, updatable = false)
	private Role role;

	public MemberRole(Member member, Role role) {
		this.member = member;
		this.role = role;
	}
}
