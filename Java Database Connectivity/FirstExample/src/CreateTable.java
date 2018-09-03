import java.sql.*;
public class CreateTable {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/jdbc_create_demo";

	static final String USER = "root";
	static final String PASS = "zhangjin";
	public static void main(String[] args) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		stmt = conn.createStatement();
		//表中元素的属性是需要分区大小写的
		//删除表只需要更改sql语句即可
		//sql = "DROP TABLE student"
		//插入数据只需要执行sql语句： "INSERT INTO student VALUES(103,'python','me',25)"
		
		String sql = "CREATE TABLE student(" +
				"id Integer NOT NULL," +
				"first VARCHAR(255)," +
				"LAST VARCHAR(255)," +
				"age Integer," +
				"PRIMARY KEY (id))";
		stmt.executeUpdate(sql);
		if(stmt != null)
			stmt.close();
		if(conn != null)
			conn.close();
		System.out.println("Done~");
	}
}
