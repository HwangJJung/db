package jdbc;

import java.io.*;
import java.util.Random;


public class GenerateBookData {
	private final static int NUMOFRECORDS = 1000000;
	
	public void makebook() throws IOException {
		String filename = "/Users/jjungmac/Documents/3학기/DB/data100000.csv";
		try {
			Random r = new Random();
			BufferedWriter bw= new BufferedWriter(new FileWriter(filename));
			for(int i=0; i< NUMOFRECORDS; i++) {
				String output ="";
				output += i+","; //id;
				output += "title" + i+","; //title;
				output += "author" + r.nextInt(1000) ; //author
				output += "\n";
				bw.write(output);
			}
		bw.close();
		} catch (IOException e) {
			System.err.println("Error" + e.getMessage());
			return;
		}
	}
}
