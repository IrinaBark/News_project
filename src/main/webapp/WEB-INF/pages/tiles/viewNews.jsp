<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.viewNews.name.edit_button"
	var="edit_button" />
<fmt:message bundle="${loc}" key="local.viewNews.name.delete_button"
	var="delete_button" />
<fmt:message bundle="${loc}" key="local.viewNews.name.title"
	var="news_title" />
<fmt:message bundle="${loc}" key="local.viewNews.name.date" var="date" />
<fmt:message bundle="${loc}" key="local.viewNews.name.brief" var="brief" />
<fmt:message bundle="${loc}" key="local.viewNews.name.content"
	var="news_content" />
<fmt:message bundle="${loc}" key="local.viewNews.name.page_title"
	var="page_title" />
<fmt:message bundle="${loc}" key="local.newsList.name.news"
	var="news_link" />


<div class="body-title">
	<a href="controller?command=go_to_news_list"><c:out
			value="${news_link }" /></a>
	<c:out value="${page_title }" />
</div>

<div class="add-table-margin">
	<table class="news_text_format">
		<tr>
			<td class="space_around_title_text"><c:out
					value="${news_title }" /></td>

			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${requestScope.news.title }" />
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text"><c:out value="${date }" /></td>

			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${requestScope.news.newsDate }" />
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text"><c:out value="${brief }" /></td>
			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${requestScope.news.briefNews }" />
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text"><c:out
					value="${news_content }" /></td>
			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${requestScope.news.content }" />
				</div></td>
		</tr>
	</table>
</div>


<c:if test="${sessionScope.role eq 'admin'}">
	<div class="first-view-button">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="go_to_edit_news" /> <input
				type="hidden" name="local" value="${sessionScope.local}" /> <input
				type="hidden" name="id" value="${news.idNews}" /> <input
				type="submit" value="${edit_button}" />
		</form>
	</div>

	<div class="second-view-button">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="go_to_confirmation_page" />
			<input type="hidden" name="local" value="${sessionScope.local}" /> <input
				type="hidden" name="idNews" value="${news.idNews}" /> <input
				type="submit" value="${delete_button}" />
		</form>
	</div>

</c:if>