package com.example.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.userservice.service.UserService;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

	public static final String ALLOWED_IP_ADDRESS = "127.0.0.1";
	public static final String SUBNET = "/32";
	public static final IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);

	private final UserService userService;
	private final Environment environment;
	private final PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
			AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth-> auth
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/**").access(
					new WebExpressionAuthorizationManager("hasIpAddress('127.0.0.1') or hasIpAddress('::1') or hasIpAddress('172.30.1.4')")
				)
				.anyRequest().authenticated()
			)
			.authenticationManager(authenticationManager)
			.addFilter(getAuthenticationFilter(authenticationManager))
			.httpBasic(Customizer.withDefaults())
			.headers(headers -> headers
				.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		return http.build();
	}

	private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, authenticationManager);
		authenticationFilter.setAuthenticationManager(authenticationManager);
		return authenticationFilter;
	}

}
