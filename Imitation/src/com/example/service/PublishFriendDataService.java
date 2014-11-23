package com.example.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class PublishFriendDataService {

	private Connection conn;

	public JSONObject getData(JSONObject receiveObject) {

		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {
			conn = connectionPool.getConnection();

			JSONArray friendUid = (JSONArray) receiveObject.get("friendUid");
			JSONArray friendsignatureversion = (JSONArray) receiveObject
					.get("friendsignatureversion");
			JSONArray friendheadphotoversion = (JSONArray) receiveObject
					.get("friendheadphotoversion");

			String condition = uidToString(friendUid);
			PreparedStatement preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.PublishFriendDataService_searchVersion);
			System.out.println("condition: " + condition + ", sql: "
					+ SQLStatement.PublishFriendDataService_searchVersion);
			preparedStatement.setString(1, condition);

			JSONArray changedSignatureId = new JSONArray();
			JSONArray changedSignatureContent = new JSONArray();
			JSONArray changedHeadphotoId = new JSONArray();
			JSONArray changedphototype = new JSONArray();

			ResultSet rs = preparedStatement.executeQuery();
			String signature = "";
			int signatureversion = 0;
			int headphotoversion = 0;
			String phototype = "";
			int i = 0;
			while (rs.next()) {
				signature = rs.getString("signature");
				signatureversion = rs.getInt("signatureversion");
				headphotoversion = rs.getInt("headphotoversion");
				phototype = rs.getString("phototype");
				if (signatureversion != friendsignatureversion.getInt(i)) {
					changedSignatureId.put(i); // uid
					changedSignatureContent.put(signature); // signature
				}
				if (headphotoversion != friendheadphotoversion.getInt(i)) { // uid
					changedHeadphotoId.put(i);
					changedphototype.put(phototype);
				}
				i++;
			}
			JSONObject sendObject = new JSONObject();
			sendObject.put("changedSignatureId", changedSignatureId);
			sendObject.put("changedSignatureContent", changedSignatureContent);
			sendObject.put("changedHeadphotoId", changedHeadphotoId);
			sendObject.put("changedphototype", changedphototype);
			return sendObject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionPool.release(conn);
		}
		return null;
	}

	private String uidToString(JSONArray uidArray) {
		// Obejct uid =

		StringBuilder stringBuilder = new StringBuilder();
		try {
			for (int i = 0; i < uidArray.length() - 1; i++) {
				stringBuilder.append(uidArray.get(i) + ",");
			}
			stringBuilder.append(uidArray.get(uidArray.length() - 1));
			System.out.println(stringBuilder.toString());
			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
