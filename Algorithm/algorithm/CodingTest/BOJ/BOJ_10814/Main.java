package BOJ.BOJ_10814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	static class Member
	{
		int age;
		String name;
		
		public Member(int age, String name) {
			this.age = age;
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return getAge() + " " + getName();
		}		
		
		
	}
	
	public static void solution(List<Member> list)
	{
		List<Member> answer = list.stream()
			.sorted((a,b)->a.getAge()-b.getAge())
			.collect(Collectors.toList());
		
		for(Member m : answer)
		{
			System.out.println(m);
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Member> list = new ArrayList<>();
		
		int n = Integer.parseInt(br.readLine());
		for(int i=0;i<n;i++)
		{
			String[] info = br.readLine().split(" ");
			list.add(new Member(Integer.parseInt(info[0]), info[1]));
		}
		
		solution(list);

	}

}
