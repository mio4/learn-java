
import java.io.File;
import java.io.IOException;

public class monitor extends Thread{
	String root = "D://Test";
	String fpath;
	String ftrigger;
	String fmission;
	String realpath;
	summary sum;
	detail deta;
	
	String []repath = new String [1000];
	int renum = 0;
	
	
	long size;
	long sizenow;
	
	long time;
	long timenow;
	
	monitor(detail det, summary summ, String path, String trigger, String mission){
		this.sum = summ;
		this.deta = det;
		this.fpath = root + path;
		this.ftrigger = trigger;
		this.fmission = mission;
		
	}
	public void renameinitial() {
		File f = new File(fpath);
		realpath = f.getParent();
		File froot = new File(realpath);
		size = 0;
		time = f.lastModified();
		File []children = froot.listFiles();
		for(File ff: children) {
			size = size + ff.length();
			repath[renum++] = ff.getAbsolutePath();
		}
		
	}
	
	
	public boolean renamedjudge() {
		File f = new File(fpath);
		realpath = f.getParent();
		File froot = new File(realpath);
		
		sizenow = 0;
		String []repathnow = new String [1000];
		int pnow = 0;
		
		int loca = 0;
		
		
		File []children = froot.listFiles();
		for(File ff: children) {
			sizenow = sizenow + ff.length();
			repathnow[pnow++] = ff.getAbsolutePath();
		}
		if(sizenow == size && !f.exists()) {
			
				for(int i = 0, j = 0; j < renum; j++) {
					
					if(repathnow[i].equals(repath[j])) {
						i++;
						j = 0;
					}
					loca = i;
				}
			String fnow = repathnow[loca];
			File fn = new File(fnow);
			timenow = fn.lastModified();
			if("record-detail".equals(fmission)) {
				synchronized(deta) {
					deta.path = fpath;
					deta.pathnow = fnow;
					deta.time = time;
					deta.timenow = timenow;
					deta.size = size;
					deta.sizenow = size;
					deta.detailout();
					deta.notify();
				}
			}
			
			
			

			f = fn;
			return true;
		}
		else {
			return false;
		}
	}
	public void modifiedini() {
		File f = new File(fpath);
		time = f.lastModified();
	}
	public boolean modifiedjudge() {
		File f = new File(fpath);
		timenow = f.lastModified();
		if(time != timenow) {
			if("record-detail".equals(fmission)) {
				synchronized(deta) {
					deta.path = fpath;
					deta.pathnow = fpath;
					deta.time = time;
					deta.timenow = timenow;
					deta.size = f.length();
					deta.sizenow = f.length();
					deta.detailout();
					deta.notify();
				}
			}
			
			time = timenow;
			return true;
		}
		else {
			return false;
		}
	}
	public void sizechangeini() {
		File f = new File(fpath);
		size = f.length();
	}
	public boolean sizechangejudge() {
		File f = new File(fpath);
		sizenow = f.length();
		timenow = f.lastModified();
		if(size != sizenow) {
			if("record-detail".equals(fmission)) {
				synchronized(deta) {
					deta.path = fpath;
					deta.pathnow = fpath;
					deta.time = time;
					deta.timenow = timenow;
					deta.size = size;
					deta.sizenow = sizenow;
					deta.detailout();
					deta.notify();
				}
			}
			size = sizenow;
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public void monitorfile() {
		if("renamed".equals(ftrigger)) {
//			System.out.println("----------RENAME----------");
			if(renamedjudge()) {
				System.out.println("----------RENAME JUDGE----------");
				if("record-summary".equals(fmission)) {
					System.out.println("----------RENAME OJBK----------");
				synchronized(sum){
					System.out.println("----------RENAME DETECTED----------");
					sum.renamednum++;
					sum.summaryout();
					sum.notify();
				}
					this.stop();}
				
				
				
			}
		}
		else if("Modified".equals(ftrigger)) {
			if(modifiedjudge()) {
				if("record-summary".equals(fmission)) {
					synchronized(sum){
						sum.modinum++;
						sum.summaryout();
						sum.notify();}
					}
			}
		}
		else if("size-changed".equals(ftrigger)) {
			if(sizechangejudge()) {
				if("record-summary".equals(fmission)) {
					synchronized(sum){
						sum.sizenum++;
						sum.summaryout();
						sum.notify();}
					}
			}
		}
	}
	public void run() {
		renameinitial();
		modifiedini();
		sizechangeini();
		
		while(true) {
			monitorfile();
			try {
				this.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
