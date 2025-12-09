<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head><title>Register</title><link rel="stylesheet" href="assets/css/styles.css"></head>
<body>
<div class="nav"><a class="brand" href="${pageContext.request.contextPath}/">Smart Health</a></div>
<div class="container">
  <div class="card" style="max-width:520px;margin:auto">
    <h2>Create Account</h2>
    <c:if test="${not empty error}"><div class="alert">${error}</div></c:if>
    <form method="post" action="${pageContext.request.contextPath}/auth/register">
      <label>Name</label><input class="input" type="text" name="name" required>
      <label>Email</label><input class="input" type="email" name="email" required>
      <label>Password</label><input class="input" type="password" name="password" required minlength="6">
      <button class="btn" type="submit" style="margin-top:1rem">Register</button>
    </form>
  </div>
</div>
</body></html>
