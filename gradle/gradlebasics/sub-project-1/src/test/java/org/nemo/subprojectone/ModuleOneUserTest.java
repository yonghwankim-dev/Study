package org.nemo.subprojectone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ModuleOneUserTest {

	@Test
	void createEntity() {
		ModuleOneUser user = new ModuleOneUser("user1");
		assertNotNull(user);
	}
}
