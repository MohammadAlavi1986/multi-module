package me.smash.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    PrintWriter out = response.getWriter();
    out.println("SimpleServlet Executed");
    out.flush();
    out.close();
  }
}