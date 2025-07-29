package PROM.PROM_92341;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    int[] fees;
    String[] records;
    @Before
    public void setUp(){
        fees = new int[]{180, 5000, 10, 600};
        records = new String[]{"05:34 5961 IN",
                "06:00 0000 IN",
                "06:34 0000 OUT",
                "07:59 5961 OUT",
                "07:59 0148 IN",
                "18:59 0000 IN",
                "19:09 0148 OUT",
                "22:59 5961 IN",
                "23:00 5961 OUT"};
    }

    @Test
    public void testSolution() throws Exception{
        //given

        //when
        int[] actual = Solution.solution(fees, records);
        //then
        assertThat(actual).isEqualTo(new int[]{14600, 34400, 5000});
    }
    
    @Test
    public void testSolution2(){
        //given
        fees = new int[]{120, 0, 60, 591};
        records = new String[]{"16:00 3961 IN",
                                "16:00 0202 IN",
                                "18:00 3961 OUT",
                                "18:00 0202 OUT",
                                "23:58 3961 IN"};
        //when
        int[] actual = Solution.solution(fees, records);
        //then
        assertThat(actual).isEqualTo(new int[]{0, 591});
    }
    
    @Test
    public void testProcessRecord_whenCarIsIn(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        //when
        Solution.Car car = parking.processRecord(records[0]);
        //then
        assertThat(car).isEqualTo(new Solution.Car(5961));
    }

    @Test
    public void testProcessRecord_whenCarIsOut(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        //when
        parking.processRecord(records[0]);
        Solution.Car car = parking.processRecord(records[3]);

        //then
        assertThat(parking.carReduceParkingMinuteMap.get(car)).isEqualTo(145);
    }
    
    @Test
    public void testGetCarMinute_whenCarIsNoOut(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        parking.processRecord(records[0]);
        //when
        int actual = parking.getCarMinutes(new Solution.Car(5961));
        //then
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void testGetCarMinute_whenNoCarIn(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        //when
        int actual = parking.getCarMinutes(new Solution.Car(5961));
        //then
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void testOutCar(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        parking.processRecord(records[0]);

        //when
        String minute = "07:59";
        int actual = parking.outCar(new Solution.Car(5961, minute));
        //then
        assertThat(actual).isEqualTo(145);
    }

    @Test
    public void testCalcFee(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        //when
        parking.processRecord(records[0]);
        Solution.Car car = parking.processRecord(records[3]);
        int actual = parking.calcFee(car);
        //then
        assertThat(actual).isEqualTo(5000);
    }

    @Test
    public void testCalcFee_overTime(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        Solution.Car car;
        //when
        parking.processRecord(records[1]);
        parking.processRecord(records[2]);
        car = parking.processRecord(records[5]);
        parking.processPastMidnight();
        int actual = parking.calcFee(car);
        //then
        assertThat(actual).isEqualTo(14600);
    }
    
    @Test
    public void testCalcFee_CarNumberIs5961(){
        //given
        Solution.Parking parking = new Solution.Parking(fees);
        Solution.Car car;
        //when
        parking.processRecord(records[0]);
        parking.processRecord(records[3]);
        parking.processRecord(records[7]);
        car = parking.processRecord(records[8]);
        parking.processPastMidnight();
        int actual = parking.calcFee(car);
        //then
        assertThat(actual).isEqualTo(5000);
    }

    @Test
    public void testGetMinuteTime(){
        //given
        Solution.Time time = new Solution.Time(5, 39);
        //when
        int actual = time.getMinuteTime();
        //then
        assertThat(actual).isEqualTo(339);
    }

}
