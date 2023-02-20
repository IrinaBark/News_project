<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${requestScope.presentation eq 'newsList' }">
	<c:import url="/WEB-INF/pages/tiles/newsList.jsp" />
</c:if>


<c:if test="${requestScope.presentation eq 'viewNews' }">
	<c:import url="/WEB-INF/pages/tiles/viewNews.jsp" />
</c:if>

<c:if test="${requestScope.presentation eq 'addNews' }">
	<c:import url="/WEB-INF/pages/tiles/addNews.jsp" />
</c:if>

<c:if test="${requestScope.presentation eq 'confirmationPage' }">
	<c:import url="/WEB-INF/pages/tiles/confirmationToDelete.jsp" />
</c:if>

<c:if test="${requestScope.presentation eq 'error' }">
	<c:import url="/WEB-INF/pages/tiles/error.jsp" />
</c:if>

<c:if test="${requestScope.presentation eq 'registration' }">
	<c:import url="/WEB-INF/pages/tiles/registration.jsp" />
</c:if>