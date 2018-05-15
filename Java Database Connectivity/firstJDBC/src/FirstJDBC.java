import java.sql.*; //导入数据库需要的包
public class FirstJDBC {
	public static void main(String[] args){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e) {}

	}
}
