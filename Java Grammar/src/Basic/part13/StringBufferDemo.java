package Basic.part13;

public class StringBufferDemo {
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer();
/*		StringBuffer sb1 = sb.append(100);
		System.out.println(sb == sb1);
		System.out.println(sb);
		System.out.println(sb1);*/

		sb.append(100);
		sb.append("mio");
		System.out.println(sb.toString());
	}
}
