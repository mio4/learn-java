package Taxi;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFile {
	/*Overview ： 用于读入地图文件信息以及读入红绿灯文件信息
	 */
	private int[][] map;
	private int[] light;
	private int lightCnt = 0;

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : None;
	 */
	public ReadFile() {

	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == invariant(this);
	 */
	public boolean repOK(){
		if(map==null || light==null)
			return false;
		return true;
	}

	/** @REQUIRES : path!=null
	 * @MODIFIES : this.map
	 * @EFFECTS : \exist file ==> (read map)
	 */
	public void ReadMap(String path, int size) {

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
							System.out.println("地图信息中出现非法字符，退出程序");
							System.exit(0);
						}
						map[row][i++] = num-'0';
					}
				}
				row++;
			}		
		} catch (Exception e) {
			System.out.println("地图输入文件出错，请重新运行程序");
			System.exit(0);
		}
	}

	/** @REQUIRES : path!=null;
	 * @MODIFIES : this.light;
	 * @EFFECTS : \exist file ==> (read light);
	 */
	public void ReadLight(String path, int size){

		//int tempCnt = 0;
		try {
			this.light = new int[6405];
			FileReader fileReader = new FileReader(path);
			BufferedReader br = new BufferedReader(fileReader);
			String str = null;
			int num = 0;
			while((str=br.readLine()) != null){
				int i = 0;
				//int temp = str.length();
				while(i<str.length()){
					num = str.charAt(i);
					if(num != ' ' && num != '\t'){
						if(num != '0' && num != '1'){
							System.out.println("红绿灯文件出现非法字符，程序退出");
							System.exit(0);
						}
						this.light[(this.lightCnt)++] = num-'0';
					}
					i++;
				}
			}
			if(this.lightCnt != 6400) {
				System.out.println("红绿灯文件数字个数不够");
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("红绿灯输入文件出错，请重新运行程序");
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == map;
	 */
	public int[][] getMap(){
		return this.map;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == light;
	 */
	public int[] getLight(){
		return this.light;
	}

}
