import java.io.File;

public class SafeFile extends File{
	
	public SafeFile(String str) { //传入字符串作为路径创建文件
		super(str);
	}
	
	@Override
	public synchronized boolean isDirectory() { //判断是否是目录
		try {
			return super.isDirectory();
		} catch (Exception e) {}
		return false;
	}
	
	@Override
	public synchronized boolean isFile() { //判断是否是文件
		try {
			return super.isFile();
		} catch (Exception e) {}
		return false;
	}
	
	@Override
	public synchronized long length() { //返回文件的大小
		try {
			return super.length();
		} catch (Exception e) {}
		return 0;
	}
	
	@Override
	public synchronized String getAbsolutePath(){ //返回文件的绝对路径
		try {
			return super.getAbsolutePath();
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	public synchronized long lastModified() { //返回文件的最后修改时间
		try {
			return super.lastModified();
		} catch (Exception e) {}
		return 0;
	}

	@Override
	public synchronized String getName() { //返回文件名称
		try {
			return super.getName();
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	public synchronized String getParent() { //返回父目录
		try {
			return super.getParent();
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	public synchronized boolean delete() { //删除文件
		try {
			return super.delete();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public synchronized boolean setLastModified(long time) {
		try {
			super.setLastModified(time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

