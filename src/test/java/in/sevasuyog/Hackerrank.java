package in.sevasuyog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Arrays;

public class Hackerrank {
	 static long nCr(long n, long r)
	    {
	    	if (n<r) return 0;
	        if (r == 0) return 1;
	        
	        return (fac[(int) n] * invFac[(int) r] % mod
	        	 * invFac[(int) (n - r)] % mod)
	         % mod;
	    }
	    
	    static long mod = 17;
	    static Map<Long, Map<Long, Long>> map = new HashMap<Long, Map<Long, Long>>();
	    static Integer[][] count = null;
	    static int[] countTemp = new int[26];
	    static Long[] fac;
	    static Long[] invFac;
	    public static void initialize(String s) {
	        count = new Integer[s.length() + 1][26];
	        for(int i = 0; i < 26; i++) {
	        	count[0][i] = 0;
	        }
	        for(int i = 1; i <= s.length(); i++) {
	        	System.arraycopy(count[i - 1], 0, count[i], 0, 26);
	        	count[i][s.charAt(i - 1) - 'a']++;
	        }
	        fac = new Long[(int)s.length() + 1];
	        fac[0] = 1l;
	        
	        for (int i = 1; i <= s.length(); i++)
	            fac[i] = fac[i - 1] * i % mod;
	        
	        System.out.println(Arrays.asList(fac));
	        
	        invFac = new Long[(int)s.length() + 1];

	        invFac[0] = invFac[1] = 1l;
	        for (int i = 2; i <= s.length(); i++)
	        	invFac[i] = invFac[(int) (mod % i)] *
	                (mod - mod / i) % mod;
	        
	        System.out.println(Arrays.asList(invFac));
	    }

    public static void main(String[] args) throws IOException {
        initialize("aaaaa");
    }
}
