package by.htp.ex.controller.impl;

import java.io.IOException;
import by.htp.ex.controller.Command;
import by.htp.ex.util.validation.impl.AccessValidator;
import by.htp.ex.util.validation.impl.AccessValidator.AccessValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToConfirmationPage implements Command {

	private static final String ERROR_NO_SELECTED_NEWS_LOCAL_KEY = "local.error.name.no_selected_news";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";

	private static final String ID_NEWS_PARAM = "idNews";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_CONFIRMATION_PAGE = "confirmationPage";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String BASE_LAYOUT_PAGE = "WEB-INF/pages/layouts/baseLayout.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		AccessValidationBuilder builder = new AccessValidator.AccessValidationBuilder();
		AccessValidator validator = builder.checkPermission(session).validate();

		if (!validator.getErrors().isEmpty()) {
			session.setAttribute(ERROR_MESSAGE_PARAM, validator.buildErrorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			String[] idNews;
			idNews = request.getParameterValues(ID_NEWS_PARAM);

			if (idNews == null) {
				request.getSession().setAttribute(ERROR_MESSAGE_PARAM, ERROR_NO_SELECTED_NEWS_LOCAL_KEY);
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			} else {
				request.getSession().setAttribute(ID_NEWS_PARAM, idNews);
				request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_CONFIRMATION_PAGE);
				request.getRequestDispatcher(BASE_LAYOUT_PAGE).forward(request, response);
			}
		} 
	}
}
