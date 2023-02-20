package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.ValidationException;
import by.htp.ex.util.validation.impl.AccessValidator;
import by.htp.ex.util.validation.impl.AccessValidator.AccessValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SaveNews implements Command {

	private final INewsService service = ServiceProvider.getInstance().getNewsService();

	private static final String JSP_TITLE_PARAM = "title";
	private static final String JSP_DATE_PARAM = "date";
	private static final String JSP_BRIEF_PARAM = "brief";
	private static final String JSP_CONTENT_PARAM = "content";

	private static final String ERROR_MESSAGE_PARAM = "errorMessage";
	private static final String USER_INFO_MESSAGE_PARAM = "user_info_message";
	private static final String INFO_MESSAGE_LOCAL_KEY = "local.saveNews.name.saved_successfully";
	private static final String ERROR_VALIDATION_PARAM = "errorValidationMessage";
	private static final String DELIMITER = ";";
	
	private static final String COMMAND_VIEW_NEWS = "controller?command=go_to_view_news&id=";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		AccessValidationBuilder builder = new AccessValidator.AccessValidationBuilder();
		AccessValidator validator = builder.checkPermission(session).validate();

		if (!validator.getErrors().isEmpty()) {
			session.setAttribute(ERROR_MESSAGE_PARAM, validator.buildErrorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			String title;
			String date;
			String brief;
			String content;
			int id;

			title = request.getParameter(JSP_TITLE_PARAM);
			date = request.getParameter(JSP_DATE_PARAM);
			brief = request.getParameter(JSP_BRIEF_PARAM);
			content = request.getParameter(JSP_CONTENT_PARAM);

			try {
				id = service.save(title, date, brief, content);
				request.getSession().setAttribute(USER_INFO_MESSAGE_PARAM, INFO_MESSAGE_LOCAL_KEY);
				response.sendRedirect(COMMAND_VIEW_NEWS + Integer.toString(id));
				
			} catch(ServiceException e) {
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
				
			} catch (ValidationException e) {
				String[] errors = e.getMessage().split(DELIMITER);
				request.getSession().setAttribute(ERROR_VALIDATION_PARAM, errors);
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			}
		} 
	}
}
