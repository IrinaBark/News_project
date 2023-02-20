package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToErrorPage implements Command {

	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_ERROR_PAGE = "error";
	private static final String ERROR_VALIDATION_PARAM = "errorValidationMessage";
	private static final String BASE_LAYOUT_PAGE = "WEB-INF/pages/layouts/baseLayout.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String message;
		String[] validationMessage;
		message = (String) request.getSession().getAttribute(ERROR_MESSAGE_PARAM);
		validationMessage = (String[]) request.getSession().getAttribute(ERROR_VALIDATION_PARAM);

		if (message != null) {
			session.setAttribute(ERROR_MESSAGE_PARAM, message);
		}
		if (validationMessage != null) {
			session.setAttribute(ERROR_VALIDATION_PARAM, validationMessage);
		}
		request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_ERROR_PAGE);
		request.getRequestDispatcher(BASE_LAYOUT_PAGE).forward(request, response);
	}
}
