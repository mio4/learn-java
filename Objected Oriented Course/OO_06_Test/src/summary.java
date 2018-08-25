
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.IOException;

public class summary {
	int renamednum = 0;
	int modinum = 0;
	int sizenum = 0;
	int pathnum = 0;
	public void summaryout() {
		try {
			System.out.println("----------summary----------");
			BufferedWriter writer = new BufferedWriter(new FileWriter("D://result//summary.txt", false)); 
			writer.write("renamed的触发次数为：" + renamednum);
			writer.write("\r\n");
			writer.write("Modified的触发次数为：" + modinum);
			writer.write("\r\n");
			writer.write("path-changed的触发次数为：" + pathnum);
			writer.write("\r\n");
			writer.write("size-changed的触发次数为：" + sizenum);
			writer.write("\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
