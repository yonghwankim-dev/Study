package compression;

public class Heap<E> {
	private E[] harr;
	private int capacity;	// ���� �ִ� ũ��
	private int heapSize;	// ���� ���� ũ��
	
	public Heap()
	{
		this(10);
	}
	
	public Heap(int capacity)
	{
		harr = (E[]) new Object[capacity];
		this.capacity = capacity;
		heapSize = 0;
	}

	// ���� ���Ҹ� �߰�
	public void add(E obj)
	{
		// ���� �����ִ��� �˻�
		if(heapSize==capacity)
		{
			System.out.println("Overflow : Could not add\n");
			return;
		}
		
		// heap�� ������ �ڸ��� ���� �߰�
		heapSize++;
		int i = heapSize - 1;
		harr[i] = obj;

		// ���� �ö󰡸鼭 �� ����
		minTrickleUp(i);
	}

	// ��Ʈ ����� ���� �����մϴ�. ����� ��Ʈ ���� ���ŵ˴ϴ�.
	public E remove()
	{
		if(heapSize<=0)
		{
			return null;
		}
		
		if(heapSize==1)
		{
			heapSize--;
			return harr[0];
		}
		
		// �����κ��� ��Ʈ ����� ���� �����ϰ� �ּҰ����� ��ü
		E root = harr[0];
		harr[0] = harr[--heapSize];
		minTrickleDown(0);	// ��Ʈ ��� ������
		
		return root;
	}
	
	// i��° ����� ���� new_val ������ �����մϴ�.
	public void set(int i, E newObj)
	{
		// i��° ����� �� ����
		harr[i] = newObj;
		
		// ���� �ö󰡸鼭 �� ����
		minTrickleUp(i);
	}

	// �ּҰ� ��ȯ, ��Ʈ ����� ���� �������� �ʰ� ���� ��ȯ�մϴ�.
	public E getMin()
	{
		return harr[0];
	}
	
	// ���� ����� ����� ������ ��ȯ
	public int size()
	{
		return heapSize; 
	}
	
	// �� ���
	public void printHeap()
	{
		for(int i=0;i<heapSize;i++)
		{
			System.out.println(harr[i]);
			System.out.print(" ");
		}
		System.out.println();
	}
	
	// �� ����
	public void sort()
	{
		// �ִ����� �ǵ��� ����
		for(int i=heapSize/2-1;i>=0;i--)
		{
			maxTrickleDown(heapSize, i);
		}
		
		// ���� ������ ���Һ��� �����Ͽ� �� ���� ����
		for(int i=heapSize-1;i>0;i--) {
				
			// ���� ������ ���ҿ� ��Ʈ ���� ��ȯ
			// 0��°�� �ִ밪�� ��ġ��, i��°�� �ּҰ��� ��ġ��
			swap(0,i);
			
			// ��Ʈ���� �ִ����� �ǵ��� ����
			maxTrickleDown(i,0);
		}
	}
	
	// �ּ� ���� �ǵ��� ����
	// ���� �ö󰡸鼭 �ּ� �� ����
	private void minTrickleUp(int i)
	{
		if(i==0)
		{
			return;
		}
		
		int parent = getParent(i);
		if(((Comparable<E>)harr[i]).compareTo(harr[parent])<0)
		{
			swap(parent, i);
			minTrickleUp(parent);
		}
	}
	
	// i��°���� �Ʒ��� �������鼭 �ּ� �� ����
	private void minTrickleDown(int i)
	{
		int left = getLeftChild(i);
		int right = getRightChild(i);
		int smallest = i;
		
		if(left < heapSize && ((Comparable<E>)harr[left]).compareTo(harr[smallest])<0)
		{
			smallest = left;
		}
		
		if(right < heapSize && ((Comparable<E>)harr[right]).compareTo(harr[smallest])<0)
		{
			smallest = right;
		}
		
		if(smallest != i)
		{
			swap(smallest,i);
			minTrickleDown(smallest);
		}		
	}
	
	// 0~lastPosition ���������� i��° ������ �ִ� �� ����
	private void maxTrickleDown(int lastPosition, int i)
	{
		int left = getLeftChild(i);
		int right = getRightChild(i);
		int largest = i;
		
		if(left < lastPosition && ((Comparable<E>)harr[left]).compareTo(harr[largest])>0)
		{
			largest = left;
		}
		
		if(right < lastPosition && ((Comparable<E>)harr[right]).compareTo(harr[largest])>0)
		{
			largest = right;
		}
		
		if(largest != i)
		{
			swap(largest,i);
			maxTrickleDown(lastPosition, largest);
		}		
	}
	
	// parent��°�� i��°�� ��ȯ
	// parent��° ���ҿ� i��° ���� ��ȯ
	private void swap(int parent, int i)
	{
		E temp = harr[parent];
		harr[parent] = harr[i];
		harr[i] = temp;
	}	
	
	// i��° �θ��� �ε��� ��ȯ
	// �־��� i��° ����� �θ� ��� ��ȯ
	private int getParent(int i)
	{
		return (i-1)/2;
	}
	
	// i��°�� ���� �ڽ� �ε��� ��ȯ
	// �־��� i��° ����� ���� �ڽ� ��ȯ
	private int getLeftChild(int i)
	{
		return (2*i+1);
	}
	
	// i��°�� ������ �ڽ� �ε��� ��ȯ
	// �־��� i��° ����� ������ �ڽ� ��ȯ
	private int getRightChild(int i)
	{
		return (2*i+2);
	}
	
	
	
}
