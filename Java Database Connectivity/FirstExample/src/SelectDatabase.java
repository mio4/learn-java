import java.sql.*;
public class SelectDatabase {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/jdbc_create_demo";

	static final String USER = "root";
	static final String PASS = "zhangjin";

	public static void main(String[] args) throws Exception{
		Connection conn = null;
		Class.forName(JDBC_DRIVER);
		System.out.println("Connecting to a selected database...");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("Connecting to the database successfully");
		if(conn != null)
			conn.close();
		System.out.println("all things done~");
	}
}
