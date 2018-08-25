

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class detail {
	String path;
	String pathnow;
	long time;
	long timenow;
	long size;
	long sizenow;
	String name;
	String namenow;
	public void detailout() {
		try {
			System.out.println("----------detail----------");
			BufferedWriter writer = new BufferedWriter(new FileWriter("D://result//detail.txt", false)); 
			writer.write("路径从：" + path + "   更改到：" + pathnow);
			writer.write("\r\n");
			writer.write("修改时间从：" + time + "   更改到：" + timenow);
			writer.write("\r\n");
			writer.write("文件大小从：" + size + "   更改到：" + sizenow);
			writer.write("\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
