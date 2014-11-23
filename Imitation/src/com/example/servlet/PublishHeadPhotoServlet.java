package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;

import com.example.service.PublishHeadPhotoService;
import com.example.tools.RequestBody;

public class PublishHeadPhotoServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		String jsonStr = RequestBody.readJsonFromRequestBody(request);
		System.out.println(jsonStr);
		
		String path = getServletContext().getRealPath("/");
		System.out.println(path);
		try{
			JSONObject receiveObject = new JSONObject(jsonStr);			
			
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			PrintWriter printwriter = response.getWriter();
			PublishHeadPhotoService publishHeadPhotoService = new PublishHeadPhotoService(path);			
			JSONObject sendObject = publishHeadPhotoService.getResult(receiveObject);
			
			printwriter.println(sendObject);
			printwriter.flush();
			printwriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
