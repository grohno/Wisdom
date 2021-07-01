<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="util.Constant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>ユーザ登録 | Wisdom - Q＆A Communication</title>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=2.0,user-scalable=yes">
		<meta charset="utf-8">
		<link href="./jsp/wisdom.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
		<script src="./jsp/js/script.js"></script>
	</head>
	<body id="new_user" class="sign">
	<div id="loading"></div>
		<article>
			<p class="img logo"><img src="./jsp/images/wisdom_logo.png" alt="Wisdom - Q＆A Communication"></p>
			<div class="signbox">
				<h1>新規ユーザ登録</h1>
				<% if (request.getAttribute(Constant.SCOPE_MSG_ERR) != null) { %>
					<p><%= request.getAttribute(Constant.SCOPE_MSG_ERR) %></p>
				<% } %>
				<% if (request.getAttribute(Constant.SCOPE_MSG) != null) { %>
					<p><%= request.getAttribute(Constant.SCOPE_MSG) %></p>
				<% } %>
				<form action="<%=Constant.PATH_USER_CNTL %>" method="post">
					<input type="hidden" name="<%=Constant.PARAM_ACTION%>" value="<%=Constant.ACTION_USER_CREATE%>">
					<table>
						<tr>
							<th>E-mail</th>
							<td><input type="email" name="<%=Constant.PARAM_LOGIN_EMAIL%>"></td>
						</tr>
						<tr>
							<th>ユーザ名</th>
							<td><input type="text" name="<%=Constant.PARAM_USER_NAME%>"></td>
						</tr>
						<tr>
							<th>パスワード</th>
							<td><input type="password" name="<%=Constant.PARAM_LOGIN_PASS%>"></td>
						</tr>
						<tr>
							<td colspan="2"><input class="button" type="submit"
								value="登録する"></td>
						</tr>
					</table>
				</form>
				<p class="rootBtn">
					<a href="<%=Constant.PATH_SESSION_CNTL%>">ホームへ戻る</a>
				</p>
			</div><!-- .signbox end -->
			<p class="copyright">
				<small>
					Copyright <script type="text/javascript"> document.write(new Date().getFullYear()); </script> ZENET Co., Ltd. All Rights Reserved.
				</small>
			</p>
		</article>
	</body>
</html>