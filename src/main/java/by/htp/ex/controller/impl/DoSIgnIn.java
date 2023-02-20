package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.ValidationException;
import by.htp.ex.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSIgnIn implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";

	private static final String GUEST_ROLE_VALUE = "guest";
	private static final String USER_PARAM = "user";
	private static final String ROLE_PARAM = "role";
	private static final String USER_STATUS_ACTIVE = "active";
	private static final String USER_STATUS_NOT_ACTIVE = "not active";

	private static final String AUTHENTIFICATION_ERROR_PARAM = "AuthenticationError";
	private static final String AUTHENTIFICATION_ERROR_LOCAL_KEY = "local.header.name.login_error";
	private static final String ERROR_VALIDATION_PARAM = "errorValidationMessage";
	private static final String DELIMITER = ";";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String COMMAND_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		String login;
		String password;

		login = request.getParameter(JSP_LOGIN_PARAM);
		password = request.getParameter(JSP_PASSWORD_PARAM);

		try {

			String role = service.signIn(login, password);

			if (!role.equals(GUEST_ROLE_VALUE)) {
				session.setAttribute(USER_PARAM, USER_STATUS_ACTIVE);
				session.setAttribute(ROLE_PARAM, role);
				response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);
			} else {
				session.setAttribute(USER_PARAM, USER_STATUS_NOT_ACTIVE);
				session.setAttribute(AUTHENTIFICATION_ERROR_PARAM, AUTHENTIFICATION_ERROR_LOCAL_KEY);
				response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);
			}
		} catch (ServiceException e) {
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} catch (ValidationException e) {
			String[] errors = e.getMessage().split(DELIMITER);
			session.setAttribute(ERROR_VALIDATION_PARAM, errors);
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		}
	}
}
