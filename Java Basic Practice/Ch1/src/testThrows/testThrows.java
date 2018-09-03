
package testThrows;
//throws表示将异常交给上一层函数去处理，并且异常不一定必须要发生
public class testThrows {
	public static void main(String[] args){
		try{
			fun();
		} catch(Exception e){
			//e.printStackTrace();
			System.err.println("非数据类型不能进行转换");
		}
	}
	public static void fun() throws NumberFormatException{
		String s = "abc";
		System.out.println(Double.parseDouble(s));
	}
}
