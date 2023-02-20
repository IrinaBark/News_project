package by.htp.ex.filter;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PreviousRequestFilter implements Filter {
	
	private static final String REQUEST_BEGINNING = "controller?";
	private static final String PREVIOUS_REQUEST_COOKIE_NAME = "previousRequest";
	private static final String LOCALE_NAME = "local";
	private static final String COMMAND_PARAM = "command";
	private static final String COMMAND_CHANGE_LOCALE = "change_locale";
	private static final String ID_NEWS_PARAM = "idNews";
	private static final String LOCALE_RU = "ru";
	private static final String GET_METHOD_VALUE = "GET";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		StringBuilder previousRequest = new StringBuilder(REQUEST_BEGINNING);
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		Enumeration<String> params = req.getParameterNames();
		
		Cookie previousReq = new Cookie(PREVIOUS_REQUEST_COOKIE_NAME, "");
		Cookie localeCookie = new Cookie(LOCALE_NAME, LOCALE_RU);
		
		String command = req.getParameter(COMMAND_PARAM);

		if (COMMAND_CHANGE_LOCALE.equals(command)) {
			localeCookie.setValue(req.getParameter(LOCALE_NAME));
			resp.addCookie(localeCookie);

		} else if (req.getMethod().equals(GET_METHOD_VALUE)) {

			while (params.hasMoreElements()) {
				
				String name = params.nextElement();
				
				if (name.equals(ID_NEWS_PARAM)) {
					String[] value = request.getParameterValues(name);
					for (String s : value) {
						previousRequest.append(name).append("=").append(s).append("&");
					}
					continue;
				}
				String value = request.getParameter(name);

				if (name.equals(LOCALE_NAME)) {

					req.getSession().setAttribute(LOCALE_NAME, value);
					continue;
				}
				previousRequest.append(name).append("=").append(value).append("&");
			}
			previousRequest.setLength(previousRequest.length() - 1);
			previousReq.setValue(previousRequest.toString());
			resp.addCookie(previousReq);
		}
		chain.doFilter(request, response);
	}
}
