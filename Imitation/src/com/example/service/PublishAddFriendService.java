package com.example.service;

import java.sql.Connection;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONObject;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class PublishAddFriendService {

	private Connection conn;

	// String friendName = receiveObject.getString("friendName");
	public JSONObject addFriend(JSONObject receiveObject) {
		JSONObject sendObject = new JSONObject();
		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {					
			
			conn = connectionPool.getConnection();
			PreparedStatement preparedStatement;

			preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.PublishAddFriendService_addFriend);
						
			String friendName = receiveObject.getString("friendName");
			preparedStatement.setString(1, friendName);
			
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				sendObject.put("result", true);
				sendObject.put("message", "添加成功");
				int id = rs.getInt("id");
				int status = rs.getInt("status");
				String signature = rs.getString("signature");
				String ip = rs.getString("ip");
				sendObject.put("id", id);
				sendObject.put("status", status);
				sendObject.put("signature", signature);
				sendObject.put("ip", ip);
			}else{
				sendObject.put("result", false);
				sendObject.put("message", "那家伙不存在啊！！！");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			try{
				sendObject.put("result", false);
				sendObject.put("message", e.getMessage());
			}catch(Exception ex){				
			}
		} finally {
			connectionPool.release(conn);
		}
		return sendObject;

	}
}
