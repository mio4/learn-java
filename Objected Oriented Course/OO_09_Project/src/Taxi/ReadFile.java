package Taxi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

public class ReadFile {
	private int[][] map;

	public ReadFile() {
		/** @REQUIRES: None
		* @MODIFIES: None
		* @EFFECTS: None
		*/
	}

	public void ReadMap(String path, int size) {
		/** @REQUIRES: path!=null
		* @MODIFIES: map
		* @EFFECTS: \exist file ==> (read map)
		*/
		try {
			this.map = new int[80][80];
			FileReader reader = new FileReader(path);
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			int row = 0,num = 0;	
			while((str=br.readLine()) != null) { //将读取的字符串中的数字存储到Map数组中
				int i = 0;
				while(i != size) {
					num = str.charAt(i);
					//判断是否存在其他字符：
					if(num!=' ' && num!= '\t') {
						if(num!='0' && num!='1' && num!='2' && num!='3') {
							System.out.println("地图信息中出现非法字符，请重新运行程序");
							System.exit(0);
						}
						map[row][i++] = num-'0';
					}
					//if(i == size) break;
				}
				row++;
			}		
		} catch (Exception e) {
			System.out.println("地图输入信息出错，请重新运行程序");
			
		}
	}
	
	public int[][] getMap(){
		/** @REQUIRES: None
		* @MODIFIES: None
		* @EFFECTS: \result == map
		*/
		return this.map;
	}
	
}
