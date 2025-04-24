package saga.sec.auth.config.security.token;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import saga.sec.auth.config.security.AccountUserDto;

public class JwtTokenUserDetails implements UserDetails {
	private AccountUserDto accountUserDto;

	public JwtTokenUserDetails(AccountUserDto accountUserDto) {
		this.accountUserDto = accountUserDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return accountUserDto.authorities();
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return accountUserDto.userId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}
}
