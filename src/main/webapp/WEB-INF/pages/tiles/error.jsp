<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.error.name.return_button"
	var="return_button" />
<fmt:message bundle="${loc}" key="local.error.name.general_message"
	var="general_message" />

<h1>
	<c:out value="${general_message}" />
</h1>

<c:if test="${not (sessionScope.errorMessage eq null)}">
	<font color="blue" size="+3"> <fmt:message bundle="${loc}"
			key="${sessionScope.errorMessage}" var="error_message" /> <c:out
			value="${error_message}" />
	</font>
</c:if>
<c:if test="${not (sessionScope.errorValidationMessage eq null)}">
	<font color="blue" size="+3"> 
	<c:forEach var="error" items="${sessionScope.errorValidationMessage}">
	<fmt:message bundle="${loc}"
			key="${error}" var="error_message" /> <c:out
			value="${error_message}" /><br>
			</c:forEach>
	</font>
</c:if>
<br />
<form action="controller" method="get">
	<input type="hidden" name="command" value="go_to_base_page" /> <input
		type="submit" value="${return_button}" />
</form>

