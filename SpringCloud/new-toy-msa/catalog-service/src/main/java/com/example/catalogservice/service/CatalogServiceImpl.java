package com.example.catalogservice.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class CatalogServiceImpl implements CatalogService {

	private final CatalogRepository catalogRepository;
	private final Environment env;

	@Override
	public Iterable<CatalogEntity> getAllCatalogs() {
		return catalogRepository.findAll();
	}
}
