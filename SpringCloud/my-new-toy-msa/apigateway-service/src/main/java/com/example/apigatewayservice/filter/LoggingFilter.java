package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

	public LoggingFilter() {
		super(Config.class);
	}

	// @Override
	// public GatewayFilter apply(Config config) {
	// 	return (exchange, chain) -> {
	// 		ServerHttpRequest request = exchange.getRequest();
	// 		ServerHttpResponse response = exchange.getResponse();
	//
	// 		// custom pre filter
	// 		log.info("Logging Filter BaseMessage: {}, {}", config.getBaseMessage(), request.getRemoteAddress());
	//
	// 		if (config.isPreLogger()){
	// 			log.info("Logging Filter Start: request uri -> {}", request.getURI());
	// 		}
	//
	// 		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
	// 			if (config.isPostLogger()){
	// 				log.info("Logging Filter End: response code -> {}", response.getStatusCode());
	// 			}
	// 		}));
	// 	};
	// }

	// 우선순위를 갖는 LoggingFilter 적용
	@Override
	public GatewayFilter apply(Config config) {
		return new OrderedGatewayFilter((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			// custom pre filter
			log.info("Logging Filter BaseMessage: {}, {}", config.getBaseMessage(), request.getRemoteAddress());

			if (config.isPreLogger()) {
				log.info("Logging Filter Start: request uri -> {}", request.getURI());
			}

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				if (config.isPostLogger()) {
					log.info("Logging Filter End: response code -> {}", response.getStatusCode());
				}
			}));
		}, Ordered.HIGHEST_PRECEDENCE);
	}

	@Data
	public static class Config{
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}
