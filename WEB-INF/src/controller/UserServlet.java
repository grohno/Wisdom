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

 * ユーザコントローラ ユーザーの登録

 * @author master

 * @version 1.0

**/

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // ユーザー管理画面表示処理
        if (session.getAttribute(Constant.SCOPE_LOGIN_USER) != null
                && Constant.ACTION_USER_EDIT.equals(req.getParameter(Constant.PARAM_ACTION))) {
            req.getRequestDispatcher(Constant.PATH_USER_DATA_JSP).forward(req, resp);
        } else if (session.getAttribute(Constant.SCOPE_LOGIN_USER) != null) {
            //ログイン済
            resp.sendRedirect(Constant.PATH_WISDOM_CNTL);
        } else {
            //未ログイン
            req.getRequestDispatcher(Constant.PATH_NEW_USER_JSP).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(Constant.ENCODE);
        HttpSession session = req.getSession();
        if (Constant.ACTION_USER_UPDATE.equals(req.getParameter(Constant.PARAM_ACTION))) {
            // ユーザー情報更新(パスワード以外)
            User user = new User();
            user.setId(Integer.parseInt(req.getParameter(Constant.PARAM_USER_ID)));
            user.setEmail(req.getParameter(Constant.PARAM_LOGIN_EMAIL));
            user.setName(req.getParameter(Constant.PARAM_USER_NAME));
            UserDao userDao = new UserDao();
            try {
                userDao.updateUser(user);
                req.setAttribute(Constant.SCOPE_MSG, Constant.MSG_UPDATE);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                req.setAttribute(Constant.SCOPE_MSG_ERR, Constant.MSG_ERR_UPDATE);
            }
            User sessionUser = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER);
            user.setEncryptPassword(sessionUser.getEncryptPassword());
            user.setSalt(sessionUser.getSalt());
            session.setAttribute(Constant.SCOPE_LOGIN_USER, user);
            req.getRequestDispatcher(Constant.PATH_USER_DATA_JSP).forward(req, resp);
        } else if (Constant.ACTION_USER_UPDATE_PASSWORD.equals(req.getParameter(Constant.PARAM_ACTION))) {
            // ユーザー情報更新(パスワード)
            User sessionUser = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER);
            User user = new User();
            user.setId(Integer.parseInt(req.getParameter(Constant.PARAM_USER_ID)));
            user.setEncryptPassword(req.getParameter(Constant.PARAM_LOGIN_PASS));
            String oldPassword = req.getParameter(Constant.PARAM_OLD_LOGIN_PASS);
            UserDao userDao = new UserDao();
            try {
                if (userDao.updateUserPassword(user, sessionUser, oldPassword)) {
                    req.setAttribute(Constant.SCOPE_MSG, Constant.MSG_UPDATE);
                } else {
                    req.setAttribute(Constant.SCOPE_MSG_ERR, Constant.MSG_ERR_UPDATE);
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                req.setAttribute(Constant.SCOPE_MSG_ERR, Constant.MSG_ERR_UPDATE);
            }

            user.setEmail(sessionUser.getEmail());
            user.setName(sessionUser.getName());
            user.setSalt(sessionUser.getSalt());
            session.setAttribute(Constant.SCOPE_LOGIN_USER, user);

            req.getRequestDispatcher(Constant.PATH_USER_DATA_JSP).forward(req, resp);

            // ログイン済の場合メイン画面へ遷移
        } else if (session.getAttribute(Constant.SCOPE_LOGIN_USER) != null) {
            resp.sendRedirect(Constant.PATH_WISDOM_CNTL);
        } else {
            //ユーザサインアップ
            User user = new User();
            user.setEmail(req.getParameter(Constant.PARAM_LOGIN_EMAIL));
            user.setName(req.getParameter(Constant.PARAM_USER_NAME));
            user.setEncryptPassword(req.getParameter(Constant.PARAM_LOGIN_PASS));
            UserDao userDao = new UserDao();
            try {
                userDao.createUser(user);
                req.setAttribute(Constant.SCOPE_MSG, Constant.MSG_SIGN_UP);
                req.getRequestDispatcher(Constant.PATH_LOGIN_JSP).forward(req, resp);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                req.setAttribute(Constant.SCOPE_MSG_ERR, Constant.MSG_ERR_SIGN_UP);
                req.getRequestDispatcher(Constant.PATH_NEW_USER_JSP).forward(req, resp);
            }

        }

    }

}