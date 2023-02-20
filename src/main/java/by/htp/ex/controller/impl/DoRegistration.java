package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.controller.Command;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoRegistration implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String SUCCESSFUL_REGISTRATION_LOCAL_KEY = "local.header.name.successful_registration";
	private static final String FAILED_REGISTRATION_LOCAL_KEY = "local.header.name.failed_registration";
	
	private static final String JSP_NAME_PARAM = "firstName";
	private static final String JSP_SURMANE_PARAM = "lastName";
	private static final String JSP_EMAIL_PARAM = "email";
	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	private static final String JSP_BIRTHDAY_PARAM = "birthday";
	
	//private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String INFO_MESSAGE_PARAM = "info_message";
	private static final String ERROR_VALIDATION_PARAM = "errorValidationMessage";
	private static final String DELIMITER = ";";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String COMMAND_GO_TO_BASE_PAGE = "controller?command=go_to_base_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);

		String name;
		String surname;
		String email;
		String login;
		String password;
		String birthday;

		boolean registeredSuccessfully = false;

		name = request.getParameter(JSP_NAME_PARAM);
		surname = request.getParameter(JSP_SURMANE_PARAM);
		email = request.getParameter(JSP_EMAIL_PARAM);
		login = request.getParameter(JSP_LOGIN_PARAM);
		password = request.getParameter(JSP_PASSWORD_PARAM);
		birthday = request.getParameter(JSP_BIRTHDAY_PARAM);
		

		NewUserInfo newUserInfo = new NewUserInfo(name, surname, email, login, password, birthday);

		try {
			registeredSuccessfully = service.registration(newUserInfo);
			
			if (registeredSuccessfully) {
				session.setAttribute(INFO_MESSAGE_PARAM, SUCCESSFUL_REGISTRATION_LOCAL_KEY);
				response.sendRedirect(COMMAND_GO_TO_BASE_PAGE);
			} else {
				session.setAttribute(INFO_MESSAGE_PARAM, FAILED_REGISTRATION_LOCAL_KEY);
				response.sendRedirect(COMMAND_GO_TO_BASE_PAGE);
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
