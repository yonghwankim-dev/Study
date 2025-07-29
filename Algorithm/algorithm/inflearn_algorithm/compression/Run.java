package compression;

public class Run implements Comparable<Run>{
	public byte symbol;
	public int runLen;
	public int freq;
	public Run left, right;
	
	public int codeword;		// 부여된 codeword를 32bit로 저장
	public int codewordLen;		// 부여된 codeword의 길이. 
								// 즉 codeword의 하위 cordwordLen비트가 실제 cordword
	
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
