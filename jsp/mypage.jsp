<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Question"%>
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
			<%
			    User loginUser = (User) session.getAttribute(Constant.SCOPE_LOGIN_USER);
			    User mypageUser = (User) request.getAttribute(Constant.SCOPE_MYPAGE_USER);
			%>

			<% if (mypageUser != null && mypageUser.getId() != loginUser.getId()) {  %>
			<h1 class="balloon"><%=mypageUser.getName()%> の質問一覧（最大10件）</h1>
			<% } else { %>
			<h1 class="balloon">自分の質問一覧（最大10件）</h1>
			<% } %>
			<%	@SuppressWarnings("unchecked")
			    ArrayList<Question> myQueList = (ArrayList<Question>) request.getAttribute("myQue");
			%>

			<% if (myQueList.size() > 0) { %>
			<div class="tablewrapper">
			<table class="list">
				<tr>
					<th>#</th>
					<th>タイトル</th>
					<th>本文</th>
					<th>更新日</th>
				</tr>
				<%
				    int cnt = 1;
				%>
				<%
				    for (Question myQue : myQueList) {
				%>
				<tr>
					<td><%=cnt%></td>
					<td>
						<% CategoryDao categoryDao = new CategoryDao(); %>
						<span class="cate cateId<%=myQue.getCategoryId() %>"><%=categoryDao.categoryName(myQue.getCategoryId()) %></span><br>
						<a href="<%=Constant.PATH_DETAIL_QUE_ID%><%=myQue.getId()%>"><%=myQue.getTitle()%></a></td>
					<td>
						<% if (myQue.getContent().length() > 30) { %>
						<%=myQue.getContent().substring(0, 30)%>...
						<% } else { %>
						<%=myQue.getContent()%>
						<% } %>
					</td>
					<td><%=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(myQue.getUpdatedOn())%></td>
					<%
					    cnt++;
					%>
				</tr>
				<%
				    }
				%>
			</table>
			</div><!-- .tablewrapper end -->
			<% } else { %>
			<p class="noque">過去にした質問はありません。</p>
			<% } %>

			<% if (mypageUser != null && mypageUser.getId() != loginUser.getId()) {  %>
			<h1 class="balloon"><%=mypageUser.getName()%> の回答した質問一覧（最大10件）</h1>
			<% } else { %>
			<h1 class="balloon">自分の回答した質問一覧（最大10件）</h1>
			<% } %>

			<%	@SuppressWarnings("unchecked")
			    ArrayList<Question> myAnsQueList = (ArrayList<Question>) request.getAttribute("myAnsQue");
			%>
			<% if (myAnsQueList.size() > 0) { %>
			<div class="tablewrapper">
			<table class="list">
				<tr>
					<th>#</th>
					<th>タイトル</th>
					<th>本文</th>
					<th>更新日</th>
				</tr>
				<%
				    int cnt = 1;
				%>
				<%
				    for (Question myAnsQue : myAnsQueList) {
				%>
				<tr>
					<td><%=cnt%></td>
					<td>
						<% CategoryDao categoryDao = new CategoryDao(); %>
						<span class="cate cateId<%=myAnsQue.getCategoryId() %>"><%=categoryDao.categoryName(myAnsQue.getCategoryId()) %></span><br>
						<a href="<%=Constant.PATH_DETAIL_QUE_ID%><%=myAnsQue.getId()%>"><%=myAnsQue.getTitle()%></a></td>
					<td>
						<% if (myAnsQue.getContent().length() > 30) { %>
						<%=myAnsQue.getContent().substring(0, 30)%>...
						<% } else { %>
						<%=myAnsQue.getContent()%>
						<% } %>
					</td>
					<td><%=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(myAnsQue.getUpdatedOn())%></td>
					<%
					    cnt++;
					%>
				</tr>
				<%
				    }
				%>
			</table>
			</div><!-- .tablewrapper end -->
			<% } else { %>
			<p class="noque">解答した質問はありません。</p>
			<% } %>


			<%	@SuppressWarnings("unchecked")
			    ArrayList<Question> myBookmarkQueList = (ArrayList<Question>) request.getAttribute("myBookmarkQueList");
			%>

			<% if (myBookmarkQueList != null) { %>
			<h1 class="balloon">ブックマーク一覧（最大10件）</h1>
			<div class="tablewrapper">
			<table class="list">
				<tr>
					<th>#</th>
					<th>タイトル</th>
					<th>本文</th>
					<th>更新日</th>
				</tr>
				<%
				    int cnt = 1;
				%>
				<%
				    for (Question myBookmarkQue : myBookmarkQueList) {
				%>
				<tr>
					<td><%=cnt%></td>
					<td>
						<% CategoryDao categoryDao = new CategoryDao(); %>
						<span class="cate cateId<%=myBookmarkQue.getCategoryId() %>"><%=categoryDao.categoryName(myBookmarkQue.getCategoryId()) %></span><br>
						<a href="<%=Constant.PATH_DETAIL_QUE_ID%><%=myBookmarkQue.getId()%>"><%=myBookmarkQue.getTitle()%></a></td>
					<td>
						<% if (myBookmarkQue.getContent().length() > 30) { %>
						<%=myBookmarkQue.getContent().substring(0, 30)%>...
						<% } else { %>
						<%=myBookmarkQue.getContent()%>
						<% } %>
					</td>
					<td><%=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(myBookmarkQue.getUpdatedOn())%></td>
					<%
					    cnt++;
					%>
				</tr>
				<%
				    }
				%>
			</table>
			</div><!-- .tablewrapper end -->
			<% } %>

			<a href=<%=Constant.PATH_WISDOM_CNTL%> class="btn">back</a>
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