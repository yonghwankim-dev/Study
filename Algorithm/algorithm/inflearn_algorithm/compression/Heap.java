package compression;

public class Heap<E> {
	private E[] harr;
	private int capacity;	// 힙의 최대 크기
	private int heapSize;	// 힙의 현재 크기
	
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

	// 힙에 원소를 추가
	public void add(E obj)
	{
		// 힙이 꽉차있는지 검사
		if(heapSize==capacity)
		{
			System.out.println("Overflow : Could not add\n");
			return;
		}
		
		// heap의 마지막 자리에 값을 추가
		heapSize++;
		int i = heapSize - 1;
		harr[i] = obj;

		// 위로 올라가면서 힙 조정
		minTrickleUp(i);
	}

	// 루트 노드의 값을 추출합니다. 추출된 루트 노드는 제거됩니다.
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
		
		// 힙으로부터 루트 노드의 값을 제거하고 최소값으로 대체
		E root = harr[0];
		harr[0] = harr[--heapSize];
		minTrickleDown(0);	// 루트 노드 힙조정
		
		return root;
	}
	
	// i번째 노드의 값을 new_val 값으로 변경합니다.
	public void set(int i, E newObj)
	{
		// i번째 노드의 값 변경
		harr[i] = newObj;
		
		// 위로 올라가면서 힙 조정
		minTrickleUp(i);
	}

	// 최소값 반환, 루트 노드의 값을 제거하지 않고 값만 반환합니다.
	public E getMin()
	{
		return harr[0];
	}
	
	// 힙에 저장된 노드의 개수를 반환
	public int size()
	{
		return heapSize; 
	}
	
	// 힙 출력
	public void printHeap()
	{
		for(int i=0;i<heapSize;i++)
		{
			System.out.println(harr[i]);
			System.out.print(" ");
		}
		System.out.println();
	}
	
	// 힙 정렬
	public void sort()
	{
		// 최대힙이 되도록 조정
		for(int i=heapSize/2-1;i>=0;i--)
		{
			maxTrickleDown(heapSize, i);
		}
		
		// 힙의 마지막 원소부터 시작하여 힙 정렬 수행
		for(int i=heapSize-1;i>0;i--) {
				
			// 힙의 마지막 원소와 루트 값을 교환
			// 0번째는 최대값이 위치함, i번째는 최소값이 위치함
			swap(0,i);
			
			// 루트에서 최대힙이 되도록 조정
			maxTrickleDown(i,0);
		}
	}
	
	// 최소 힙이 되도록 조정
	// 위로 올라가면서 최소 힙 조정
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
	
	// i번째에서 아래로 내려가면서 최소 힙 조정
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
	
	// 0~lastPosition 범위내에서 i번째 원소의 최대 힙 조정
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
	
	// parent번째와 i번째를 교환
	// parent번째 원소와 i번째 원소 교환
	private void swap(int parent, int i)
	{
		E temp = harr[parent];
		harr[parent] = harr[i];
		harr[i] = temp;
	}	
	
	// i번째 부모의 인덱스 반환
	// 주어진 i번째 노드의 부모 노드 반환
	private int getParent(int i)
	{
		return (i-1)/2;
	}
	
	// i번째의 왼쪽 자식 인덱스 반환
	// 주어진 i번째 노드의 왼쪽 자식 반환
	private int getLeftChild(int i)
	{
		return (2*i+1);
	}
	
	// i번째의 오른쪽 자식 인덱스 반환
	// 주어진 i번째 노드의 오른쪽 자식 반환
	private int getRightChild(int i)
	{
		return (2*i+2);
	}
	
	
	
}
