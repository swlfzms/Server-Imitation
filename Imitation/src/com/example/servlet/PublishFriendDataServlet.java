package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;

import com.example.service.PublishFriendDataService;
import com.example.tools.RequestBody;

public class PublishFriendDataServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		String jsonStr = RequestBody.readJsonFromRequestBody(request);
		System.out.println(jsonStr);
		try{
			JSONObject receiveObject = new JSONObject(jsonStr);
			//String friendName = receiveObject.getString("friendName");			
			//System.out.println(friendName);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			PrintWriter printwriter = response.getWriter();
			PublishFriendDataService publishFriendDataService = new PublishFriendDataService();			
			JSONObject sendObject = publishFriendDataService.getData(receiveObject);			
			printwriter.println(sendObject);
			printwriter.flush();
			printwriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
