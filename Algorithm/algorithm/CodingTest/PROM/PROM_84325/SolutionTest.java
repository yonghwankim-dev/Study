package PROM.PROM_84325;


import org.junit.Test;

class SolutionTest {
	
	@Test
	void test1() {
		String[] table = {"SI JAVA JAVASCRIPT SQL PYTHON C#", "CONTENTS JAVASCRIPT JAVA PYTHON SQL C++", "HARDWARE C C++ PYTHON JAVA JAVASCRIPT", "PORTAL JAVA JAVASCRIPT PYTHON KOTLIN PHP", "GAME C++ C# JAVASCRIPT C JAVA"};
		
		String[] languages = {"PYTHON", "C++", "SQL"};
		int[] preference = {7,5,5};
		Solution sol = new Solution();
		
		System.out.println(sol.solution(table, languages, preference));
	}
	
	@Test
	void test2() {
		String[] table = {"SI JAVA JAVASCRIPT SQL PYTHON C#", "CONTENTS JAVASCRIPT JAVA PYTHON SQL C++", "HARDWARE C C++ PYTHON JAVA JAVASCRIPT", "PORTAL JAVA JAVASCRIPT PYTHON KOTLIN PHP", "GAME C++ C# JAVASCRIPT C JAVA"};
		
		String[] languages = {"JAVA", "JAVASCRIPT"};
		int[] preference = {7,5};
		Solution sol = new Solution();
		
		System.out.println(sol.solution(table, languages, preference));
	}

}
