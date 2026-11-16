package com.nemo.unit_test.ch06.ex08;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

class AuditManagerTest {
	@DisplayName("현재 파일의 최대방문자 기록에 달하면 새로운 파일이 생성되어야 한다.")
	@Test
	void a_new_file_is_created_when_the_current_file_overflows(){
	    // given
		AuditFileSystem fileSystemMock = BDDMockito.mock(AuditFileSystem.class);
		BDDMockito.given(fileSystemMock.getFiles("audits"))
			.willReturn(Set.of(Paths.get("audits","audit_1.txt").toString(), Paths.get("audits","audit_2.txt").toString()));
		BDDMockito.given(fileSystemMock.readAllLines(Paths.get("audits","audit_2.txt")))
			.willReturn(List.of(
				"Perter; 2019-04-06T16:30:00",
				"Jane; 2019-04-06T16:31:00",
				"Jack; 2019-04-06T16:32:00"
			));
		AuditManager sut = new AuditManager(3, "audits", fileSystemMock);
	    // when
		sut.addRecord("Alice", LocalDateTime.of(2019, 4, 6, 18, 0, 0));
	    // then
		BDDMockito.verify(fileSystemMock)
			.writeAllText(Paths.get("audits","audit_3.txt"), "Alice; 2019-04-06T18:00:00");
	}
}
