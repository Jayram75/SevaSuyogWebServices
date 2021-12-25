package in.sevasuyog;

class Solution extends SolutionAbstract {
	public static void main(String[] args) throws Throwable {
		System.out.println(longestPalindrome("ababbab")); 
	}

	static Boolean[][] isPalindrome = null;
	
	static String longestPalindrome(String s) {
		int len = s.length();
		
		isPalindrome = new Boolean[len][len];
		
		for(int l = len; l > 0; l--) {
			for(int i = 0; i + l <= len; i++) {
				int j = i + l - 1;
				if(isPalindrome(s, i, j)) {
					return s.substring(i, j + 1);
				}
			}
		}
		return "";
	}

	static boolean isPalindrome(String s, int i, int j) {
		if(i > j) return true;
		if(isPalindrome[i][j] != null) return isPalindrome[i][j];
		if(i == j) {
			isPalindrome[i][j] = true;
			return true;
		}
		if(s.charAt(i) == s.charAt(j)) {
			if(isPalindrome(s, i + 1, j - 1)) {
				isPalindrome[i][j] = true;
				return true;
			}
		}
		isPalindrome[i][j] = false;
		return false;
	}	
}