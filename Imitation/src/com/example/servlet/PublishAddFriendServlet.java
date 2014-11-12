package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;

import com.example.service.PublishAddFriendService;
import com.example.tools.RequestBody;

public class PublishAddFriendServlet extends HttpServlet {

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String jsonStr = RequestBody.readJsonFromRequestBody(request);
		System.out.println(jsonStr);
		try{
			JSONObject receiveObject = new JSONObject(jsonStr);
			//String friendName = receiveObject.getString("friendName");			
			//System.out.println(friendName);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			PrintWriter printwriter = response.getWriter();
			PublishAddFriendService publishAddFriendService = new PublishAddFriendService();			
			JSONObject sendObject = publishAddFriendService.addFriend(receiveObject);			
			printwriter.println(sendObject);
			printwriter.flush();
			printwriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
