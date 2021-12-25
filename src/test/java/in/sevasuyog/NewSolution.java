package in.sevasuyog;

import java.util.ArrayList;
import java.util.Collections;

class NewSolution {
    ArrayList<Integer> subsetSums(ArrayList<Integer> arr, int N) {
    	ArrayList<Integer> list = subsetSumsUnsorted(arr, N);
    	Collections.sort(list);
    	return list;
    }
    
    ArrayList<Integer> subsetSumsUnsorted(ArrayList<Integer> arr, int N){
        ArrayList<Integer> list = new ArrayList<Integer>();
    	if(N == 0) {
    		list.add(0);
    		return list;
    	}
        int item = arr.remove(0);
        ArrayList<Integer> subList = subsetSumsUnsorted(arr, N - 1);
        
        list.addAll(subList);
        
        for(Integer i : subList) {
        	list.add(i + item);
        }
        return list;
    }
}