package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import dto.User;
import util.Constant;

/**
 * セッションコントローラ ユーザーのログイン状態を管理
 * @author master
 * @version 1.0
 * doGetメソッド :
 *     ログイン状態 : 一覧画面を表示
 *     非ログイン状態 : ログイン画面を表示
 * doPostメソッド :
 *     ログイン成功 : 一覧画面を表示
 *     ログイン失敗 : ログイン画面を表示
 */
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // セッションスコープを利用する準備
        HttpSession session = req.getSession();

        // ログインボタンが押されていた場合
        if (req.getParameter(Constant.PARAM_ACTION) != null
                && Constant.ACTION_LOGIN.equals(req.getParameter(Constant.PARAM_ACTION))) {
            req.getRequestDispatcher(Constant.PATH_LOGIN_JSP).forward(req, resp); // ログインページを表示
            // ビジターボタンが押されていた場合
        } else if (req.getParameter(Constant.PARAM_ACTION) != null
                && Constant.ACTION_VISITOR.equals(req.getParameter(Constant.PARAM_ACTION))) {
            session.setAttribute(Constant.SCOPE_VISITOR_USER, "visitorUser");
            resp.sendRedirect(Constant.PATH_WISDOM_CNTL); // WisdomControllerにリダイレクト
            // セッションスコープ内にloginUserというラベルでデータが入っているか
        } else if (session.getAttribute(Constant.SCOPE_LOGIN_USER) != null) { // データがあった場合
            resp.sendRedirect(Constant.PATH_WISDOM_CNTL); // WisdomControllerにリダイレクト
        } else {
            req.getRequestDispatcher(Constant.PATH_GATE_JSP).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(Constant.ENCODE);
        HttpSession session = req.getSession();
        // ログイン処理
        if (Constant.ACTION_LOGIN.equals(req.getParameter(Constant.PARAM_ACTION))) {
            UserDao userDao = new UserDao();
            User user = null;
            try {
                user = userDao.login(req.getParameter(Constant.PARAM_LOGIN_EMAIL),
                        req.getParameter(Constant.PARAM_LOGIN_PASS));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            if (user != null) {
                session.setAttribute(Constant.SCOPE_LOGIN_USER, user);
                resp.sendRedirect(Constant.PATH_WISDOM_CNTL);
            } else {
                req.setAttribute(Constant.SCOPE_MSG_ERR, Constant.MSG_ERR_LOGIN);
                req.getRequestDispatcher(Constant.PATH_LOGIN_JSP).forward(req, resp);
            }
            // ログアウト処理
        } else if (Constant.ACTION_LOGOUT.equals(req.getParameter(Constant.PARAM_ACTION))) {
            session.removeAttribute(Constant.SCOPE_LOGIN_USER);
            resp.sendRedirect(Constant.PATH_SESSION_CNTL);
            // ビジターログアウト処理
        } else if (Constant.ACTION_VISITOR_LOGOUT.equals(req.getParameter(Constant.PARAM_ACTION))) {
            session.removeAttribute(Constant.SCOPE_VISITOR_USER);
            resp.sendRedirect(Constant.PATH_SESSION_CNTL);
            // 退会処理
        } else if (Constant.ACTION_USER_RESIGN.equals(req.getParameter(Constant.PARAM_ACTION))) {
            UserDao userDao = new UserDao();
            User user = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER);
            int id = Integer.parseInt(req.getParameter(Constant.PARAM_USER_ID));
            if (id == user.getId()) {
                try {
                    userDao.deleteUser(user);
                    session.removeAttribute(Constant.SCOPE_LOGIN_USER);
                    resp.sendRedirect(Constant.PATH_USER_CNTL);
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    req.setAttribute(Constant.SCOPE_MSG_ERR, Constant.MSG_ERR_RESIGN);
                    req.getRequestDispatcher(Constant.PATH_USER_DATA_JSP).forward(req, resp);
                }
            } else {
                req.setAttribute(Constant.SCOPE_MSG_ERR, Constant.MSG_ERR_RESIGN);
                req.getRequestDispatcher(Constant.PATH_USER_DATA_JSP).forward(req, resp);
            }
        }
    }
}