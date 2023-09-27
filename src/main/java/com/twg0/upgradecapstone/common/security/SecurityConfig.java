package com.twg0.upgradecapstone.common.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.twg0.upgradecapstone.common.security.auth.PrincipalDetailsService;
import com.twg0.upgradecapstone.common.security.auth.handler.CustomAuthFailureHandler;
import com.twg0.upgradecapstone.common.security.auth.handler.CustomLogoutSuccessHandler;
import com.twg0.upgradecapstone.common.security.filter.JwtAuthenticationFilter;
import com.twg0.upgradecapstone.common.security.filter.JwtAuthorizationFilter;
import com.twg0.upgradecapstone.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final PrincipalDetailsService principalDetailsService;
	private final CustomAuthFailureHandler customAuthFailureHandler;
	private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserService userService;

	// 관리자 계정을 미리 생성
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailsService).passwordEncoder(bCryptPasswordEncoder);

		auth.inMemoryAuthentication()
			.withUser("관리자")
			.password(bCryptPasswordEncoder.encode("1234"))
			.roles("USER").roles("ADMIN");
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> corsConfigurationSource())
			.authorizeHttpRequests(
				authHttp -> authHttp
					.requestMatchers(
						new AntPathRequestMatcher("/api/notification/test"),
						new AntPathRequestMatcher("/api/users"),
						new AntPathRequestMatcher("/api/login/**"),
						new AntPathRequestMatcher("/users"),
						new AntPathRequestMatcher("/css/**"),
						new AntPathRequestMatcher("/images/**"),
						new AntPathRequestMatcher("/js/**")
					).permitAll()
					.requestMatchers(
						new AntPathRequestMatcher("/api/notification/token"),
						new AntPathRequestMatcher("/api/villages/**"),
						new AntPathRequestMatcher("/api/devices/**")
					).authenticated()
					.requestMatchers(
						new AntPathRequestMatcher("/api/admins/**")
					).hasRole("ADMIN")
			)
			.formLogin(
				formLogin -> formLogin
					.usernameParameter("email")
					.loginProcessingUrl("/api/login")
					.failureHandler(customAuthFailureHandler)
			)
			.logout(
				logout -> logout
					.logoutUrl("/api/logout")
					.logoutSuccessHandler(customLogoutSuccessHandler)
			)
			.addFilter(getJwtAuthenticationFilter())
			.addFilterBefore(getJwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(userService);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Bean
	public JwtAuthorizationFilter getJwtAuthorizationFilter() throws Exception {
		return new JwtAuthorizationFilter(authenticationManager(), userService, principalDetailsService);
	}

	// cors
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		//		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
