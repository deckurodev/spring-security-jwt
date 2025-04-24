package saga.sec.auth.config.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import saga.sec.auth.config.jwt.JwtAuthenticationFilter;
import saga.sec.auth.config.jwt.JwtTokenProvider;
import saga.sec.auth.config.security.entrypoint.JwtAuthenticationEntryPoint;
import saga.sec.auth.config.security.provider.JwtAuthenticationProvider;
import saga.sec.auth.config.security.service.JwtUserDetailsService;
import saga.sec.auth.exception.JwtAccessDeniedHandler;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity http,
		JwtAuthenticationFilter jwtAuthenticationFilter,
		JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler
	) throws Exception {
		http
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable);

		http
			.exceptionHandling(ex -> ex
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			);

		http
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		http.authorizeHttpRequests(auth -> auth
			.requestMatchers(HttpMethod.POST, "/api/member").permitAll()
			.requestMatchers("/auth/**").permitAll()
			.anyRequest().authenticated()
		);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
		JwtUserDetailsService userDetailsService) {
		List<AuthenticationProvider> list1 = List.of(
			new JwtAuthenticationProvider(jwtTokenProvider, userDetailsService));
		ProviderManager parent = new ProviderManager(list1);

		List<AuthenticationProvider> list2 = List.of(new AnonymousAuthenticationProvider("key"));
		ProviderManager providerManager1 = new ProviderManager(list2, parent);
		return new JwtAuthenticationFilter(jwtTokenProvider, providerManager1);
	}
}
