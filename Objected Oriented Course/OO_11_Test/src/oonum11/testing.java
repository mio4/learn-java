package oonum11;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class testing {
	public static void main(String[] args){
		String s = "";
		s+="0";
		for(int i=0;i<78;i++)
			s+="3";
		s+="0";
		s+="\r\n";
		for(int i=0;i<79;i++){
			File file = new File("map2.txt");
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(file,true));
				bw.write(s);
				bw.close();
			} catch (IOException e) {}
		}


		s = "";
		s+="0";
		for(int i=0;i<78;i++)
			s+="0";
		s+="0";
		s+="\r\n";
		File file = new File("map2.txt");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file,true));
			bw.write(s);
			bw.close();
		} catch (IOException e) {}

	}
}
