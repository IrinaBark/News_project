package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.util.validation.impl.AccessValidator;
import by.htp.ex.util.validation.impl.AccessValidator.AccessValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToRegistrationPageCommand implements Command {

	private static final String HAVE_ALREADY_AUTHORIZED_ERROR_LOCAL_KEY = "local.registration.name.error_is_authorized";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_PARAM_VALUE_FOR_REGISTRATION = "registration";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String BASE_LAYOUT_PAGE = "WEB-INF/pages/layouts/baseLayout.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		AccessValidationBuilder builder = new AccessValidator.AccessValidationBuilder();
		
		AccessValidator validator = builder.checkAuthorization(session).validate();
		
		if (validator.getErrors().isEmpty()) {
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, HAVE_ALREADY_AUTHORIZED_ERROR_LOCAL_KEY);
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			request.setAttribute(PRESENTATION_PARAM, PRESENTATION_PARAM_VALUE_FOR_REGISTRATION);
			request.getRequestDispatcher(BASE_LAYOUT_PAGE).forward(request, response);
		}
	}
}
