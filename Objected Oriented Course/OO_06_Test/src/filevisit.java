
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.IOException;

public class filevisit extends Thread{
	final String root = new String("D://Test");
	String path;
	String ff;
	public filevisit(String file) {
		ff = this.root + file;
	}
	public boolean creatfile() {
		File f = new File(ff);
		path = f.getParent();
		File froot = new File(path);
		if(froot.exists()) {
			;
			
		}
		else {
			froot.mkdirs(); 
		}
	
		if(f.exists()) {
			System.out.println("文件已存在");
			return false;
		}
		else {
			try {
				f.createNewFile();
				System.out.println("已在指定路径成功创建文件");
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return true;
		}
	}
	public boolean deletefile() {
		File f = new File(ff);
		if(f.exists()) {
			f.delete();
			System.out.println("指定文件已删除");
			return true;
		}
		else {
			System.out.println("指定文件不存在");
			return false;
		}
	}
	public boolean renamefile(String s) {
		String fff = new String();
		fff = root + s;
		File f = new File(ff);
		File f_after = new File(fff);
		if(f.exists()) {
			f.renameTo(f_after);
			System.out.println("指定文件已重命名");
			return true;
		}
		else {
			System.out.println("指定文件不存在");
			return false;
		}
	}
	public boolean modifytime() {
		File f = new File(ff);
		long st;
		st = System.currentTimeMillis();
		if(f.exists()) {
			f.setLastModified(st);
			System.out.println("已成功使指定文件的最后修改时间为目前时间");
			return true;
		}
		else {
			System.out.println("指定文件不存在");
			return false;
		}
		
	}
	public boolean movefile(String s) {
		File f = new File(ff);
		String fff;
		fff = root + s;
		File f_after = new File(fff);
		path = f_after.getParent();
		File froot = new File(path);
		if(f.exists()) {
			
			if(froot.exists()) {
				
					f.renameTo(f_after);
				
			}
			else {
				froot.mkdirs();
				
					f.renameTo(f_after);
				
			}
			System.out.println("指定文件已移动到指定位置");
			return true;
		}
		else {
			System.out.println("指定文件不存在");
			return false;
		}
	}
	public boolean sizechange() {
		File f = new File(ff);
		if(f.exists()) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
			writer.write("oo is a such a thing to break your body and break your mind");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("指定文件改变大小成功");
		return true;
		}
		else {
			System.out.println("指定文件不存在");
			return false;
		}
	}
	public void run() {
		try {
			this.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// add your operation//

	}

}
