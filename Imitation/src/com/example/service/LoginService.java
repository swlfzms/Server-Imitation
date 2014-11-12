package com.example.service;

import java.sql.Connection;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class LoginService {

	private String className = RegisterService.class.getName();

	private String username;
	private String password;
	private String ip;
	
	private Connection conn;

	public LoginService(String username, String password, String ip) {
		this.username = username;
		this.password = password;
		this.ip = ip;		 
	}

	public JSONObject login() {
		JSONObject object = new JSONObject();
		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {
			conn = connectionPool.getConnection();
			PreparedStatement preparedStatement;

			preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.LoginService_LoginSQL);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.password);
			ResultSet rs = preparedStatement.executeQuery();

			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);				
				
				object.put("result", true);
				object.put("id", id);
				object.put("message", "登录成功！！！");

				// 更改登录状态和IP
				preparedStatement = (PreparedStatement) conn
						.prepareStatement(SQLStatement.LoginService_LoginStatusLogin);
				preparedStatement.setString(1, username);
				preparedStatement.executeUpdate();
				
				preparedStatement = (PreparedStatement) conn
						.prepareStatement(SQLStatement.LoginService_LoginIP);
				preparedStatement.setString(1, ip);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			} else {
				object.put("result", false);
				object.put("message", "大爷的,用户名或密码错误！！！");
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				object.put("result", false);
				object.put("message", "未知原因错误！！！");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			connectionPool.release(conn);
		}
		return object;
	}
}
