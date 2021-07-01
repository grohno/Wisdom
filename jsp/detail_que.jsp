<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="dto.QuestionAnswersRelation"%>
<%@ page import="dto.AnswerUserRelation"%>
<%@ page import="dto.User"%>
<%@ page import="dao.CategoryDao"%>
<%@ page import="util.Constant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>質問詳細 | Wisdom - Q＆A Communication</title>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=2.0,user-scalable=yes">
		<meta charset="utf-8">
		<link href="./jsp/wisdom.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
		<script src="./jsp/js/script.js"></script>
	</head>
	<body id="detail">
	<div id="loading"></div>
		<header>
			<div class="inner">
				<p class="img logo"><a href="<%=Constant.PATH_WISDOM_CNTL%>"><img src="./jsp/images/wisdom_icon.png" alt="Wisdom - Q＆A Communication"></a></p>
				<h1><a href="<%=Constant.PATH_WISDOM_CNTL%>">Wisdom - Q＆A Communication</a></h1>
			</div>
		</header>
		<article>
			<h1 class="balloon">質問</h1>
			<%
			    QuestionAnswersRelation questionAnswersRelation = (QuestionAnswersRelation) request
			            .getAttribute("questionAnswersRelation");
			%>
			<%
			    User loginUser = (User) session.getAttribute(Constant.SCOPE_LOGIN_USER);
			%>
			<p class="que qa_icon">
				<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getTitle()%>
				<% CategoryDao categoryDao = new CategoryDao(); %>
				<span class="cate cateId<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getCategoryId() %>"><%=categoryDao.categoryName(questionAnswersRelation.getQuestionUserRelation().getQuestion().getCategoryId()) %></span>
				<span class="byname">by <%=questionAnswersRelation.getQuestionUserRelation().getUser().getName()%></span>

			</p>
			<p>
				<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getContent()%>
			</p>
			<h2 class="ans qa_icon">回答</h2>
			<table>
				<%
					int cnt = 1;
				    for (AnswerUserRelation answerUserRelation : questionAnswersRelation.getAnswerUserRelation()) {
				%>
				<tr
				<% if (questionAnswersRelation.getQuestionUserRelation().getQuestion().getBestAnsId() != 0
					 && questionAnswersRelation.getQuestionUserRelation().getQuestion().getBestAnsId() == answerUserRelation.getAnswer().getId()) { %>
				 class="bestAnsTr"
				<% } %>
				>
					<td>
						A<%= cnt %>.
						<% cnt++; %>
						<% if (questionAnswersRelation.getQuestionUserRelation().getQuestion().getBestAnsId() != 0
							 && questionAnswersRelation.getQuestionUserRelation().getQuestion().getBestAnsId() == answerUserRelation.getAnswer().getId()) { %>
						<span class="bestAnsMark">ベストアンサー！</span>
						<% } %>
						<% if (questionAnswersRelation.getQuestionUserRelation().getQuestion().getHelpfulAnsId() != 0 &&
							  questionAnswersRelation.getQuestionUserRelation().getQuestion().getHelpfulAnsId() == answerUserRelation.getAnswer().getId()) { %>
						<span class="helpfulAnsMark">助かった！(<%=answerUserRelation.getAnswer().getHelpfulCount() %>)</span>
						<% } %>
					</td>
					<td>
						<span class="pre"><%=answerUserRelation.getAnswer().getContent()%></span>
						<span class="byname">by <%=answerUserRelation.getUser().getName()%></span>
					<% if (loginUser != null) { %>
						<%  boolean flag = true;
							if (questionAnswersRelation.getQuestionUserRelation().getQuestion().getHelpfulUser() != null && questionAnswersRelation.getQuestionUserRelation().getQuestion().getHelpfulUser().length() != 0) {
						    String[] strHelpfulUsers = questionAnswersRelation.getQuestionUserRelation().getQuestion().getHelpfulUser().split(",", 0);
							    for (String strHelpfulUser: strHelpfulUsers) {
							        int intHelpfulUser = Integer.parseInt(strHelpfulUser);
							        if (intHelpfulUser == loginUser.getId()) {
								        flag = false;
								        continue;
							       	}
							    }
							}
						%>

						<% if (answerUserRelation.getAnswer().getCreateUserId() != loginUser.getId() && flag) { %>
							<form action="<%=Constant.PATH_WISDOM_CNTL %>" method="post" class="helpfulForm" onSubmit="return helpfulCheck()">
								<input type="hidden" name="<%=Constant.PARAM_QUE_ID %>" value="<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getId()%>">
								<input type="hidden" name="<%=Constant.PARAM_ANS_ID %>" value="<%=answerUserRelation.getAnswer().getId()%>">
								<input type="hidden" name="<%=Constant.PARAM_ANS_HELPFUL_COUNT %>" value="<%=answerUserRelation.getAnswer().getHelpfulCount()%>">
								<input type="hidden" name="<%=Constant.PARAM_ANS_HELPFUL_USER %>" value="<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getHelpfulUser()%>">
								<input type="hidden" name="<%=Constant.PARAM_USER_ID %>" value="<%=loginUser.getId()%>">
								<button class="helpful" type="submit" name="<%=Constant.PARAM_ACTION%>" value="<%=Constant.ACTION_HELPFUL%>">助かった！と思ったらクリック</button>
							</form>
						<% } %>

						<% if (questionAnswersRelation.getQuestionUserRelation().getQuestion().getBestAnsId() == 0 && questionAnswersRelation.getQuestionUserRelation().getQuestion().getCreateUserId() == loginUser.getId()) { %>
						<form action="<%=Constant.PATH_WISDOM_CNTL %>" method="post" class="bestAnsForm" onSubmit="return bestAnsCheck()">
							<input type="hidden" name="<%=Constant.PARAM_QUE_ID %>" value="<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getId()%>">
							<input type="hidden" name="<%=Constant.PARAM_ANS_ID %>" value="<%=answerUserRelation.getAnswer().getId()%>">
							<button type="submit" name="<%=Constant.PARAM_ACTION%>" value="<%=Constant.ACTION_BEST_ANS%>">この回答をベストアンサーにする</button>
						</form>
						<% } %>
					<% } %>
					</td>
				</tr>
				<%
				    }
				%>
			</table>
		<% if (loginUser != null) { %>
			<%
			    if (loginUser.getId() != questionAnswersRelation.getQuestionUserRelation().getUser().getId()) {
			%>
			<form action="<%=Constant.PATH_WISDOM_CNTL %>" method="post">
				<textarea name="<%=Constant.PARAM_ANS_CONTENT%>" required></textarea><br>
				<input class="button" type="submit" value="回答する">
				<input type="hidden" name="<%=Constant.PARAM_ACTION%>" value="<%=Constant.ACTION_ANS_CREATE%>">
				<input type="hidden" name="<%=Constant.PARAM_QUE_ID %>" value="<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getId()%>">
			</form>
			<%
	     		} else if (loginUser.getId() == questionAnswersRelation.getQuestionUserRelation().getQuestion().getCreateUserId()) {
	 		%>
			<a href="WisdomController?action=editQue&que_id=<%=questionAnswersRelation.getQuestionUserRelation().getQuestion().getId()%>" class="btn">編集</a>
			<%
			    }
			%>
		<% } %>
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