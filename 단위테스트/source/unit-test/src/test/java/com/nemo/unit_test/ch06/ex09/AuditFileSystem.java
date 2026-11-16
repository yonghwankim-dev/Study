package com.nemo.unit_test.ch06.ex08;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public interface AuditFileSystem {
	Set<String> getFiles(String directoryName);

	void writeAllText(Path filePath, String content);

	List<String> readAllLines(Path filePath);
}
