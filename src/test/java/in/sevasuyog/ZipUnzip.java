package in.sevasuyog;

import java.io.FileReader;
import java.io.IOException;

public class ZipUnzip {
	static String charSet = "1234567890asdfghjklzxcvbnmqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM \n";
	
	public static void main(String[] args) throws IOException {
		System.out.println(charSet.length());
		StringBuffer sb = zip("C:\\Users\\LENOVO\\Desktop\\Jayram\\Notes.txt");
		preprocess(sb);
	}

	private static void preprocess(StringBuffer sb) {
		// TODO Auto-generated method stub
		//Byte bite = Byte.valueOf((byte) 0);
		//bite.set
		
	}

	private static StringBuffer zip(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		StringBuffer sb = new StringBuffer("");
		while(true) {
			int c = fileReader.read();
			if(c == -1) break;
			sb.append((char) c);
		}
		
		fileReader.close();
		return sb;
	}
}
