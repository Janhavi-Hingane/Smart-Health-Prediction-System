<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %>
<!doctype html>
<html><head><title>Result</title><link rel="stylesheet" href="../assets/css/styles.css"></head>
<body>
<jsp:include page="/WEB-INF/nav.jspf" />
<div class="container">
  <div class="card">
    <h2>Prediction Result</h2>
    <c:choose>
      <c:when test="${empty predictionId}">
        <div class="alert">No strong match found. Please select more symptoms or consult a doctor.</div>
      </c:when>
      <c:otherwise>
        <p>Most likely disease ID: <strong>${predictionId}</strong> (demo shows ID — map to disease name on enhancements)</p>
        <p>Matched symptoms: ${maxMatch}</p>
      </c:otherwise>
    </c:choose>
    <a class="btn" href="${pageContext.request.contextPath}/app/predict" style="margin-top:1rem">Try Again</a>
  </div>
</div>
</body></html>
