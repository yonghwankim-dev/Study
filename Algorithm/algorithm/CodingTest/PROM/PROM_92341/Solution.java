package PROM.PROM_92341;

import java.util.*;

class Solution {
    static class Parking{
        int defaultTime;
        int defaultFee;
        int unitTime;
        int unitFee;
        Map<Car, Integer> carReduceParkingMinuteMap; // 차량별 주차누적시간(분)
        Map<Integer, Car> carMap;

        public Parking(int[] fees) {
            this.defaultTime = fees[0];
            this.defaultFee = fees[1];
            this.unitTime = fees[2];
            this.unitFee = fees[3];
            this.carReduceParkingMinuteMap = new HashMap<>();
            this.carMap = new HashMap<>();
        }

        public Car processRecord(String record){
            String[] recordArray = record.split(" ");
            Car car = Car.car(recordArray);

            if(car.isParkIn()){
                inCar(car);
            }else if(car.isParkOut()){
                outCar(car);
            }
            
            return car;
        }

        protected int toMinute(String timeText){
            String[] timeArray = timeText.split(":");
            Time time = new Time(Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]));
            return time.getMinuteTime();
        }

        protected void inCar(Car car){
            int minute = carReduceParkingMinuteMap.containsKey(car) ? getCarMinutes(car) : 0;
            carReduceParkingMinuteMap.put(car, minute);
            carMap.put(car.carNumber, car);
        }

        protected int outCar(Car car){
            Car parkedCar = carMap.get(car.carNumber);
            int parkedMinute = getParkedMinute(parkedCar.recentTime, car.recentTime);

            parkedCar.inOrOut = INOUT.OUT;
            carReduceParkingMinuteMap.put(car, getCarMinutes(car) + parkedMinute);
            return getCarMinutes(car);
        }

        protected int getParkedMinute(String inMinuteTime, String outMinuteTime){
            return toMinute(outMinuteTime) - toMinute(inMinuteTime);
        }

        protected int getCarMinutes(Car car){
            return carReduceParkingMinuteMap.getOrDefault(car, 0);
        }

        protected int calcFee(Car car) {
            int parkedMinute = getCarMinutes(car);
            int overMinuteTime = parkedMinute <= defaultTime ? 0 : parkedMinute - defaultTime;
            return (int) (defaultFee + Math.ceil((double) overMinuteTime / unitTime) * unitFee);
        }

        protected List<Car> processPastMidnight() {
            List<Car> inCars = new ArrayList<>();
            for(int carNumber : carMap.keySet()){
                if(carMap.get(carNumber).isParkIn()){
                    outCar(new Car(carNumber, "23:59"));
                    inCars.add(carMap.get(carNumber));
                }
            }
            return inCars;
        }
    }
    enum INOUT{IN, OUT}

    static class Car implements Comparable<Car>{
        int carNumber;
        String recentTime;
        INOUT inOrOut;
        public Car(int carNumber) {
            this(carNumber, "");
        }

        public Car(int carNumber, String recentTime){
            this(carNumber, recentTime, "IN");
        }

        public Car(int carNumber, String recentTime, String inOrOut) {
            this.carNumber = carNumber;
            this.recentTime = recentTime;
            this.inOrOut = INOUT.valueOf(inOrOut);
        }

        public static Car car(String[] recordArray){
            return new Car(Integer.parseInt(recordArray[1]), recordArray[0], recordArray[2]);
        }

        public boolean isParkIn(){
            return inOrOut == INOUT.IN;
        }

        public boolean isParkOut(){
            return inOrOut == INOUT.OUT;
        }

        @Override
        public int compareTo(Car car) {
            return this.carNumber - car.carNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Car)) return false;
            Car car = (Car) o;
            return carNumber == car.carNumber;
        }

        @Override
        public int hashCode() {
            return Objects.hash(carNumber);
        }
    }

    static class Time{
        private int hour;
        private int minute;

        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        public int getMinuteTime(){
            return hour * 60 + minute;
        }
    }

    public static int[] solution(int[] fees, String[] records) {
        List<Integer> answer = new ArrayList<>();
        Parking parking = new Parking(fees);
        List<Car> cars = new ArrayList<>();
        for(String record : records){
            Car car = parking.processRecord(record);
            if(!cars.contains(car)){
                cars.add(car);
            }
        }
        parking.processPastMidnight();

        Collections.sort(cars);
        for(Car car : cars){
            answer.add(parking.calcFee(car));
        }
        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
}
