package com.example.service;

import java.sql.Connection;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONObject;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class PublishSignatureService {

	private Connection conn;

	public PublishSignatureService() {

	}

	public JSONObject changeSignature(JSONObject receiveObject) {
		JSONObject sendObject = new JSONObject();
		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {
			conn = connectionPool.getConnection();
			PreparedStatement preparedStatement;

			preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.PublishSignatureService_changeSignature);
			int id = receiveObject.getInt("id");
			String signature = receiveObject.getString("signature");
			preparedStatement.setString(1, signature);
			preparedStatement.setInt(2, id);
			int count = preparedStatement.executeUpdate();
			if (count == 1) {
				sendObject.put("result", true);
				sendObject.put("message", "签名更改成功");
			} else {
				sendObject.put("result", false);
				sendObject.put("message", "不知道哪里出错了");
			}
			preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.PublishSignatureService_checkSignatureVersion);
			preparedStatement.setInt(1, id);
			int version = 0;
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				version = rs.getInt("signatureVersion");
			}
			preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.PublishSignatureService_changeSignatureVersion);
			preparedStatement.setInt(1, version + 1);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.release(conn);
		}
		return sendObject;
	}
}
