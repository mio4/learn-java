package testFileInputStream;
import java.io.*;
public class TestFileInputStream {
	public static void main(String[] args){

		//FileInputStream in = null;
		FileReader in = null;
		//首先是找到文件
		try{
			//in = new FileInputStream("readme.txt");
			in = new FileReader("readme.txt");
		} catch(FileNotFoundException e){
			System.out.println("系统找不到指定文件");
			System.exit(-1);
		}
		//然后是读取字节
		int b = 0;
		long num = 0;
		try{
			while((b = in.read()) != -1){
				System.out.println((char) b);
				num++;
			}
			in.close();
			System.out.println("总共读取了 " + num + "个字节的文件");
		} catch(IOException e){
			System.out.println("文件读取错误!");
		}
	}
}
