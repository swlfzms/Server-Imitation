package com.example.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONObject;

import com.example.database.ConnectionPool;
import com.example.tools.SQLStatement;
import com.mysql.jdbc.PreparedStatement;

public class PublishHeadPhotoService {

	private Connection conn;
	private String prePath;
	public PublishHeadPhotoService(String prePath) {
		// TODO Auto-generated constructor stub
		this.prePath = prePath;
	}
	
	public byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	private byte charToByte(char c) {
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	} 
	
	public JSONObject getResult(JSONObject receiveObject){
		JSONObject sendObject = new JSONObject();
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			conn = connectionPool.getConnection();
			PreparedStatement preparedStatement;
			
			int id = receiveObject.getInt("id");
			int length = receiveObject.getInt("length");
			String type = receiveObject.getString("type");
			String image = receiveObject.getString("image"); //十六进制					
			
			preparedStatement = (PreparedStatement) conn
					.prepareStatement(SQLStatement.PublishHeadPhotoService_checkheadphotoversion);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				int version = rs.getInt("headphotoversion")+1;
				preparedStatement = (PreparedStatement) conn
						.prepareStatement(SQLStatement.PublishHeadPhotoService_updateheadphotoversion);				
				preparedStatement.setInt(1, version);
				preparedStatement.setString(2, type);
				preparedStatement.setInt(3, id);
							
				int result = preparedStatement.executeUpdate();
				if(result == 1){						
					byte[] headphoto = hexStringToBytes(image);
					String headPhotoSavePath = prePath+SQLStatement.PublishHeadPhotoService_savePath;
					String fileNameJPG = headPhotoSavePath+id+".jpg";
					String fileNamePNG = headPhotoSavePath+id+".png";
					
					
					File file = new File(fileNameJPG);					
					if (file.exists()) {
						file.delete();
					} else {
						file = new File(fileNamePNG);
						if (file.exists()) {
							file.delete();
						}
					}
					file = new File(headPhotoSavePath+id+"."+type);
					
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
					bufferedOutputStream.write(headphoto, 0, headphoto.length);
					bufferedOutputStream.close();
					sendObject.put("result", true);
					sendObject.put("message", "头像更换成功");
				}else{
					sendObject.put("result", false);
					sendObject.put("message", "头像更换失败");					
				}
			}
			return sendObject;
		} catch (Exception e) {
			// TODO: handle exception
			try {
				e.printStackTrace();
				sendObject.put("result", false);
				sendObject.put("message", "头像更换失败: "+e.getMessage());
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}finally{
			connectionPool.release(conn);
		}
		return sendObject;
	}
}
