<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %>
<!doctype html>
<html><head><title>Predict</title><link rel="stylesheet" href="../assets/css/styles.css"></head>
<body>
<jsp:include page="/WEB-INF/nav.jspf" />
<div class="container">
  <div class="card">
    <h2>Select Symptoms</h2>
    <c:if test="${not empty error}"><div class="alert">${error}</div></c:if>
    <form method="post" action="${pageContext.request.contextPath}/app/predict">
      <div class="grid" style="grid-template-columns:repeat(2,minmax(0,1fr))">
        <c:forEach items="${symptoms}" var="s">
          <label><input type="checkbox" name="symptoms" value="${s.id}"> ${s.name}</label>
        </c:forEach>
      </div>
      <button class="btn" type="submit" style="margin-top:1rem">Predict</button>
    </form>
  </div>
</div>
</body></html>
