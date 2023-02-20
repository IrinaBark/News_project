package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsList implements Command {

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	private static final String NEWS_PARAM = "news";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_VALUE_FOR_NEWS_LIST = "newsList";
	private static final String AUTHENTIFICATION_ERROR_PARAM = "AuthenticationError";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String BASE_LAYOUT_PAGE = "WEB-INF/pages/layouts/baseLayout.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		List<News> newsList;

		try {
			newsList = newsService.list();

			request.setAttribute(NEWS_PARAM, newsList);
			request.setAttribute(PRESENTATION_PARAM, PRESENTATION_VALUE_FOR_NEWS_LIST);
			request.setAttribute(AUTHENTIFICATION_ERROR_PARAM, session.getAttribute(AUTHENTIFICATION_ERROR_PARAM));
			request.getRequestDispatcher(BASE_LAYOUT_PAGE).forward(request, response);

		} catch (ServiceException e) {
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		}
	}
}
