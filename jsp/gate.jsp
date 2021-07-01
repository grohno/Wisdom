<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="dto.User"%>
<%@ page import="util.Constant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Wisdom - Q＆A Communication</title>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=2.0,user-scalable=yes">
		<meta charset="utf-8">
		<link href="./jsp/wisdom.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
		<script src="./jsp/js/script.js"></script>
	</head>
	<body id="gate">
	<div id="loading"></div>
		<header>
			<div class="inner">
				<p class="img logo"><a href="<%=Constant.PATH_SESSION_CNTL%>"><img src="./jsp/images/wisdom_icon.png" alt="Wisdom - Q＆A Communication"></a></p>
				<h1><a href="<%=Constant.PATH_SESSION_CNTL%>">Wisdom - Q＆A Communication</a></h1>
			</div>
		</header>
		<article>
			<div class="upper flex column2">
				<div class="left item imgblock">
				</div>
				<div class="right item textblock">
					<div class="innerbox">
						<h2><span class="oswald">Wisdom</span> へようこそ！</h2>
						<p>
							<span class="oswald">Wisdom</span> は、日常のちょっとした疑問から専門的な質問まで、<br class="mini">
							気軽に幅広く質問・回答ができる、<br class="mini">
							ナレッジコミュニケーションサービスです。<br>
							<br>
							あなたも、<span class="oswald">Wisdom</span> で分からないことを尋ねてみたり、<br class="mini">
							誰かの疑問を解決したりしてみませんか？
						</p>
						<p class="rootBtn">
							<a href="<%=Constant.PATH_SESSION_CNTL%>?<%=Constant.PARAM_ACTION%>=<%=Constant.ACTION_LOGIN%>" class="login">ログイン</a>
							<a href="<%=Constant.PATH_USER_CNTL%>" class="newUser">新規登録はこちら</a>
						</p>
					</div>
				</div>
			</div><!-- .upper end  -->
			<div class="lower flex column2">
				<div class="left item textblock">
					<div class="innerbox">
						<h2>みんなの質問・回答を見てみよう！</h2>
						<p>
							<span class="oswald">Wisdom</span> では、日々いろんなユーザーが<br class="mini">
							活発に質問・回答を投稿しています。<br>
							質問・回答は、こちらのボタンから、どなたでもご覧いただけます！<br>
							<br>
							ぜひあなたも、<br class="mini"><span class="oswald">Wisdom</span> のナレッジコミュニケーションを体感してください。
						</p>
						<p class="rootBtn">
							<a href="<%=Constant.PATH_SESSION_CNTL%>?<%=Constant.PARAM_ACTION%>=<%=Constant.ACTION_VISITOR%>" class="visitor">
								ログインせずに質問を見る
							</a>
						</p>
					</div>
				</div>
				<div class="right item imgblock">
				</div>
			</div><!-- .lower end  -->
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