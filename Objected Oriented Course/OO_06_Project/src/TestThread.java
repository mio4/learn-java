import java.io.BufferedWriter;
import java.io.FileWriter;

//用于测试
public class TestThread extends Thread{
	
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch(Exception e) {}
		changeTime("F:/test/1.txt",System.currentTimeMillis()); //测试时将参数替换成需要测试的文件的绝对路径
		addFile("F:/test/100.txt"); //添加一个100.txt文件，参数为文件绝对路径
		rename("F:/test/1.txt","F:/test/test01/1.txt"); //改变文件路径
		move("F:/test/test01/1.txt","F:/test/1.txt"); //移动文件
		delete("F:/test/1.txt"); //删除文件
		changeSize("F:/test/2.txt"); //修改文件大小	
		System.out.println("TestThread finished");
	}

	public boolean addFile(String file) { //测试SafeFile类的添加文件功能
		SafeFile f = new SafeFile(file);
		try {
			if(!f.exists()) { //如果文件在当前文件夹下不存在
				f.createNewFile();
				return true; //添加成功
			}
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean rename(String from,String to) { //测试SafeFile类的rename功能
		try {
			SafeFile f1 = new SafeFile(from);
			SafeFile f2 = new SafeFile(to);
			f1.renameTo(f2);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean delete(String src) { //测试SafeFile类的删除文件功能
		try {
			SafeFile f = new SafeFile(src);
			f.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean move(String from,String to) { //个人理解move方法和rename功能相同
		try {
			rename(from,to);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	
	public boolean changeTime(String File,long time) {
		SafeFile f = new SafeFile(File);
		try {
			f.setLastModified(time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean changeSize(String file) {
		SafeFile sf = new SafeFile(file);
		try {
			BufferedWriter bw = null;
			String str = " ";	
			bw = new BufferedWriter(new FileWriter(sf,true));
			bw.write(str);
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
