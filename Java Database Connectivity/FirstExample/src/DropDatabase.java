import java.sql.*;
public class DropDatabase {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/jdbc_create_demo";

	static final String USER = "root";
	static final String PASS = "zhangjin";

	public static void main(String[] args) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		//注册驱动
		Class.forName(JDBC_DRIVER);
		//连接到数据库
		System.out.println("Connecting to the database...");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("Connected to the database successfully");
		//执行语句
		System.out.println("Deleting the database...");
		stmt = conn.createStatement();
		//mysql执行语句不区分大小写，包括其中的数据库名称
		String sql = "DROP DATABASE Jdbc_create_DEMO2";
		stmt.executeUpdate(sql);
		System.out.println("Deleting the database successfully");
		if(stmt != null)
			stmt.close();
		if(conn != null)
			stmt.close();
		System.out.println("All things done~");
	}
}

