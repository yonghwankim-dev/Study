package PROM.PROM_1835;


import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DFSTestTest {

	@Test
	public void test() {
		int person = 8;
		boolean[] chosen = new boolean[person+1];
		int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		String[] data = {"N~F=0", "R~T>2"};
		
        int answer = Solution.solution(person, data);
		Assertions.assertThat(answer).isEqualTo(3648);
	}
	
	@Test
	public void test2() {
		int person = 8;
		boolean[] chosen = new boolean[person+1];
		int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		String[] data = {"M~C<2", "C~M>1"};
		
        int answer = Solution.solution(person, data);

		Assertions.assertThat(answer).isEqualTo(0);
	}

}
