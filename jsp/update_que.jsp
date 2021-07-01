<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="dto.Question"%>
<%@ page import="util.Constant"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Category"%>
<%@ page import="dao.CategoryDao"%>
<!DOCTYPE html>
<html>
	<head>
		<title>質問投稿 | Wisdom - Q＆A Communication</title>
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
			<h1 class="balloon">質問を更新します</h1>
			<%
				@SuppressWarnings("unchecked")
				ArrayList<Category> categories= (ArrayList<Category>) request.getAttribute("categories");
			%>
			<form action="<%=Constant.PATH_WISDOM_CNTL %>" method="post">
				<%
				    Question question = (Question) request.getAttribute(Constant.SCOPE_QUE);
				%>
				<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="<%=Constant.ACTION_QUE_UPDATE%>"> <input
					type="hidden" name="<%=Constant.PARAM_QUE_ID %>" value="<%=question.getId()%>">
				<table class="post">
					<tr>
						<th>タイトル</th>
						<td><input type="text" name="<%=Constant.PARAM_QUE_TITLE %>"
							value="<%=question.getTitle()%>"></td>
					</tr>
					<tr>
						<th>本文</th>
						<td><textarea name="<%=Constant.PARAM_QUE_CONTENT%>"><%=question.getContent()%></textarea></td>
					</tr>
					<tr>
						<th>カテゴリー</th>
						<td class="searchCategory">
							<select name="<%=Constant.PARAM_SEARCH_CATEGORY %>">
							<% for (Category category : categories) {  %>
								<option value="<%=category.getId()%>"><%=category.getName()%></option>
							<% } %>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="投稿する"></td>
					</tr>
				</table>
			</form>
			<a href="<%=Constant.PATH_WISDOM_CNTL%>" class="btn">back</a>
		</article>
		<footer>
			<p class="copyright">
				<small>
					Copyright <script type="text/javascript"> document.write(new Date().getFullYear()); </script> ZENET Co., Ltd. All Rights Reserved.
				</small>
			</p>
		</footer>
	</body>
</html>