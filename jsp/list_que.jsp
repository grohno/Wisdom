<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.QuestionUserRelation"%>
<%@ page import="dto.QuestionAnswersRelation"%>
<%@ page import="dto.User"%>
<%@ page import="dto.Category"%>
<%@ page import="dao.CategoryDao"%>
<%@ page import="util.Constant"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
	<head>
		<title>質問一覧 | Wisdom - Q＆A Communication</title>
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
			<h1 class="balloon">質問一覧</h1>
			<% @SuppressWarnings("unchecked")
			    ArrayList<QuestionUserRelation> questionUserRelations = (ArrayList<QuestionUserRelation>) request
			            .getAttribute("questionUserRelations");
				@SuppressWarnings("unchecked")
				ArrayList<QuestionAnswersRelation> questionAnswersRelations = (ArrayList<QuestionAnswersRelation>) request
		            .getAttribute("questionAnswersRelation");
				@SuppressWarnings("unchecked")
				ArrayList<Integer> loginUserBookmarks = (ArrayList<Integer>) request.getAttribute("loginUserBookmarks");
				@SuppressWarnings("unchecked")
				ArrayList<Category> categories= (ArrayList<Category>) request.getAttribute("categories");
			%>
			<%
			    User loginUser = (User) session.getAttribute(Constant.SCOPE_LOGIN_USER);
			%>
			<div class="tablewrapper">
			<table class="list">
				<tr>
					<th>#</th>
					<th>タイトル</th>
					<th class="ansNum">回答<br>件数</th>
					<th>本文</th>
					<th>更新日</th>
				<% if (loginUser != null) { %>
					<th class="ansNum">ブック<br>マーク</th>
				<% } %>
					<th>ユーザ</th>
				</tr>
				<%
				    int cnt = 1;
				%>
				<%
				    for (QuestionUserRelation questionUserRelation : questionUserRelations) {
				%>
				<tr>
					<td><%=cnt%></td>
					<td>
						<% CategoryDao categoryDao = new CategoryDao(); %>
						<span class="cate cateId<%=questionUserRelation.getQuestion().getCategoryId() %>"><%=categoryDao.categoryName(questionUserRelation.getQuestion().getCategoryId()) %></span><br>
						<a href="<%=Constant.PATH_DETAIL_QUE_ID%><%=questionUserRelation.getQuestion().getId()%>">
							<%=questionUserRelation.getQuestion().getTitle()%>
						</a>
					</td>
					<td class="ansNum">
					<% for (QuestionAnswersRelation questionAnswersRelation : questionAnswersRelations) { %>
						<% if(questionUserRelation.getQuestion().getId()
						        == questionAnswersRelation.getQuestionUserRelation().getQuestion().getId()) { %>
							<%= questionAnswersRelation.getAnswerUserRelation().size()  %>
						<% } %>
					<% } %>
					</td>
					<td>
						<% if (questionUserRelation.getQuestion().getContent().length() > 30) { %>
						<%=questionUserRelation.getQuestion().getContent().substring(0, 30)%>...
						<% } else { %>
						<%=questionUserRelation.getQuestion().getContent()%>
						<% } %>
					</td>
					<td class="updatedAt"><%=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(questionUserRelation.getQuestion().getUpdatedOn())%></td>
				<% if (loginUser != null) { %>
					<td class="ansNum bookmark
					<% if (loginUserBookmarks.size() != 0) {%>
						<% for (int loginUserBookmark : loginUserBookmarks) {
							if (loginUserBookmark == questionUserRelation.getQuestion().getId()) { %>
								bookmark_on
							<% } %>
						<% } %>
					<% } %>
					 ">
						<form action="<%=Constant.PATH_MYPAGE_CNTL%>" method="post">
							<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="<%=Constant.ACTION_USER_BOOKMARK%>">
							<input type="hidden" name="<%=Constant.PARAM_QUE_ID %>" value="<%=questionUserRelation.getQuestion().getId()%>">
							<button type="submit" name="<%=Constant.ACTION_USER_BOOKMARK%>"><img src="./jsp/images/wisdom_icon_bookmark.png" alt="ブックマークボタン" width="24" height="24"></button>
						</form>
					</td>
				<% } %>
					<td class="userName">
					<% if (loginUser != null) { %>
						<form action="<%=Constant.PATH_MYPAGE_CNTL%>" method="post">
							<input type="hidden" name="<%=Constant.PARAM_USER_ID%>" value="<%=questionUserRelation.getUser().getId()%>">
							<input type="hidden" name="<%=Constant.PARAM_USER_NAME%>" value="<%=questionUserRelation.getUser().getName()%>">
							<input type="submit" value="<%=questionUserRelation.getUser().getName()%>" class="btn">
						</form>
					<% } else { %>
						<span class="btn"><%=questionUserRelation.getUser().getName()%></span>
					<% } %>
					</td>
					<%
					    cnt++;
					%>
				</tr>
				<%
				    }
				%>
			</table>
			</div><!-- .tablewrapper end -->
			<div class="searchBlock">
				<form action="<%=Constant.PATH_WISDOM_CNTL %>" method="get">
					<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="<%=Constant.ACTION_SEARCH%>">
					<p class="searchCategory">
						<label for="searchCategory">カテゴリーで絞り込み</label><br>
						<select name="<%=Constant.PARAM_SEARCH_CATEGORY %>">
						<% for (Category category : categories) {  %>
							<option value="<%=category.getId()%>"><%=category.getName()%></option>
						<% } %>
						</select>
						<input type="submit" value="絞り込み">
					</p>
				</form>
				<form action="<%=Constant.PATH_WISDOM_CNTL %>" method="get">
					<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="<%=Constant.ACTION_SEARCH%>">
					<p class="searchTitle">
						<label for="searchTitle">タイトルで検索！</label><br>
						<input type="text" name="<%=Constant.PARAM_SEARCH_TITLE %>" required placeholder="ここに検索文字を入力">
						<input type="submit" value="検索">
					</p>
				</form>
				<form action="<%=Constant.PATH_WISDOM_CNTL %>" method="get">
					<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="<%=Constant.ACTION_SEARCH%>">
					<p class="searchUpdatedAt">
						<label for="searchUpdatedAt">更新日で検索！</label><br>
						<input type="date" name="<%=Constant.PARAM_SEARCH_UPDATEAT_BETWEEN %>" required>
						～
						<input type="date" name="<%=Constant.PARAM_SEARCH_UPDATEAT_AND %>" required>
						<input type="submit" value="検索">
					</p>
				</form>
			</div><!-- .searchBlock end  -->
		<% if (loginUser != null) { %>
			<a href="WisdomController?action=<%=Constant.ACTION_QUE_NEW%>" class="btn">新規投稿</a>
			<a href="<%=Constant.PATH_USER_CNTL%>?action=<%=Constant.ACTION_USER_EDIT%>" class="btn">ユーザー管理</a>
			<a href="<%=Constant.PATH_MYPAGE_CNTL%>" class="btn">マイページ</a>
		<% } %>
		</article>
	<% if (loginUser != null) { %>
		<form action="<%=Constant.PATH_SESSION_CNTL %>" method="post">
			<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="<%=Constant.ACTION_LOGOUT%>">
			<input type="submit" value="LOGOUT">
		</form>
	<% } else { %>
		<form action="<%=Constant.PATH_SESSION_CNTL %>" method="post">
			<input type="hidden" name="<%=Constant.PARAM_ACTION %>" value="<%=Constant.ACTION_VISITOR_LOGOUT%>">
			<input type="submit" value="GO HOME">
		</form>
	<% } %>
		<footer>
			<p class="copyright">
				<small>
					Copyright <script type="text/javascript"> document.write(new Date().getFullYear()); </script> ZENET Co., Ltd. All Rights Reserved.
				</small>
			</p>
		</footer>
	</body>
</html>