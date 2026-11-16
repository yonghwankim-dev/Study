package com.nemo.unit_test.ch06.ex08;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileBasedAuditFileSystem implements AuditFileSystem {

	@Override
	public Set<String> getFiles(String directoryName) {
		if(!Files.exists(Paths.get(directoryName))){
			return Collections.emptySet();
		}
		File dir = new File(directoryName);
		File[] files = dir.listFiles();
		if(files == null){
			return Collections.emptySet();
		}
		return Stream.of(files)
			.filter(file->!file.isDirectory())
			.map(File::getName)
			.collect(Collectors.toSet());
	}

	@Override
	public void writeAllText(Path filePath, String content) {
		try {
			Files.writeString(filePath,
				content + System.lineSeparator(),
				StandardOpenOption.CREATE,
				StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException("Failed to create and write initial audit file", e);
		}
	}

	@Override
	public List<String> readAllLines(Path filePath) {
		try {
			return new ArrayList<>(Files.readAllLines(filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
