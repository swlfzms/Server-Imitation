package com.example.service;

import java.sql.Connection;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONObject;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class RegisterService {

	private String className = RegisterService.class.getName();

	private String username;
	private String password;
	private String email;

	private Connection conn;

	public RegisterService(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * <p>
	 * <b>用户注册</b>
	 * </p>
	 * 
	 * @return
	 */
	public JSONObject register() {
		JSONObject object = new JSONObject();
		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {
			conn = connectionPool.getConnection();
			PreparedStatement preparedStatement;

			// 查询用户是否存在
			preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.RegisterService_UserIsExistOrNotSQL);
			System.out.println(SQLStatement.RegisterService_UserIsExistOrNotSQL);
			preparedStatement.setString(1, this.username);
			ResultSet rs = preparedStatement.executeQuery();
			
			int count = 2;
			if(rs.next()){
				count = rs.getInt(1);
			}			
			if (count == 0) { // 用户不存在
				preparedStatement = (PreparedStatement) conn
						.prepareStatement(SQLStatement.RegisterService_RegisterSQL);
				preparedStatement.setString(1, this.username);
				preparedStatement.setString(2, this.password);
				preparedStatement.setString(3, this.email);
				
				count = preparedStatement.executeUpdate();

				if (count == 0) {
					object.put("result", false);
					object.put("message",
							SQLStatement.RegisterService_RegisterFailureMessage);
				} else {
					object.put("result", true);
					object.put("message",
							SQLStatement.RegisterService_RegisterSuccessMessage);
				}

			} else if (count == 1) { // 用户存在
				object.put("result", false);
				object.put("message",
						SQLStatement.RegisterService_UserIsExistMessage);
			} else {// 出错了
				object.put("result", false);
				object.put("message",
						SQLStatement.RegisterService_UnknowException);
			}
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();

			//出错后装填
			try {
				object.put("result", false);
				object.put("message",
						SQLStatement.RegisterService_UnknowException);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally { //返回连接池
			connectionPool.release(conn);
		}
		return object;
	}

}
