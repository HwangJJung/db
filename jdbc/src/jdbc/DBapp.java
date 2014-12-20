package jdbc;

import java.io.IOException;

 import java.sql.*;


public class DBapp {
	
	public static void main(String[] args) throws IOException {

		GenerateBookData gbd = new GenerateBookData();
		gbd.makebook();
		
		
		Connection conn = null;
		Statement stmt =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql = null;
		String jdbcUrl="jdbc:mysql://localhost/BOOKSHOP";
		String userID="root";
		String userPW="tomntoms";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch ( ClassNotFoundException e ) {
			System.err.println("Driver Error" + e.getMessage());
			return;
		}
		System.out.println("JDBC Driver is found. OK.");
			
//		try {
//			conn = DriverManager.getConnection(jdbcUrl, userID, userPW);
//			System.out.println("Connection Success");
//			stmt= conn.createStatement();
//			sql= "SELECT * FROM book_inventory";
//			rs= stmt.executeQuery( sql);
//			while (rs.next() ) {
//				int num= rs.getInt(1) ; // number
//				String title = rs.getString("title");
//				String author= rs.getString("author");
//				System.out.printf("%d %s %s\n", num, title, author);
//			}
//		stmt.close();
//		conn.close();
//		} catch ( SQLException e ) {
//			System.err.println("DB Error" + e.getMessage());
//			return;
//		}
//		
		
//		try {
//			conn = DriverManager.getConnection(jdbcUrl, userID, userPW);
//			conn.setAutoCommit(false);
//			System.out.println("Connection AutoCommit false");
//			
//			try{
//				sql= "UPDATE book_inventory SET title=?, author=?, publication=? WHERE id=?";
//				pstmt = conn.prepareStatement(sql);
//				stmt = conn.createStatement();
//				pstmt.setString(1, "Head First SQL");
//				pstmt.setString(2, "린베일리");
//				pstmt.setString(3, "한빛미디어");
//				pstmt.setInt(4, 101);
//				pstmt.execute();
//				sql= "SELECT * FROM book_inventory WHERE id = 101";
//				rs= stmt.executeQuery(sql);
//				while (rs.next() ) {
//					int num = rs.getInt(1) ; // number
//					String title = rs.getString("title");
//					String author= rs.getString("author");
//					System.out.printf("%d %s %s\n", num, title, author);
//				}
//				conn.commit();
//			} catch(SQLException e) {
//				conn.rollback();
//				System.err.println("DB Error" + e.getMessage());
//				return;
//			} finally {
//				if(pstmt!=null)
//					try { pstmt.close(); }
//					catch(final SQLException e) {
//					}
//				if(rs!=null)
//					try { rs.close(); }
//					catch(final SQLException e) {
//					}
//				if(conn !=null)
//					try { conn.setAutoCommit(true);}
//					catch(final SQLException e) {
//					}
//				stmt.close();
//				conn.close();
//			}
//		} catch(SQLException e) {
//			System.err.println("DB Connection Error " + e.getMessage());
//			return;
//		}
//		

		
		//transaction
		try {
			conn = DriverManager.getConnection(jdbcUrl, userID, userPW);
			conn.setAutoCommit(false);
			System.out.println("Connection AutoCommit false");
			
			try{
				sql= "UPDATE book_inventory SET num_inventory = num_inventory -1 WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 101);
				pstmt.execute();
				stmt = conn.createStatement();

				sql= "SELECT * FROM book_inventory WHERE id = 101";	
				rs= stmt.executeQuery(sql);	
				if(rs.next()) {
					int num = rs.getInt(1) ; // id
					String title = rs.getString("title");
					String author= rs.getString("author");
					int inventory = rs.getInt("num_inventory");
	
					if( inventory > 0) {
						conn.commit();
						System.out.printf("%d %s %s %d \n", num, title, author, inventory);		
					}
					else{
						conn.rollback();
						System.out.printf("%d %s %s 's inventory is empty.\n", num, title, author);			
					}
				}
				else {
					System.out.printf("no result");	
				}
			} catch(SQLException e) {
				conn.rollback();
				System.err.println("DB Error " + e.getMessage());
				return;
			} finally {
				if(pstmt!=null)
					try { pstmt.close(); }
					catch(final SQLException e) {
					}
				if(rs!=null)
					try { rs.close(); }
					catch(final SQLException e) {
					}
				if(conn !=null)
					try { conn.setAutoCommit(true);}
					catch(final SQLException e) {
					}
				stmt.close();
				conn.close();
			}
		} catch(SQLException e) {
			System.err.println("DB Connection Error " + e.getMessage());
			return;
		}
		
	}

}
