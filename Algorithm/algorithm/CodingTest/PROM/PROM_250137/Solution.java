package PROM.PROM_250137;

public class Solution {
	public int solution(int[] bandage, int health, int[][] attacks) {
		int answer = 0;
		int n = attacks.length;
		int time = attacks[n - 1][0];

		int currentHealth = health;
		int bandageTime = 0;

		int monsterTurn = 0;
		final int monsterTimeIndex = 0;
		final int monsterAttackIndex = 1;
		for (int i = 1; i <= time; i++){
			if (i == attacks[monsterTurn][monsterTimeIndex]){
				health -= attacks[monsterTurn][monsterAttackIndex];
			}else{
				if (currentHealth < health){
					currentHealth += bandage[1];
				}
				bandageTime++;

			}


			if (health <= 0){
				return -1;
			}
		}
		return answer;
	}
}
