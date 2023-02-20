package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLocale implements Command {

	private static final String LOCALE_PARAM = "local";
	private static final String PREVIOUS_REQUEST_PARAM = "previousRequest";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		Cookie previousRequestCookie = null;
		Cookie localeCookie = null;

		String locale = request.getParameter(LOCALE_PARAM);

		if (cookies != null) {
			previousRequestCookie = findCookie(cookies, PREVIOUS_REQUEST_PARAM);
			localeCookie = findCookie(cookies, LOCALE_PARAM);
			localeCookie.setValue(locale);
		}

		StringBuilder reqToExecute = new StringBuilder(previousRequestCookie.getValue());
		reqToExecute.append("&").append(LOCALE_PARAM).append("=").append(localeCookie.getValue());

		response.addCookie(localeCookie);
		response.sendRedirect(reqToExecute.toString());

	}

	private static Cookie findCookie(Cookie[] cookies, String cookieName) {
		Cookie cookie = null;
		for (Cookie c : cookies) {
			if (cookieName.equals(c.getName())) {
				cookie = c;
				return cookie;
			}
		}
		return null;
	}
}
