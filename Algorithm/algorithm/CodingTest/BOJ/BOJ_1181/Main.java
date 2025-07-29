package BOJ.BOJ_1181;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Main {
	
	public static class Lang{
		private String word;
		private int len;
		
		public Lang()
		{
			
		}
		
		public Lang(String word, int len) {
			this.word = word;
			this.len = len;
		}
		
		public String getWord() {
			return word;
		}
		public void setWord(String word) {
			this.word = word;
		}
		public int getLen() {
			return len;
		}
		public void setLen(int len) {
			this.len = len;
		}
		@Override
		public String toString() {
			return "lang [word=" + word + ", len=" + len + "]";
		}	
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		List<Lang> list = new ArrayList<>();
		List<String> answer = new ArrayList<String>();
		
		// �ܾ� �Է�
		for(int i=0;i<tc;i++)
		{
			String word = br.readLine();
			
			list.add(new Lang(word,word.length()));
		}
		
		// �ܾ� ���� �������� -> �ܾ� �������� ����
		list.sort(Comparator.comparing(Lang::getLen).thenComparing(Lang::getWord));
		
		// ��� ����Ʈ�� �ߺ��Ǵ� �ܾ� �����ϰ� ����
		for(Lang l : list)
		{
			String w = l.getWord();
			if(answer.contains(w))
			{
				continue;
			}
			else
			{
				answer.add(w);
			}
		}
		
		// ���
		for(String s : answer)
		{
			System.out.print(s + " ");
		}

		
		
	}
}
