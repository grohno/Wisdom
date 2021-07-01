package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyPageDao;
import dao.UserDao;
import dto.Question;
import dto.User;
import util.Constant;

public class MyPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 自分の質問/回答ページ表示処理
        HttpSession session = req.getSession();
        // ログイン判定（未ログインの場合はログイン画面へリダイレクト）
        if (session.getAttribute(Constant.SCOPE_LOGIN_USER) == null) {
            resp.sendRedirect(Constant.PATH_SESSION_CNTL);
        } else {
            try {
                User loginUser = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER);
                int loginUserId = loginUser.getId();
                MyPageDao mpdao = new MyPageDao();

                // 自分の投稿した質問情報を取得（ユーザ質問情報）
                ArrayList<Question> myQueList = mpdao.selectMyQue(loginUserId, 10, Constant.SQL_FIND_MY_QUE);
                // 自分の投稿した回答の質問情報を取得（ユーザ回答情報）
                ArrayList<Question> myAnsQueList = mpdao.selectMyQue(loginUserId, 10, Constant.SQL_FIND_MY_ANS_QUE);

                // リクエストスコープ["myQue"]にユーザ質問情報を格納
                req.setAttribute("myQue", myQueList);
                // リクエストスコープ["myAnsQue"]にユーザ回答情報を格納
                req.setAttribute("myAnsQue", myAnsQueList);

                if (loginUser.getBookmark() != null && loginUser.getBookmark().length() != 0) {
                    // 自分がブックマークした投稿の質問情報を取得
                    ArrayList<Question> myBookmarkQueList =
                            mpdao.selectBookmarkQue(loginUserId, 10, loginUser.getBookmark());

                    // リクエストスコープに自分がブックマークした投稿の質問情報を格納
                    req.setAttribute("myBookmarkQueList", myBookmarkQueList);
                }

                // マイページ画面へ遷移
                req.getRequestDispatcher(Constant.PATH_MYPAGE_JSP).forward(req, resp);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding(Constant.ENCODE);
        HttpSession session = req.getSession();
        // ログイン判定（未ログインの場合はログイン画面へリダイレクト）
        if (session.getAttribute(Constant.SCOPE_LOGIN_USER) == null) {
            req.getRequestDispatcher(Constant.PATH_GATE_JSP).forward(req, resp);
            // ユーザーの質問/回答ページ表示処理
        } else if (req.getParameter(Constant.PARAM_USER_ID) != null) {
            try {
                int userId = Integer.parseInt(req.getParameter(Constant.PARAM_USER_ID));
                MyPageDao mpdao = new MyPageDao();

                // ユーザーの投稿した質問情報を取得（ユーザ質問情報）
                ArrayList<Question> myQueList = mpdao.selectMyQue(userId, 10, Constant.SQL_FIND_MY_QUE);
                // ユーザーの投稿した回答の質問情報を取得（ユーザ回答情報）
                ArrayList<Question> myAnsQueList = mpdao.selectMyQue(userId, 10, Constant.SQL_FIND_MY_ANS_QUE);

                // リクエストスコープ["myQue"]にユーザ質問情報を格納
                req.setAttribute("myQue", myQueList);
                // リクエストスコープ["myAnsQue"]にユーザ回答情報を格納
                req.setAttribute("myAnsQue", myAnsQueList);


                User loginUser = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER);
                // もし、押したユーザーボタンがログインしている人と同じ名前のボタンだった場合
                if (loginUser.getId() == userId && loginUser.getBookmark() != null) {
                    // 自分がブックマークした投稿の質問情報を取得
                    ArrayList<Question> myBookmarkQueList =
                            mpdao.selectBookmarkQue(userId, 10, loginUser.getBookmark());

                    // リクエストスコープに自分がブックマークした投稿の質問情報を格納
                    req.setAttribute("myBookmarkQueList", myBookmarkQueList);
                }

                User mypageUser = new User();
                mypageUser.setId(userId);
                mypageUser.setName(req.getParameter(Constant.PARAM_USER_NAME));
                // リクエストスコープ["mypageUser"]にユーザー情報を格納
                req.setAttribute(Constant.SCOPE_MYPAGE_USER, mypageUser);

                // マイページ画面へ遷移
                req.getRequestDispatcher(Constant.PATH_MYPAGE_JSP).forward(req, resp);

            } catch (Exception e) {
                e.printStackTrace();
            }
            // ブックマーク情報の登録
        } else if (req.getParameter(Constant.PARAM_QUE_ID) != null) {
            User loginUser = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER);
            String queId = req.getParameter(Constant.PARAM_QUE_ID);
            UserDao userDao = new UserDao();
            boolean flag = true;

            // 一つもブックマークされてない場合、POSTで送られてきた質問IDをそのままユーザーのブックマーク情報に追加する
            if (loginUser.getBookmark() == null) {
                loginUser.setBookmark(queId);
                // ブックマーク情報が存在する場合
            } else {
                // POSTで送られてきた質問IDがすでにブックマークされているものかどうかを確認
                String[] strLoginUserBookmarks = loginUser.getBookmark().split(",", 0);
                for (String strLoginUserBookmark: strLoginUserBookmarks) {
                    if (strLoginUserBookmark.equals(queId)) {
                        flag = false;
                        continue;
                    }
                }

                ArrayList<String> strLoginUserBookmarksList = new ArrayList<>();

                // まだブックマークされていない質問であれば、ユーザーのブックマーク情報に質問IDを追加する
                if (flag) {
                    loginUser.setBookmark(loginUser.getBookmark() + "," + queId);
                    // すでにブックマークされている質問であれば、解除とみなし削除
                } else {
                    for (String strLoginUserBookmark: strLoginUserBookmarks) {
                        if (!strLoginUserBookmark.equals(queId)) {
                            strLoginUserBookmarksList.add(strLoginUserBookmark);
                        }
                    }
                    String str = strLoginUserBookmarksList.get(0);
                    for (int i = 1; i < strLoginUserBookmarksList.size(); i++) {
                        str += "," + strLoginUserBookmarksList.get(i);
                    }
                    loginUser.setBookmark(str);
                }
            }

            try {
                userDao.updateUser(loginUser);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            session.setAttribute(Constant.SCOPE_LOGIN_USER, loginUser);
            resp.sendRedirect(Constant.PATH_WISDOM_CNTL);
        }

    }

}
