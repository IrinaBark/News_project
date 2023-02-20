<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.header.name.newsTitle"
	var="newsTitle" />
<fmt:message bundle="${loc}" key="local.header.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.header.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.header.name.login" var="login" />
<fmt:message bundle="${loc}" key="local.header.name.password"
	var="password" />
<fmt:message bundle="${loc}" key="local.header.name.registration"
	var="registration" />
<fmt:message bundle="${loc}" key="local.header.name.signIn" var="signIn" />
<fmt:message bundle="${loc}" key="local.header.name.signOut"
	var="signOut" />

<div class="wrapper">
	<div class="newstitle">
		<c:out value="${newsTitle}" />
	</div>

	<div class="local-link">

		<div align="right">

			<a href="controller?command=change_locale&local=en"> <c:out
					value="${en_button}" /></a> &nbsp;&nbsp; <a
				href="controller?command=change_locale&local=ru"> <c:out
					value="${ru_button}" />
			</a><br />

		</div>

		<c:if test="${not (sessionScope.user eq 'active')}">

			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_sign_in" />
					<c:out value="${login}" />
					<input type="text" name="login" value="" /><br />
					<c:out value="${password}" />
					<input type="password" name="password" value="" /><br />

					<c:if test="${not (sessionScope.info_message eq null)}">
						<font color="blue" size="+2"> <fmt:message bundle="${loc}"
								key="${sessionScope.info_message}" var="info_message" /> <c:out
								value="${info_message}" />
						</font>
					</c:if>

					<c:if test="${not (requestScope.AuthenticationError eq null)}">
						<font color="red"> <fmt:message bundle="${loc}"
								key="${sessionScope.AuthenticationError}" var="login_error" />
							<c:out value="${login_error}" />
						</font>
					</c:if>
					<a href="controller?command=go_to_registration_page"><c:out
							value="${registration}" /></a> <input type="submit"
						value="${signIn}" /><br />
				</form>
			</div>
		</c:if>
		<c:if test="${sessionScope.user eq 'active'}">
			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_sign_out" /> <input
						type="submit" value="${signOut}" /><br />

					<c:if test="${not (sessionScope.user_info_message eq null)}">
						<font color="blue" size="+2"> <fmt:message bundle="${loc}"
								key="${sessionScope.user_info_message}" var="user_message" /> <c:out
								value="${ user_message}" />
						</font>
						<c:set var="user_info_message" scope="session" value="${local.message.name.emptyMessage}" />
					</c:if>
				</form>
			</div>
		</c:if>
	</div>
</div>
