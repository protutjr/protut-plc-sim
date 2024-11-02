package br.com.protut;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.*;

public class GetValuesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setContentType("application/json;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        JSONObject responseJSON = new JSONObject();
        JSONArray coils = new JSONArray(PlcSimulator.getCoils());
        JSONArray discreteInputs = new JSONArray(PlcSimulator.getDiscreteInputs());
        JSONArray holdingRegisters = new JSONArray(PlcSimulator.getHoldingRegisters());
        JSONArray inputRegisters = new JSONArray(PlcSimulator.getInputRegisters());
        
        responseJSON.put("coils", coils);
        responseJSON.put("discreteInputs", discreteInputs);
        responseJSON.put("holdingRegisters", holdingRegisters);
        responseJSON.put("inputRegisters", inputRegisters);

        out.println(responseJSON.toString());
    }

}