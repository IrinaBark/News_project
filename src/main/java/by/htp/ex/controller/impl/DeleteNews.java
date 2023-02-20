package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class DeleteNews implements Command {

	private static final String JSP_NEWS_TO_DELETE_PARAM = "idNews";
	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String USER_INFO_MESSAGE_PARAM = "user_info_message";
	private static final String INFO_MESSAGE_LOCAL_KEY = "local.error.name.news_successfully_deleted";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String COMMAND_GO_TO_BASE_PAGE = "controller?command=go_to_base_page";

	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		AccessValidationBuilder builder = new AccessValidator.AccessValidationBuilder();
		AccessValidator validator = builder.checkPermission(session).validate();

		if (!validator.getErrors().isEmpty()) {
			session.setAttribute(ERROR_MESSAGE_PARAM, validator.buildErrorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			String[] id;

			List<String> idNewsToDelete = new ArrayList<String>();
			id = (String[]) session.getAttribute(JSP_NEWS_TO_DELETE_PARAM);

			for (String idOfNews : id) {
				idNewsToDelete.add(idOfNews);
			}

			try {
				newsService.delete(idNewsToDelete);
				session.setAttribute(USER_INFO_MESSAGE_PARAM, INFO_MESSAGE_LOCAL_KEY);
				response.sendRedirect(COMMAND_GO_TO_BASE_PAGE);

			} catch (ServiceException e) {
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			}
		}
	}
}
