package com.nemo.unit_test.ch06.ex08;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class AuditManager {
	private final int maxEntriesPerFile;
	private final String directoryName;
	private final AuditFileSystem auditFileSystem;

	public AuditManager(int maxEntriesPerFile, String directoryName, AuditFileSystem auditFileSystem) {
		this.maxEntriesPerFile = maxEntriesPerFile;
		this.directoryName = directoryName;
		this.auditFileSystem = auditFileSystem;
	}

	public void addRecord(String visitorName, LocalDateTime timeOfVisit){
		Set<String> filePaths = auditFileSystem.getFiles(directoryName);
		List<AuditFile> sortedAuditFiles = sortByIndex(filePaths);

		String newRecord = visitorName + "; " + timeOfVisit.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		Path path = Paths.get(directoryName);
		if(sortedAuditFiles.isEmpty()){
			// 디렉토리가 존재하지 않은 경우 디렉토리 생성
			try{
				if (!Files.exists(path)){
					Files.createDirectories(path);
				}

				// 2. newFile 경로 생성
				Path newFilePath = path.resolve("audit_1.txt");

				// 3. newFile에 newRecord 작성
				auditFileSystem.writeAllText(newFilePath, newRecord);
			} catch (Exception e) {
				throw new RuntimeException("Failed to create and write initial audit file", e);
			}
			return;
		}

		AuditFile lastAuditFile = sortedAuditFiles.getLast();
		List<String> lines;
		lines = auditFileSystem.readAllLines(lastAuditFile.path);

		if (lines.size() < maxEntriesPerFile){
			lines.add(newRecord);
			String newContent = String.join(System.lineSeparator(), lines);
			auditFileSystem.writeAllText(lastAuditFile.path, newContent);
		}else{
			int newIndex = lastAuditFile.index + 1;
			String newName = String.format("audit_%d.txt", newIndex);
			Path newFile = path.resolve(newName);
			auditFileSystem.writeAllText(newFile, newRecord);
		}
	}

	private List<AuditFile> sortByIndex(Set<String> filePaths) {
		return filePaths.stream()
			.map(path ->{
				int index = extractIndex(path);
				return new AuditFile(index, Paths.get(path));
			})
			.sorted(Comparator.comparingInt(a -> a.index))
			.toList();
	}

	private int extractIndex(String fileName) {
		String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
		String indexStr = nameWithoutExtension.substring(nameWithoutExtension.lastIndexOf("_") + 1);
		return Integer.parseInt(indexStr);
	}

	private record AuditFile(int index, Path path) {
	}
}
