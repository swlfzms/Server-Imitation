package com.example.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class LogoutService {

	
	public void logout(int id){
		
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection conn = connectionPool.getConnection();
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = (PreparedStatement) conn.prepareStatement(SQLStatement.LoginService_LoginStatusLogout);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{			
			connectionPool.release(conn);
		}
	}
}
