<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.addNews.name.update_button"
	var="update_button" />
<fmt:message bundle="${loc}" key="local.addNews.name.save_button"
	var="save_button" />
<fmt:message bundle="${loc}" key="local.addNews.name.cancel_button"
	var="cancel_button" />
<fmt:message bundle="${loc}" key="local.viewNews.name.title"
	var="news_title" />
<fmt:message bundle="${loc}" key="local.viewNews.name.date" var="date" />
<fmt:message bundle="${loc}" key="local.viewNews.name.brief" var="brief" />
<fmt:message bundle="${loc}" key="local.viewNews.name.content"
	var="news_content" />
<fmt:message bundle="${loc}" key="local.addNews.name.add_title"
	var="add_title" />
<fmt:message bundle="${loc}" key="local.newsList.name.news"
	var="news_link" />

<script type="text/javascript">
	
<%@include file="/script/validation.js"%>
	
</script>

<div class="body-title">
	<a href="controller?command=go_to_news_list"><c:out
			value="${news_link }" /></a>
	<c:out value="${add_title}" />
</div>
<form name="form" action="controller" method="post"
	onsubmit="return validateNewsForm()">
	<div class="add-table-margin">

		<table class="news_text_format">
			<tr>
				<td class="space_around_title_text"><c:out
						value="${news_title }" /></td>
				<td>
					<div class="title_field">
						<input type="text" name="title" value="${requestScope.news.title}"
							class="text_field" required /><br />
					</div>
				</td>

			</tr>
			<tr>
				<td class="space_around_title_text"><c:out value="${date}" /></td>
				<td><input type="date" name="date"
					value="${requestScope.news.newsDate}" class="date_field" required /><br />

				</td>
			</tr>
			<tr>
				<td class="space_around_title_text"><c:out value="${brief}" /></td>
				<td>
					<div class="brief_field_format">
						<input type="text" name="brief"
							value="${requestScope.news.briefNews}" class="brief_field"
							required /><br />
					</div>
				</td>
			</tr>
			<tr>
				<td class="space_around_title_text"><c:out
						value="${news_content}" /></td>
				<td>
					<div class="content_field_format">
						<input type="text" name="content"
							value="${requestScope.news.content}" class="content_field"
							required /><br />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="button_field">
						<c:choose>
							<c:when test="${not (requestScope.news eq null)}">
								<input type="hidden" name="command" value="update_news" />
								<input type="hidden" name="local" value="${sessionScope.local}" />
								<input type="hidden" name="idNews"
									value="${requestScope.news.idNews}" />
								<input type="submit" value="${update_button}" />
							</c:when>
							<c:otherwise>
								<input type="hidden" name="command" value="save_news" />
								<input type="hidden" name="local" value="${sessionScope.local}" />
								<input type="submit" value="${save_button}" />
							</c:otherwise>
						</c:choose>
					</div>
				</td>

			</tr>
		</table>
	</div>
</form>

<table>
	<tr>
		<td><c:choose>
				<c:when test="${not (requestScope.news eq null)}">
					<form action="controller" method="post">
						<input type="hidden" name="command" value="go_to_view_news" /> <input
							type="hidden" name="id" value="${requestScope.news.idNews}" /> <input
							type="submit" value="${cancel_button}" />
					</form>
				</c:when>
				<c:otherwise>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="go_to_news_list" /> <input
							type="submit" value="${cancel_button}" />
					</form>
				</c:otherwise>
			</c:choose></td>
	</tr>
</table>