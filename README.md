## Spring Security 학습

> Spring Security의 인증/인가 구조를 학습하고, JWT 인증 기반으로 구성
> 계속해서 컴포넌트 공부하기 위한 프로젝트... (블로그??)

## 기술 스택

- **Spring Security 6.x**

![sec.png](src/main/resources/static/sec.png)


### filter 빈 구성 시에 JwtAuthenticationProvider 설정
```
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
```