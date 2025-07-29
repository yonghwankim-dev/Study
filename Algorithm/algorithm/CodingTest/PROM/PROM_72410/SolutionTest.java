package PROM.PROM_72410;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    
    @Test
    public void test() throws Exception{
        //given
        String[] new_ids = {"...!@BaT#*..y.abcdefghijklm",
                            "z-+.^.",
                            "=.=",
                            "123_.def",
                            "abcdefghijklmn.p"};
        String[] expecteds = {"bat.y.abcdefghi",
                              "z--",
                              "aaa",
                              "123_.def",
                              "abcdefghijklmn"};
        String[] actuals = new String[new_ids.length];


        //when
        for(int i = 0; i < new_ids.length; i++){
            actuals[i] = Solution.solution(new_ids[i]);
        }

        //then
        for(int i = 0; i < new_ids.length; i++){
            assertThat(actuals[i]).isEqualTo(expecteds[i]);
        }
    }
}
