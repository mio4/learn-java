//查询数据库中的元素
import java.sql.*;
public class SelectRecords {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/emp";

	static final String USER = "root";
	static final String PASS = "zhangjin";

	public static void main(String[] args) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		stmt = conn.createStatement();
		String sql = "SELECT id,first,last,age FROM employees;";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			int id = rs.getInt("id");
			int age = rs.getInt("age");
			String first= rs.getString(("first"));
			String last = rs.getString("last");

			System.out.print("ID: " + id);
			System.out.print(",Age:" + age);
			System.out.print(",First:" + first);
			System.out.println(",last:" + last);
		}

		if(stmt != null)
			stmt.close();
		if(conn != null)
			conn.close();
		System.out.println("Done~");
	}
}
