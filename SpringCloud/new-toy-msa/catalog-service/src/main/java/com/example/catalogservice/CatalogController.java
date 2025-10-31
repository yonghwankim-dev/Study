package com.example.catalogservice;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
	private final Environment env;
	private final CatalogService catalogService;

	public CatalogController(Environment env, CatalogService catalogService) {
		this.env = env;
		this.catalogService = catalogService;
	}

	@GetMapping("/health-check")
	public String status(){
		return String.format("It's Working in Catalog Service on LOCAL PORT %s (SERVER PORT %s)", env.getProperty("local.server.port"), env.getProperty("server.port"));
	}

	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
		Iterable<CatalogEntity> catalogs = catalogService.getAllCatalogs();
		List<ResponseCatalog> result = new ArrayList<>();
		catalogs.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseCatalog.class));
		});
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
