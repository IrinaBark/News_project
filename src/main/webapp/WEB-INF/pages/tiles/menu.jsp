<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.menu.name.newsList"
	var="newsList" />
<fmt:message bundle="${loc}" key="local.menu.name.addNews" var="addNews" />


<div class="menu-wrapper">
	<div class="menu-title-wrapper">
		<div class="menu-title">News</div>
	</div>

	<div class="list-menu-invisible-wrapper">
		<div class="list-menu-wrapper" style="float: right;">
			<ul style="list-style-image: url(images/img.jpg); text-align: left;">
				<li style="padding-left: 15px;"><a
					href="controller?command=go_to_news_list"><c:out
							value="${newsList}" /></a><br /></li>

				<c:if test="${sessionScope.role eq 'admin'}">
					<li style="padding-left: 15px;"><a
						href="controller?command=go_to_add_news"><c:out
								value="${addNews}" /> </a> <br /></li>
				</c:if>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<!--  grey free space at the bottom of menu -->
	<div style="height: 25px;"></div>
</div>

