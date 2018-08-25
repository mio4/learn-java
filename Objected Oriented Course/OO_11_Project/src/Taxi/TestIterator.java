package Taxi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.awt.Point;

public class TestIterator extends Thread{
	/*Overview : 对于VIP出租车迭代器的输出，可以输出到指定文本，如Track.txt
    */
	private Taxi2[] taxis;
	private long time = 10000;
	private File file = new File("Track.txt");
	private BufferedWriter bw;


	public TestIterator(Taxi2[] taxis){
		/** @REQUIRES : None;
		 * @MODIFIES : this.taxis;
		 * @EFFECTS : this.taxis == taxis;
		 */
		this.taxis = taxis;
	}


	@Override
	public void run(){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : \result = new Point(srcx,srcy);
		 */
		while(true){
			outToFile();
			try{
				Thread.sleep(time);
			} catch(Exception e) {}
		}
	}


	public void outToFile(){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : request.equals(输出VIP出租车追踪信息到文本);
		 */
		try {
			this.bw = new BufferedWriter(new FileWriter(file, true));
		} catch(IOException e) {}
		for(int i=0;i < 30;i++){
			Iterator<VIPRequest> iter = taxis[i].iterator();
			if(iter.hasNext()){
				try {
					String str = "--------------------------\r\n";
					bw.write(str);
					VIPRequest v = iter.next();
					str = "请求产生时间：" + v.getReqTime() + "\r\n";
					bw.write(str);
					str = "请求出发地：" + "(" + v.getSrc().x + "," + v.getSrc().y + ")" + "\r\n";
					bw.write(str);
					str = "请求目的地：" + "(" + v.getDst().x + "," + v.getDst().y + ")" + "\r\n";
					bw.write(str);
					for(String s : v.getPath())
						bw.write(s);
					str = "--------------------------\r\n";
					bw.write(str);
				} catch (IOException e) {}
			}
		}
	}

	public static void outToFileTaxi(long time, Point src,Point dst,Point tax, int type,int id){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : request.equals(固定输出追踪信息到文本);
		 */
		//File file = new File("Track.txt");
		File file = new File("taxi"+id + " track" + ".txt");
		BufferedWriter bw = null;
		String str;
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			if(type == -1){
				str = "出租车：" + id + "\r\n";
				bw.write(str);
				bw.close();
			}
			else if(type == 0){
				str = "请求产生时间：" + time + "\r\n";
				bw.write(str);
				bw.close();
			} else if (type == 1){
				str = "请求出发地：" + "(" + src.x + "," + src.y + ")" + "\r\n";
				bw.write(str);
				bw.close();
			} else if (type == 2){
				str = "请求目的地：" + "(" + dst.x + "," + dst.y + ")" + "\r\n";
				bw.write(str);
				bw.close();
			} else if (type == 3){
				str = "(" + tax.x + "," + tax.y + ")" + "\r\n";
				bw.write(str);
				bw.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
