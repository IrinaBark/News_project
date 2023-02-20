<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.registration.name.title"
	var="title" />
<fmt:message bundle="${loc}" key="local.registration.name.first_name"
	var="first_name" />
<fmt:message bundle="${loc}" key="local.registration.name.last_name"
	var="last_name" />
<fmt:message bundle="${loc}" key="local.registration.name.login"
	var="login" />
<fmt:message bundle="${loc}" key="local.registration.name.password"
	var="password" />
<fmt:message bundle="${loc}"
	key="local.registration.name.repeat_password" var="repeat_password" />
<fmt:message bundle="${loc}"
	key="local.registration.name.registration_button" var="reg_button" />
<fmt:message bundle="${loc}" key="local.registration.name.email"
	var="email" />
	<fmt:message bundle="${loc}" key="local.registration.name.birthday"
	var="birthday" />
<link rel="stylesheet" href="/styles/registrationForm.css">
<script type="text/javascript">
	
<%@include file="/script/validation.js"%>

</script>

<div class="wrapper">

	<div class="registration_form">

		<div class="title">
			<c:out value="${title}" />
		</div>


		<form name="form" action="controller" method="post"
			onsubmit="return validateRegistration()">
			<div class="form_wrap">
				<div class="input_grp">


					<div class="input_wrap">
						<label for="fname"><c:out value="${first_name}" /></label> <input
							type="text" id="firstName" name="firstName">
					</div>


					<div class="input_wrap">
						<label for="lastname"><c:out value="${last_name}" /></label> <input
							type="text" id="lastName" name="lastName">
					</div>
					<div class="input_wrap">
						<label for="birthday"><c:out value="${birthday}" /></label> <input
							type="text" id="birthday" name="birthday" value = "yyyy-MM-dd">
					</div>
				</div>


				<div class="input_wrap">
					<label for="email"><c:out value="${email}" /></label> <input
						type="text" id="email" name="email">
				</div>

				<div class="input_wrap">
					<label for="login"><c:out value="${login}" /></label> <input
						type="text" id="login" name="login">
				</div>
				<div class="input_wrap">
					<label for="password"><c:out value="${password}" /></label> <input
						type="text" id="password" name="password">
				</div>
				<div class="input_wrap">
					<label for="repeatPassword"><c:out
							value="${repeat_password}" /></label> <input type="text"
						id="repeatPassword" name="repeatPassword">
				</div>


				<div class="input_wrap">
					<input type="hidden" name="command" value="do_registration" /> <input
						type="submit" value="${reg_button}" class="submit_btn">
				</div>

			</div>
		</form>
	</div>
</div>
