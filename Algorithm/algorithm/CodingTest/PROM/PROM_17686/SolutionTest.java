package PROM.PROM_17686;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

	@Test
	public void test() {
		//given
		String[] files = {"img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"};
		//when
		String[] actual = new Solution().solution(files);
		//then
		assertThat(actual).isEqualTo(new String[]{"img1.png", "IMG01.GIF", "img02.png", "img2.JPG", "img10.png", "img12.png"});
	}

	@Test
	public void test2() throws Exception{
	    //given
	    String[] files = {"F-5 Freedom Fighter", "B-50 Superfortress", "A-10 Thunderbolt II", "F-14 Tomcat"};
	    //when
	    String[] actual = new Solution().solution(files);
	    //then
		assertThat(actual).isEqualTo(new String[]{"A-10 Thunderbolt II", "B-50 Superfortress", "F-5 Freedom Fighter", "F-14 Tomcat"});
	}

	@Test
	public void test3() throws Exception{
	    //given
	    String[] files = {"img100.png", "img200.png", "img0001.0000png"};
	    //when
		String[] actual = new Solution().solution(files);
	    //then
		assertThat(actual).isEqualTo(new String[]{"img0001.0000png", "img100.png", "img200.png"});
	}

}
