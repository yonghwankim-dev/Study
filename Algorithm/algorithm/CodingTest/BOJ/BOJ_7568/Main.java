package BOJ.BOJ_7568;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static class Person{
		int rank=1;
		int weight;
		int height;
		
		public Person(int weight, int height) {
			this.weight = weight;
			this.height = height;
		}

		@Override
		public String toString() {
			return "Person [rank=" + rank + ", weight=" + weight + ", height=" + height + "]";
		}
		
		public static void compare(Person p1, Person p2)
		{
			if(p1.weight > p2.weight &&
					p1.height > p2.height)
			{
				p2.rank++;
			}
			else if(p1.weight < p2.weight &&
						p1.height < p2.height)
			{
				p1.rank++;
			}
		}
	}
	
	public static String solution(Person[] persons)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<persons.length;i++)
		{
			for(int j=i+1;j<persons.length;j++)
			{
				Person.compare(persons[i], persons[j]);
			}
		}
		
		for(Person p : persons)
		{
			sb.append(p.rank+" ");
		}
		return sb.toString().trim();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		Person[] persons = new Person[n];
		
		for(int i=0; i<n;i++)
		{
			String[] str = br.readLine().split(" ");
			int weight = Integer.parseInt(str[0]);
			int height = Integer.parseInt(str[1]);
			
			persons[i] = new Person(weight, height);
		}
	
		System.out.println(solution(persons));		
	}
}
