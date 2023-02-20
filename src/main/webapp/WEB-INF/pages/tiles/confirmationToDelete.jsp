<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}"
	key="local.confirmationToDelete.name.warning_message"
	var="warning_message" />
<fmt:message bundle="${loc}"
	key="local.confirmationToDelete.name.yes_button" var="yes_button" />
<fmt:message bundle="${loc}"
	key="local.confirmationToDelete.name.no_button" var="no_button" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h3>
			<c:out value="${warning_message}" />
		</h3>
		<table>
			<tr>
				<td>
					<div class="yes_button">
						<form action="controller" method="post">
							<input type="hidden" name="command" value="delete_news" /> 
							<input type="submit" value="${yes_button}" />
						</form>
					</div>
				</td>
				<td>
					<div class="no_button">
						<form action="controller" method="get">
							<c:if test="${fn:length(sessionScope.idNews) gt 1}">
								<input type="hidden" name="command" value="go_to_news_list" />
								<input type="submit" value="${no_button}" />
							</c:if>
							<c:if test="${fn:length(sessionScope.idNews) eq 1}">
								<input type="hidden" name="command" value="go_to_view_news" />
								<input type="hidden" name="id" value="${sessionScope.idNews[0]}" />
								<input type="submit" value="${no_button}" />
							</c:if>

						</form>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>