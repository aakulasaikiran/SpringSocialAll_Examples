package com.javapapers.java.social.facebook;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String code = "";

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		code = req.getParameter("code");
		HttpSession session=req.getSession();

		if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);
		String access = "";
		try {
			JSONObject json = new JSONObject(accessToken);
			access = json.get("access_token").toString();
			fbConnection.getFriendList(access);
			session.setAttribute("accessT", accessToken);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		FBGraph fbGraph = new FBGraph(access);
		String graph = fbGraph.getFBGraph();
		FBConnection connection=new FBConnection();
		//connection.getFriendList();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		ServletOutputStream out = res.getOutputStream();
		out.println("<h1>Facebook Login using Java</h1>");
		out.println("<h2>Application Main Menu</h2>");
		out.println("<div>Id " + fbProfileData.get("id"));
		out.println("<div>Welcome " + fbProfileData.get("name"));
		out.println("<div>Your Email: " + fbProfileData.get("email"));
		out.println("<div>You are " + fbProfileData.get("gender"));
		out.println("<div>You are First_Name : " + fbProfileData.get("first_name"));
		out.println("<div>You are last_name : " + fbProfileData.get("last_name"));
		out.println("<div>You are short_name : " + fbProfileData.get("short_name"));
		out.println("<div>You are name_format : " + fbProfileData.get("name_format"));
		
		out.print("<a href='<%=connection.getFriendList();%>'>friendslist</a>");
	}

}
