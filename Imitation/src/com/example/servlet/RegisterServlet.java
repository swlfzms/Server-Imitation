package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.example.service.RegisterService;
import com.example.tools.RequestBody;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(request, response);
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);

		// 获取客户端发过来的数据
		String jsonStr = RequestBody.readJsonFromRequestBody(request);
		System.out.println(jsonStr);

		try {
			JSONObject object = new JSONObject(jsonStr);
			String password = object.getString("password");
			String username = object.getString("username");
			String email = object.getString("email");
			System.out.println(username + " " + password + " " + email);

			
			//返回数据
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");			
						
			RegisterService registerService = new RegisterService(username,
					password, email);
			JSONObject jsonObject = registerService.register();
			
			PrintWriter out = response.getWriter();
			out.println(jsonObject);
			out.flush();
			out.close();
			System.out.println(jsonObject.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
