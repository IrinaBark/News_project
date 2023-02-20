package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.util.validation.impl.AccessValidator;
import by.htp.ex.util.validation.impl.AccessValidator.AccessValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignOut implements Command {

	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String USER_PARAM = "user";
	private static final String USER_NOT_ACTIVE_VALUE = "not active";
	private static final String USER_INFO_PARAM = "user_info_message";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String COMMAND_GO_TO_BASE_PAGE = "controller?command=go_to_base_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		AccessValidationBuilder builder = new AccessValidator.AccessValidationBuilder();
		AccessValidator validator = builder.checkAuthorization(session).validate();

		if (!validator.getErrors().isEmpty()) {
			request.getSession().setAttribute(ERROR_MESSAGE_PARAM, validator.buildErrorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			request.getSession(true).setAttribute(USER_PARAM, USER_NOT_ACTIVE_VALUE);
			request.setAttribute(USER_INFO_PARAM, null);
			response.sendRedirect(COMMAND_GO_TO_BASE_PAGE);
		}
	}
}
