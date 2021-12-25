package in.sevasuyog;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SolutionAbstract {
	static boolean debug = true;
	
	static long mod = 1000000007;

	static long multiply(long a, long b) {
		long result = 0;
		while(b > 0) {
			if((b&1) == 1) {
				result = (result+a)%mod;
			}
			a = (2*a)%mod;
			b >>= 1;
		}
		return result;
	}
	
	static int[][] combinations = new int[1001][1001];
	
	static void initCombinations() {
		int temp = combinations.length - 1;
		for(int i = 0; i <= temp; i++) {
			for(int j = 0; j <= i; j++) {
				combinations[i][j] = nCr(i, j);
			}
		}
	}

	static int nCr(int n, int r) {
		if(r > n) return 0;
		if(r == 0) {
			combinations[n][r] = 1;
			return 1;
		}
		if(r == 1) {
			combinations[n][r] = n;
			return n;
		}
		if(combinations[n][r] != 0) return combinations[n][r];
		
		int result = (int) ((nCr(n - 1, r - 1) + nCr(n - 1, r))%mod);
		combinations[n][r] = result;
		combinations[n][n - r] = result;
		return result;
	}
	
	static <T> void printArr(T[] arr, int front, int rear) {
		if(debug) {
			System.out.println(Arrays.asList(copyArray(arr, front, rear)).toString());
		}
	}
	
	static <T> void printArr(T[] arr) {
		if(debug) {
			System.out.println(Arrays.asList(arr).toString());
		}
	}
	
	static void println(int[] data) {
		System.out.println(Arrays.stream(data).boxed().collect(Collectors.toList()).toString());
	}

	static void printNode(Node root) {
		if(debug) System.out.println(inOrderTraversal(root).toString());
	}
	
	public static StringBuffer preOrderTraversal(Node root) {
		StringBuffer str = new StringBuffer("");
		
		if(root == null) return str.append("N ");
		str.append(root.data + " ");
		str.append(preOrderTraversal(root.left));
		str.append(preOrderTraversal(root.right));
		return str;
	}
	
	public static StringBuffer inOrderTraversal(Node root) {
		StringBuffer str = new StringBuffer("");
		
		if(root == null) return str.append("");
		
		str.append(inOrderTraversal(root.left));
		str.append(root.data + " ");
		str.append(inOrderTraversal(root.right));
		return str;
	}
	
	static void print(Object s) {
		if(debug) {
			System.out.print(s);
		}
	}
	
	static void println(Object s) {
		if(debug) {
			System.out.println(s);
		}
	}
	
	static <T> T[] copyArray(T[] arr, int front, int rear) {
		return Arrays.copyOfRange(arr, rear, front + 1);
	}
	
	static <T> void insert(List<T> list, T element, Comparator<T> comparator) {
	    int index = Collections.binarySearch(list, element, comparator);
	    if (index < 0) {
	        index = -index - 1;
	    }
	    list.add(index, element);
	}
	
	static Comparator<String> strComparator = new Comparator<String>() {
		@Override public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	};
	
	static boolean init = false;
	
	static int[] bellNumbers = new int[1001];
	
	static int bellNumber(int N) {
		if(!init) {
		    initCombinations();
		    init = true;
		}
		return myBellNumber(N);
    }

	static private int myBellNumber(int n) {
		if(n == 0) return 1;
		
		if(bellNumbers[n] != 0) return bellNumbers[n];
		
		int result = 0;
		for(int i = 0; i < n; i++) {
			result = (int) ((result + multiply(combinations[n - 1][i], myBellNumber(i)))%mod);
		}
		bellNumbers[n] = result;
		return result;
	}
}
