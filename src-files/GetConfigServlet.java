package br.com.protut;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.*;

public class GetConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setContentType("application/json;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        JSONObject responseJSON = new JSONObject();
                
        responseJSON.put("tcp_port", PlcSimulator.getTcpPort());
        responseJSON.put("slave_id", PlcSimulator.getSlaveId());
        responseJSON.put("use_keep_alive", PlcSimulator.getKeepAlive());

        out.println(responseJSON.toString());
    }

}
