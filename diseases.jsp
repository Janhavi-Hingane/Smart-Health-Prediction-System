<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %>
<!doctype html>
<html><head><title>Manage Diseases</title><link rel="stylesheet" href="../assets/css/styles.css"></head>
<body>
<jsp:include page="/WEB-INF/nav.jspf" />
<div class="container">
  <div class="grid" style="grid-template-columns:1fr">
    <div class="card">
      <h2>Add / Update Disease</h2>
      <form method="post" action="${pageContext.request.contextPath}/app/diseases">
        <input type="hidden" name="action" value="create">
        <label>Name</label><input class="input" name="name" required>
        <label>Precaution</label><input class="input" name="precaution">
        <label>Medicine</label><input class="input" name="medicine">
        <label>Symptoms (hold Ctrl/⌘ to multi-select)</label>
        <select multiple size="8" name="symptoms">
          <c:forEach items="${symptoms}" var="s">
            <option value="${s.id}">${s.name}</option>
          </c:forEach>
        </select>
        <button class="btn" type="submit" style="margin-top:1rem">Save</button>
      </form>
    </div>
    <div class="card">
      <h2>Existing Diseases</h2>
      <table class="table">
        <thead><tr><th>ID</th><th>Name</th><th>Precaution</th><th>Medicine</th><th>Action</th></tr></thead>
        <tbody>
          <c:forEach items="${diseases}" var="d">
            <tr>
              <td>${d.id}</td><td>${d.name}</td><td>${d.precaution}</td><td>${d.medicine}</td>
              <td>
                <form method="post" action="${pageContext.request.contextPath}/app/diseases" onsubmit="return confirm('Delete?')">
                  <input type="hidden" name="action" value="delete">
                  <input type="hidden" name="id" value="${d.id}">
                  <button class="btn" type="submit">Delete</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body></html>
