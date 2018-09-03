package testFileWriter;
import java.io.*;
public class testFileWriter {
	public static void main(String[] args){
		FileWriter fw = null;
		try{
			fw = new FileWriter("Unicode.dat");
			for(int c=0; c < 60000; c++){
				fw.write(c);
			}
			FileReader fr = new FileReader("Unicode.dat");
			long num = 0;
			int b = 0;
			while((b = fr.read()) != -1){
				System.out.println((char)b + "\t");
				num++;
			}
			System.out.println("总共读取了 " + num + "字符");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
