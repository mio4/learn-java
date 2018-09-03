package testFileOutputStream;
import java.io.*;
public class TestFileOutputStream {
	public static void main(String[] args){
		int b = 0;
		FileInputStream in = null;
		FileOutputStream out = null;
		try{
			in = new FileInputStream("readme.txt");
			out = new FileOutputStream("readmeOut.txt");
			while((b = in.read()) != -1){
				out.write(b);
			}
			in.close();
			out.close();
		} catch(FileNotFoundException e1){
			System.out.println("文件读取失败");
			System.exit(-1);
		} catch(IOException e2){
			System.out.println("文件复制失败");
			System.exit(-1);
		}
		System.out.println("文件复制成功");
	}
}
