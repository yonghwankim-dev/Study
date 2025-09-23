package com.myshop.springconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import com.myshop.member.domain.MemberRepository;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize ->
			authorize.requestMatchers(HttpMethod.POST, "/member/join").permitAll()
				.requestMatchers("/member/login").permitAll()
				.requestMatchers("/", "/error").permitAll()
				.anyRequest().authenticated()
		);
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		http.formLogin(configurer -> {
			configurer.loginProcessingUrl("/member/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll();
		});
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(AbstractHttpConfigurer::disable);
		return http.build();
	}

	@Bean
	public UserDetailsService memberDetailsService(MemberRepository memberRepository) {
		return new MemberDetailService(memberRepository);
	}
}
