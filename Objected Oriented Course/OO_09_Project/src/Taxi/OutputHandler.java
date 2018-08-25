package Taxi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//用于输出调度结果到文件以及控制台
public class OutputHandler {
	private File file;
	private BufferedWriter bw = null;
	private long SendTime;
	private long ServeTime;
	public OutputHandler(String str) {
		this.file = new File(str+".txt");
        try {
            this.bw = new BufferedWriter(new FileWriter(file,true));           
        } catch (IOException e) {}
	}
	
	public static void AllTaxi(Long time,String str, ArrayList<Integer> taxis) { 
		File file = new File(str+".txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file,true));
            bw.write("请求发出时刻"+ time +"    乘客请求内容："+str+"\r\n");
            bw.write("所有抢单的出租车编号：\r\n");
            for(int i=0; i < taxis.size();i++) bw.write(taxis.get(i)+"\r\n");
            bw.close();
        } catch (IOException e) {}
	}
	
	public static void ChooseTaxi(String str, int id) {
		File file = new File(str+".txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file,true));
            bw.write("抢到单的出租车编号：\r\n" + id + "\r\n");
            bw.close();
        } catch (IOException e) {}
	}
	
	public void IniSendTaxi(String str) {
		File file = new File(str+".txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file,true));
            bw.write("派单成功,去接乘客：		当前时间"+ System.currentTimeMillis()+"\r\n");
            SendTime = System.currentTimeMillis();
            bw.close();
        } catch (IOException e) {}
	}
	
	public void SendTaxi(String str,int posx, int posy, long time) { 
        try {
            this.bw.write(SendTime +":("+posx+","+posy+")\r\n");
            SendTime += 200;
        } catch (IOException e) {}
	}
	
	public void IniSendPerson(String str) {
		try {
            this.bw.write("接到乘客,开始运送：		当前时间" + System.currentTimeMillis() + "\r\n");
            ServeTime = System.currentTimeMillis() + 1000;
        } catch (IOException e) {}
	}
	
	public void SendPerson(String str,int posx, int posy) {
        try {
            this.bw.write(ServeTime+":("+posx+","+posy+")\r\n");
            ServeTime += 200;
        } catch (IOException e) {}
	}
	
	public void Finish(String str) {
        try {
            bw.write("运送成功\r\n");
            bw.close();
        } catch (IOException e) {}
	}
	
	public static void InitialToConsole(int id,int x, int y) { //打印出租车的初始位置
		System.out.println("出租车 " + id + " 初始位置在 " + "(" + x + "," + y + ")");
	}
}
