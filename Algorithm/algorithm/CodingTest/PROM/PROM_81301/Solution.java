package PROM.PROM_81301;

class Solution {
	
	enum numbers{
		zero(0),one(1),two(2),three(3),four(4),five(5),six(6),seven(7),eight(8),nine(9);

		private final int num;
		numbers(int num) {
			this.num = num;
		}
		public int getNum() {
			return num;
		}
	}
	
	public int solution(String s)
	{
		for(numbers n : numbers.values())
		{
			s = s.replaceAll(n.name(), n.getNum()+"");
		}
		
		return Integer.parseInt(s);
	}
}
