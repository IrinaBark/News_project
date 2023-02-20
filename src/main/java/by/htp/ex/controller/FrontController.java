package by.htp.ex.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider();
	private final static String COMMAND_NAME = "command";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		executeCommand(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		executeCommand(request, response);
	}

	private void executeCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String commandName = request.getParameter(COMMAND_NAME);
			Command command = provider.getCommand(commandName);
			command.execute(request, response);
		} catch (Exception e) {
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}

}
