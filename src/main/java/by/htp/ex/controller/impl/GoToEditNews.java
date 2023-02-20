package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.impl.AccessValidator;
import by.htp.ex.util.validation.impl.AccessValidator.AccessValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToEditNews implements Command {

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String JSP_ID_PARAM = "id";
	private static final String NEWS_PARAM = "news";

	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_ADD_NEWS = "addNews";
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
			News news;
			String id;

			id = request.getParameter(JSP_ID_PARAM);
			try {
				news = newsService.findById(Integer.parseInt(id));
				request.setAttribute(NEWS_PARAM, news);
				request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_ADD_NEWS);
				request.getRequestDispatcher(BASE_LAYOUT_PAGE).include(request, response);
			} catch (ServiceException e) {
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			}
		}
	}
}
