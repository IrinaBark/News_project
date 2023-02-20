<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.guestInfo.name.questInfo_title"
	var="quest_title" />
<fmt:message bundle="${loc}" key="local.guestInfo.name.questInfo_news"
	var="quest_news" />
<fmt:message bundle="${loc}"
	key="local.guestInfo.name.questInfo_latest_news"
	var="quest_latest_news" />
<fmt:message bundle="${loc}"
	key="local.guestInfo.name.questInfo_no_news" var="quest_no_news" />

<c:out value="${quest_title }" />

<div class="body-title">
	<a href="controller?command=go_to_news_list"><c:out
			value="${quest_news }" /></a>
	<c:out value="${quest_latest_news }" />
</div>

<c:forEach var="news" items="${requestScope.news}">
	<div class="single-news-wrapper">
		<div class="single-news-header-wrapper">
			<div class="news-title">
				<c:out value="${news.title}" />
			</div>
			<div class="news-date">
				<c:out value="${news.newsDate}" />
			</div>

			<div class="news-content">
				<c:out value="${news.briefNews}" />
			</div>
		</div>
	</div>

</c:forEach>

<div class="no-news">
	<c:if test="${requestScope.news eq null}">
		<c:out value="${quest_no_news}" />
	</c:if>
</div>