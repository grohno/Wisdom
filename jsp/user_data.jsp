<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="dto.User"%>
<%@ page import="util.Constant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>ユーザー管理 | Wisdom - Q＆A Communication</title>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=2.0,user-scalable=yes">
		<meta charset="utf-8">
		<link href="./jsp/wisdom.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
		<script src="./jsp/js/script.js"></script>
	</head>
	<body>
	<div id="loading"></div>
		<header>
			<div class="inner">
				<p class="img logo"><a href="<%=Constant.PATH_WISDOM_CNTL%>"><img src="./jsp/images/wisdom_icon.png" alt="Wisdom - Q＆A Communication"></a></p>
				<h1><a href="<%=Constant.PATH_WISDOM_CNTL%>">Wisdom - Q＆A Communication</a></h1>
			</div>
		</header>
		<article>
			<h1 class="balloon">ユーザー管理</h1>
			<% if (request.getAttribute(Constant.SCOPE_MSG_ERR) != null) { %>
				<p><%= request.getAttribute(Constant.SCOPE_MSG_ERR) %></p>
			<% } %>
			<% if (request.getAttribute(Constant.SCOPE_MSG) != null) { %>
				<p><%= request.getAttribute(Constant.SCOPE_MSG) %></p>
			<% } %>

			<% User user = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER); %>

			<form action="<%=Constant.PATH_USER_CNTL%>" method="post">
				<input type="hidden" name="<%=Constant.PARAM_ACTION%>" value="<%=Constant.ACTION_USER_UPDATE%>">
				<input type="hidden" name="<%=Constant.PARAM_USER_ID%>"
				<% if (user.getId() != 0) { %>
				 value="<%= user.getId() %>"
				<% } %>
				 >
				<table class="post userData">
					<tr>
						<th>E-mail</th>
						<td><input type="email" name="<%=Constant.PARAM_LOGIN_EMAIL%>"
						<% if (user.getEmail() != null) { %>
						 value="<%= user.getEmail() %>"
						<% } %>
						 required></td>
					</tr>
					<tr>
						<th>ユーザ名</th>
						<td><input type="text" name="<%=Constant.PARAM_USER_NAME%>"
						<% if (user.getName() != null) { %>
						 value="<%= user.getName() %>"
						<% } %>
						 required></td>
					</tr>
					<tr>
						<td colspan="2"><input class="button" type="submit"
							value="更新する"></td>
					</tr>
				</table>
			</form>

			<form action="<%=Constant.PATH_USER_CNTL%>" method="post">
				<input type="hidden" name="<%=Constant.PARAM_ACTION%>" value="<%=Constant.ACTION_USER_UPDATE_PASSWORD%>">
				<input type="hidden" name="<%=Constant.PARAM_USER_ID%>"
				<% if (user.getId() != 0) { %>
				 value="<%= user.getId() %>"
				<% } %>
				 >
				<table class="post userData">
					<tr>
						<th>現在のパスワード</th>
						<td><input type="password" name="<%=Constant.PARAM_OLD_LOGIN_PASS%>" required placeholder="現在のパスワードを入力"></td>
					</tr>
					<tr>
						<th>新しいパスワード</th>
						<td><input type="password" name="<%=Constant.PARAM_LOGIN_PASS%>" required placeholder="新しいパスワードを入力"></td>
					</tr>
					<tr>
						<td colspan="2"><input class="button" type="submit"
							value="更新する"></td>
					</tr>
				</table>
			</form>

			<h2 class="balloon">退会</h2>
			<form action="<%=Constant.PATH_SESSION_CNTL %>" method="post" onSubmit="return resignCheck()" class="resignBtn">
				<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="resign">
				<input type="hidden" name="<%=Constant.PARAM_USER_ID%>"
				<% if (user.getId() != 0) { %>
				 value="<%= user.getId() %>"
				<% } %>
				 >
				<input type="submit" value="ユーザー登録を削除する">
			</form>

			<a href="<%=Constant.PATH_WISDOM_CNTL%>" class="btn">back</a>
		</article>
		<form action="<%=Constant.PATH_SESSION_CNTL %>" method="post">
			<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="logout">
			<input type="submit" value="<%=Constant.ACTION_LOGOUT%>">
		</form>
		<footer>
			<p class="copyright">
				<small>
					Copyright <script type="text/javascript"> document.write(new Date().getFullYear()); </script> ZENET Co., Ltd. All Rights Reserved.
				</small>
			</p>
		</footer>
	</body>
</html>