package by.htp.ex.controller.impl;

import java.io.IOException;
import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToViewNews implements Command {

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	private static final String JSP_ID_PARAM = "id";

	private static final String NEWS_PARAM = "news";
	private static final String PRESENTATION_PARAM = "presentation";
	private static final String PRESENTATION_PARAM_VALUE_FOR_VIEW_NEWS = "viewNews";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String BASE_LAYOUT_PAGE = "WEB-INF/pages/layouts/baseLayout.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		News news;
		String id;

		id = request.getParameter(JSP_ID_PARAM);
		try {
			news = newsService.findById(Integer.parseInt(id));

			request.setAttribute(NEWS_PARAM, news);
			request.setAttribute(PRESENTATION_PARAM, PRESENTATION_PARAM_VALUE_FOR_VIEW_NEWS);
			request.getRequestDispatcher(BASE_LAYOUT_PAGE).include(request, response);
		} catch (ServiceException e) {
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		}
	}
}
