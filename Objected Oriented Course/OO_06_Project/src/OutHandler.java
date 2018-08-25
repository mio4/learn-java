import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//用于输出到文件
public class OutHandler {
	private String[] strs = {"renamed","modified","path-changed","size-changed"};
	private int rename_t = 0;
	private int modify_t = 0;
	private int pathc_t = 0;
	private int sizec_t = 0;
	
	public OutHandler() {}
	
	public void FModified(String task, String obje, long t1, long t2) {
		modify_t++;
		BufferedWriter bw = null;
		String str;
		if(task.equals("record-detail"))
		{
			SafeFile f = new SafeFile("detail.txt");
			str = "modified "+ obje + " time from " + t1 + " to " + t2 + "\r\n";
			try {
				bw = new BufferedWriter(new FileWriter(f,true));
				bw.write(str);
				bw.close();
			} catch (IOException e) {}
		} else if (task.equals("record-summary")) {
			Record_Summary();
		}	
	}
	
	public void FSizeChanged(String task, String obje, long s1, long s2) {
		sizec_t++;
		BufferedWriter bw = null;
		String str;
		if(task.equals("record-detail")) {
			SafeFile f = new SafeFile("detail.txt");
			str = "size-changed " + obje + " from " + s1 + " to " + s2 + "\r\n";
			try {
				bw = new BufferedWriter(new FileWriter(f,true));
				bw.write(str);
				bw.close();
			} catch (IOException e) {}
		} else if (task.equals("record-summary")) {
			Record_Summary();
		}
	}
	
	public void FRenamed(String task, String obje, String n1, String n2, String obje2) {
		rename_t++;
		BufferedWriter bw = null;
		String str;
		if(task.equals("record-detail")) {
			SafeFile f = new SafeFile("detail.txt");
			str = "renamed " + obje + " from " + n1 + " to " + n2 + "\r\n";
			try {
				bw = new BufferedWriter(new FileWriter(f,true));
				bw.write(str);
				bw.close();
			} catch (IOException e) {}
		} else if (task.equals("record-summary")) {
			Record_Summary();
		} else if (task.equals("recover")) {
			SafeFile f = new SafeFile(obje2);
			SafeFile path = new SafeFile(obje);
			f.renameTo(path);		
		}
	}
	
	public void FPathChanged(String task, String obje, File path, String obje2) {
		pathc_t++;
		BufferedWriter bw = null;
		String str;
		if(task.equals("record-detail")) {
			SafeFile f = new SafeFile("detail.txt");
			str = "path-changed " + obje + " from " + obje + " to " + path.getAbsolutePath() + "\r\n";
			//System.out.println(str);
			try {
				bw = new BufferedWriter(new FileWriter(f,true));
				bw.write(str);
				bw.close();
			} catch (IOException e) {}
		} else if (task.equals("record-summary")) {
			Record_Summary();
		} else if (task.equals("recover")) {	
			SafeFile f = new SafeFile(obje2);
			SafeFile path1 = new SafeFile(obje);
			f.renameTo(path1);
		}
	}
	
	public void Record_Summary() {
		BufferedWriter bw = null;
		SafeFile f = new SafeFile("summary.txt");
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			bw.write("Trigger "+strs[0]+" "+rename_t+" times\r\n");
			bw.write("Trigger "+strs[1]+" "+modify_t+" times\r\n");
			bw.write("Trigger "+strs[2]+" "+pathc_t+" times\r\n");
			bw.write("Trigger "+strs[3]+" "+sizec_t+" times\r\n");
			bw.write("------------\r\n");
			bw.close();
		} catch (Exception e) {}
	}
}
