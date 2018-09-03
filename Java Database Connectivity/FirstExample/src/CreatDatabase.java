import java.sql.*;

public class CreatDatabase {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";

	static final String USER = "root";
	static final String PASS = "zhangjin";

	public static void main(String[] args){
		Connection conn = null;
		Statement stmt = null;
		try {
			//注册JDBC驱动
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			String sql_stmt = "CREATE DATABASE jdbc_create_demo3";
			int result = stmt.executeUpdate(sql_stmt);
			//executeUpdate方法返回收到影响的行数
			System.out.println("changed line:" + result);
			System.out.println("Creating database successfully");
		} catch (SQLException se){
			se.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			//close the statement
			try {
				if(stmt!=null)
					stmt.close();
			} catch (SQLException se){

			}
			//close the connection
			try {
				if(conn!=null)
					conn.close();
			} catch (SQLException e){

			}
		}
		System.out.println("All things done");
	}
}

