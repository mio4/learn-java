package oo_13;
 
import java.util.Scanner;

class Floor {
	/**@OVERVIEW:主类
	 */
	
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in); 
		Requeue queue=new Requeue();
		subControl c=new subControl();
		long iniTime=-1;
		try {	
			while(true) {
				if(queue.returnNum()==113) break;
				String str=s.nextLine();
				str=str.replaceAll("\\s*", "");
				if(str.equals("RUN")) {
					break;
				}
				else if(str.equals("")) {
					System.out.println("INVALID"+str);
					continue;
				}
				else if(str.matches("\\(FR,\\+?(\\d+),(UP|DOWN),\\+?(\\d+)\\)") ){//(FR,5,UP,0)
					str=str.replace("(", "");
					str=str.replace(")", "");
					String[] ss=str.split(",");
					if(iniTime==-1 && (str.matches("FR,1,UP,\\+?0")==false)) {
						System.out.println("INVALID("+str+")");
						continue;
					}
					else {
						if(iniTime>Long.valueOf(ss[3])){
							System.out.println("INVALID("+str+")");
							continue;
						}
						else {
							iniTime=Long.valueOf(ss[3]);
						}
					}
					if(Math.max(ss[3].length(), ss[1].length())>15) {
						System.out.println("INVALID("+str+")");
						continue;
					}
					else if(Long.valueOf(ss[1])>10 ||Long.valueOf(ss[1])<1||Long.valueOf(ss[3])>(Math.pow(2, 32)-1) || Long.valueOf(ss[3])<0) {
						System.out.println("INVALID("+str+")");
						continue;
					}
					else if((ss[1].equals("10") && ss[2].equals("UP")) || (ss[1].equals("1") && ss[2].equals("DOWN"))){
						System.out.println("INVALID("+str+")");
						continue;
					}
					Request r=new Request();
					r.outOrder(Integer.valueOf(ss[1]), ss[2], Long.valueOf(ss[3]),str);
					queue.add(r);
				}
				else if(str.matches("\\(ER,\\+?(\\d+),\\+?(\\d+)\\)")) {//(ER,3,0)
					str=str.replace("(", "");
					str=str.replace(")", "");
					String[] ss=str.split(",");
					if(iniTime==-1) {
						System.out.println("INVALID("+str+")");
						continue;
					}
					else {
						if(iniTime>Long.valueOf(ss[2])){
							System.out.println("INVALID("+str+")");
							continue;
						}
						else {
							iniTime=Long.valueOf(ss[2]);
						}
					}
					if(Math.max(ss[2].length(), ss[1].length())>15) {
						System.out.println("INVALID("+str+")");
						continue;
					}
					else if(Long.valueOf(ss[1])>10 ||Long.valueOf(ss[1])<1||Long.valueOf(ss[2])>(Math.pow(2, 32)-1) || Long.valueOf(ss[2])<0) {
						System.out.println("INVALID("+str+")");
						continue;
					}
					Request r=new Request();
					r.inOrder(Integer.valueOf(ss[1]),Long.valueOf(ss[2]),str);
					queue.add(r);
				}
				else {
					System.out.println("INVALID"+str);
				}
			}
			c.command(queue);
			c.command(queue,0);
		}
		catch(Exception e) {
			System.out.println("ERROR");
			System.out.println("#sth went wrong");
		}
		catch(Error b) {
			System.out.println("ERROR");
			System.out.println("#sth went wrong");
		}
	}
}
