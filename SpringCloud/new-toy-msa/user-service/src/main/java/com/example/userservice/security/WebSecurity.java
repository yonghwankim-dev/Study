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

import com.example.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

	private final UserService userService;
	private final Environment env;
	private final PasswordEncoder passwordEncoder;

	public static final String ALLOWED_IP_ADDRESS = "127.0.0.1";
	public static final String SUBNET = "/32";
	public static final IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService)
			.passwordEncoder(passwordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(auth->auth
			.requestMatchers("/h2-console/**").permitAll()
			.requestMatchers("/welcome").permitAll()
			.requestMatchers("/actuator/**").permitAll()
			.requestMatchers("/**").access(
				new WebExpressionAuthorizationManager(
					"hasIpAddress('127.0.0.1') or hasIpAddress('::1') or hasIpAddress('172.30.1.1')"
				)
			)
			.anyRequest().authenticated()
		);
		http.authenticationManager(authenticationManager);
		http.addFilter(getAuthenticationFilter(authenticationManager));
		http.httpBasic(Customizer.withDefaults());
		http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		return http.build();
	}

	private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
		return new AuthenticationFilter(userService, env, authenticationManager);
	}
}
