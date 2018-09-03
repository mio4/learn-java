import java.io.File;
import java.util.ArrayList;
public class Trigger extends Thread{
	private ArrayList<FileInfo> files1 = new ArrayList<FileInfo>(); //保存父目录下最顶层目录的所有文件信息
	private ArrayList<FileInfo> files2 = new ArrayList<FileInfo>(); //保存父目录下的所有文件信息
	private final static String[] strs1 = {"renamed", "modified", "path-changed", "size-changed"}; 
	private final static String[] strs2 = {"record-summary", "record-detail", "recover"};
	OutHandler outHandler = new OutHandler(); //每一个线程单独有一个OutHandler对象
	private String obje;
	private String trig;
	private String task;
	private long pre_time; 
	private long pre_size;
	private String pre_name;
	private String pre_path; 
	
	
	public Trigger(String obje, String trig, String task) {
		this.obje = obje;
		this.trig = trig;
		this.task = task;
		//初始化时记录就记录文件信息
		SafeFile f = new SafeFile(obje);
		pre_size = f.length();
		pre_time = f.lastModified();
		pre_name = f.getName();
		pre_path = f.getAbsolutePath();
	}
	
	@Override
	public void run() {
		SafeFile f = new SafeFile(obje);
		pre_size = f.length();
		pre_time = f.lastModified();
		pre_name = f.getName();
		pre_path = f.getAbsolutePath();
		//保存文件夹下的所有文件快照
		SnapShot();
		
		//首先判断是否是文件夹
		if(f.isDirectory()) { //监控文件夹
			while(true) {
				try {
					Thread.sleep(233);
				} catch(Exception e) {}
				synchronized(this) {
					DModified();
					DRenamed();
					DPathChanged();
					DSizeChanged();
				}
			} 
		} else if (f.isFile()){ //监控文件
			while(true) {
				try {
					Thread.sleep(233);
				} catch(Exception e) {}
				synchronized(this) {
					FModified();
					FRenamed();
					FPathChanged();
					FSizeChanged();
				}
			}
		}
	}
	
	public void SnapShot() { 
		String parent_path = null;
		SafeFile f = new SafeFile(obje);
		if(f.isFile()) { //如果是文件，找到父目录
			parent_path = f.getParent();
		} else if (f.isDirectory()) { //如果是文件夹，就是文件夹的路径
			parent_path = obje;
		}
		//file1快照
		try {
			SafeFile pFile = new SafeFile(parent_path);
			String[] pFileList = pFile.list();
			for(int i=0; i < pFileList.length;i++) {
				File rFile1 = new File(pFile.getAbsoluteFile()+"\\"+pFileList[i]);
				FileInfo readFileInfo = new FileInfo(rFile1.getAbsolutePath(),rFile1.getName(),rFile1.length(),rFile1.lastModified());
				files1.add(readFileInfo);
			}
		} catch (NullPointerException e) {}
		//file2快照
		SnapShotRecursion(parent_path,files2);
	}
	
	public void SnapShotRecursion(String path, ArrayList<FileInfo> files) { //递归将文件信息保存时使用，第一个参数：绝对路径，第二个参数：需要添加到的ArrayList
		try {
			File f = new File(path);
			if(f.isFile()) {
				FileInfo fi = new FileInfo(f.getAbsolutePath(),f.getName(),f.length(),f.lastModified());
				files.add(fi);
			} else {
				String[] flist = f.list();
				for(int i=0; i < flist.length; i++) {
					SnapShotRecursion(f.getAbsolutePath()+"\\"+flist[i], files);
				}
			}
		} catch(NullPointerException e) {}
	}
	//--------------------监控范围是文件的方法
	public void FModified() {
		SafeFile f = new SafeFile(obje);
		if(pre_time != f.lastModified() && trig.equals("modified")) { //发现文件发生修改
			outHandler.FModified(task,obje, pre_time, f.lastModified());
			pre_time = f.lastModified();
		}
	}
	public void FRenamed() {
		SafeFile f = new SafeFile(obje);
		if(!f.exists()) { //发现文件不存在了
			SafeFile dir = new SafeFile(f.getParent());
			String[] dirlist = dir.list();
			for(int i= 0; i < dirlist.length ;i++) { //扫描父目录下的所有文件
				File rFile = new File(dir.getAbsoluteFile()+"\\"+dirlist[i]); 
				if(rFile.length()==pre_size && rFile.lastModified()==pre_time) {
					outHandler.FRenamed(task, obje, pre_name, rFile.getName(),rFile.getAbsolutePath());
					pre_size = -1; 
				}
			}
		}
	}
	
	public void FPathChanged() {
		SafeFile f = new SafeFile(obje);
		if(!f.exists()) { //发现当前路径的文件不见了
			FRecursion(f.getParent(),f);
		}
	}
	public void FRecursion(String path, File sf) {
		try {
			File f = new File(path);
			if(f.isFile()) { //对比文件信息
				if(f.getName().equals(sf.getName()) && f.length()==pre_size && f.lastModified()==pre_time && f.getAbsolutePath()!=pre_path) {
					outHandler.FPathChanged(task, sf.getAbsolutePath(), f, f.getAbsolutePath());
					pre_size = -1;
				}
			} else { //递归到一下层
				String[] flist = f.list();
				for(int i=0; i < flist.length; i++) {
					FRecursion(f.getAbsolutePath()+"\\"+flist[i],sf);
				}
			}
		} catch(Exception e) {}
	}
	
	
	//--------------------------------
	
	public void FSizeChanged() {
		SafeFile f = new SafeFile(obje);
		if(f.exists() && pre_time != f.lastModified() && pre_size != f.length()) { //发现文件size-changed
			outHandler.FSizeChanged(task, obje, pre_size, f.length());
			pre_size = f.length();
			pre_time = f.lastModified();
		}
	}
	
	//------------------监控范围是文件夹的方法
	
	public void DModified() {
		ArrayList<FileInfo> files3 = new ArrayList<FileInfo>();
		SnapShotRecursion(obje,files3);
		for(int i=0; i < files3.size();i++) {
			for (int j=0; j < files2.size();j++) {
				if(trig.equals("modified") && files3.get(i).getPath().equals(files2.get(j).getPath()) && files3.get(i).getTime() != files2.get(j).getTime()) { //两个是同一个文件,并且修改时间不同
					outHandler.FModified(task,files3.get(i).getPath(),files3.get(i).getTime(),files2.get(j).getTime());
					files2.get(j).setTime(files3.get(i).getTime());
				}
			}
		}
	}
	
	public void DSizeChanged() {
		ArrayList<FileInfo> files3 = new ArrayList<FileInfo>();
		SnapShotRecursion(obje,files3);
		for(int i=0; i < files3.size();i++) {
			for (int j=0; j < files2.size();j++) {
				if(trig.equals("size-changed") && files3.get(i).getPath().equals(files2.get(j).getPath()) && files3.get(i).getTime() != files2.get(j).getTime() && files3.get(i).getSize() != files2.get(j).getSize()) { //两个是同一个文件,并且修改时间不同
					outHandler.FSizeChanged(task, files3.get(i).getPath(), files2.get(j).getSize(), files3.get(i).getSize());
					files2.get(j).setTime(files3.get(i).getTime());
					files2.get(j).setSize(files3.get(j).getSize());
				}
			}
		}
	}
	
	public void DRenamed() {
		//从快照中提取文件路径，如果发现文件不在了，就扫描同层目录对比是否有renamed现象产生
		for(int i=0; i < files2.size();i++) {
			SafeFile f = new SafeFile(files2.get(i).getPath());
			if(!f.exists()) { //发现文件不存在了
				SafeFile dir = new SafeFile(f.getParent());
				String[] dirlist = dir.list();
				for(int j= 0; j < dirlist.length ;j++) { //扫描父目录下的所有文件
					File rFile = new File(dir.getAbsoluteFile()+"\\"+dirlist[i]); 
					if(rFile.length()==files2.get(i).getSize() && rFile.lastModified()==files2.get(i).getTime()) {
						outHandler.FRenamed(task, files2.get(i).getPath(), files2.get(i).getName(), rFile.getName(),rFile.getAbsolutePath());
						files2.get(i).setSize(-1); //重置
					}
				}
			}	
		}
	}
	
	public void DPathChanged() {
		//从快照中提取文件路径，如果发现文件不在了，就递归扫描所有层的文件对比是否是因为路径改变的问题。
		for(int i=0; i < files2.size();i++) {
			SafeFile f = new SafeFile(files2.get(i).getPath());
			if(!f.exists()) { //文件不存在了
				DPathChangedRecursion(obje,files2.get(i));
			}
		}
	}
	
	public void DPathChangedRecursion(String path, FileInfo fi) {//path:递归到的绝对路径成熟； fi:需要对比的文件信息
		try {
			File f = new File(path);
			if(f.isFile()) { //对比文件信息
				if(f.getName().equals(fi.getName()) && f.length()==fi.getSize() && f.lastModified()==fi.getTime() && f.getAbsolutePath()!=fi.getPath()) {
					outHandler.FPathChanged(task, fi.getPath(), f, f.getAbsolutePath());
					fi.setSize(-1);
				}
			} else { //递归到下一层
				String[] flist = f.list();
				for(int i=0; i < flist.length; i++) {
					DPathChangedRecursion(f.getAbsolutePath()+"\\"+flist[i],fi);
				}
			}
		} catch(NullPointerException e) {}
	}		
}
