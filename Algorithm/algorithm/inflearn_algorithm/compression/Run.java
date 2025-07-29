package compression;

public class Run implements Comparable<Run>{
	public byte symbol;
	public int runLen;
	public int freq;
	public Run left, right;
	
	public int codeword;		// �ο��� codeword�� 32bit�� ����
	public int codewordLen;		// �ο��� codeword�� ����. 
								// �� codeword�� ���� cordwordLen��Ʈ�� ���� cordword
	
	public Run(byte symbol, int runLen, int freq) {
		this.symbol = symbol;
		this.runLen = runLen;
		this.freq = freq;
		this.left = null;
		this.right = null;
		this.codeword = 0;
		this.codewordLen = 0;
	}

	public Run() {
		
	}

	public boolean equals(byte symbol, int runLen) {
		return this.symbol == symbol && this.runLen == runLen;
	}
	
	@Override
	public int compareTo(Run r) {
		return this.freq - r.freq;
	}

	@Override
	public String toString() {
		return "Run [symbol=" + symbol + ", runLen=" + runLen + ", freq=" + freq + ", left=" + left + ", right=" + right
				+ ", codeword=" + codeword + ", codewordLen=" + codewordLen + "]";
	}

		
}
