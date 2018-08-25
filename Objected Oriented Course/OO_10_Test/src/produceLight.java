import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class produceLight {
	public static void main(String[] args){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i < 80;i++)
			sb.append("1");
		sb.append("\r\n");
		File file = new File("lightmap1.txt");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file,true));
			for(int i=0;i < 80;i++)
				bw.write(sb.toString());
			//bw.write("请求发出时刻"+ time +"    乘客请求内容："+str+"\r\n");
			//bw.write("所有抢单的出租车编号：\r\n");
			//for(int i=0; i < taxis.size();i++) bw.write(taxis.get(i)+"\r\n");
			bw.close();
		} catch (IOException e) {}

	}
}
