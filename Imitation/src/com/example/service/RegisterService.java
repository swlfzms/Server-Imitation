package com.example.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONObject;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class RegisterService {

	private String className = RegisterService.class.getName();

	private int id;
	private String username;
	private String password;
	private String email;
	private String ip;

	private Connection conn;

	public RegisterService(String username, String password, String email,
			String ip) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.ip = ip;
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
			System.out
					.println(SQLStatement.RegisterService_UserIsExistOrNotSQL);
			preparedStatement.setString(1, this.username);
			ResultSet rs = preparedStatement.executeQuery();

			int count = 2;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 0) { // 用户不存在
				preparedStatement = (PreparedStatement) conn
						.prepareStatement(SQLStatement.RegisterService_RegisterSQL);
				preparedStatement.setString(1, this.username);
				preparedStatement.setString(2, this.password);
				preparedStatement.setString(3, this.email);
				preparedStatement.setString(4, this.ip);
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

				preparedStatement = (PreparedStatement) conn
						.prepareStatement(SQLStatement.RegisterService_UserID);
				preparedStatement.setString(1, this.username);
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					id = rs.getInt("ID");
					CopyImage copyImage = new CopyImage();
					copyImage.start();
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
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();

			// 出错后装填
			try {
				object.put("result", false);
				object.put("message",
						SQLStatement.RegisterService_UnknowException);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally { // 返回连接池
			connectionPool.release(conn);
		}
		return object;
	}

	class CopyImage extends Thread {

		@Override
		public void run() {						
			File file = new File("F:\\Tomcat 7.0\\webapps\\Imitation\\headphoto\\0.png");
			File copyFile = new File("F:\\Tomcat 7.0\\webapps\\Imitation\\headphoto\\" + id + ".png");
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				FileOutputStream fileOutputStream = new FileOutputStream(
						copyFile);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						fileInputStream);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
						fileOutputStream);
				
				int length;
				byte[] buf = new byte[1024];
				while ((length = bufferedInputStream.read(buf)) != -1) {
					bufferedOutputStream.write(buf, 0, length);
					bufferedOutputStream.flush();
				}
				
				fileInputStream.close();
				fileOutputStream.close();
				bufferedInputStream.close();
				bufferedOutputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
