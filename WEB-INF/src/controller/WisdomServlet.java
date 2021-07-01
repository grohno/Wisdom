package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AnswerDao;
import dao.CategoryDao;
import dao.QuestionDao;
import dao.UserDao;
import dto.Answer;
import dto.AnswerUserRelation;
import dto.Category;
import dto.Question;
import dto.QuestionAnswersRelation;
import dto.QuestionUserRelation;
import dto.User;
import util.Constant;

/*
 *  質問の一覧表示、投稿、更新、回答
 */

public class WisdomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // セッションスコープを利用する準備
        HttpSession session = req.getSession();
        // ログイン判定（未ログインの場合はログイン画面へリダイレクト）
        if (session.getAttribute(Constant.SCOPE_LOGIN_USER) == null
                && session.getAttribute(Constant.SCOPE_VISITOR_USER) == null) {
            resp.sendRedirect(Constant.PATH_SESSION_CNTL); // ログインしてなかったらセッションコントローラーに戻す
        } else { // ログイン済、もしくはビジターの場合
            try {
                // 質問詳細画面表示処理 // パラメーターを取得している
                if (Constant.ACTION_QUE_DETAIL.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    // 画面から取得した質問idを元にDBから質問情報を取得
                    QuestionDao questionDao = new QuestionDao();
                    Question question = questionDao
                            .findQuestion(Integer.parseInt(req.getParameter(Constant.PARAM_QUE_ID)));
                    // 質問情報と質問者情報を持つオブジェクトを生成し、対象の質問情報をセット
                    QuestionUserRelation questionUserRelation = new QuestionUserRelation();
                    questionUserRelation.setQuestion(question);
                    // 質問者情報を取得し、質問情報と質問者情報を持つオブジェクトにセット
                    UserDao userDao = new UserDao();
                    questionUserRelation.setUser(userDao.findUser(question.getCreateUserId()));
                    // 質問とその質問に対する回答(複数)をまとめるオブジェクトを生成し、質問情報と質問者情報を持つオブジェクトをセット
                    QuestionAnswersRelation questionAnswersRelation = new QuestionAnswersRelation();
                    questionAnswersRelation.setQuestionUserRelation(questionUserRelation);
                    // 質問に対する回答リストをDBから取得
                    AnswerDao answerDao = new AnswerDao();
                    ArrayList<Answer> answers = answerDao.findAnswerByQuestion(question.getId());
                    // 回答リストに回答情報と回答者情報を持つオブジェクトを追加、
                    ArrayList<AnswerUserRelation> answerUserRelations = new ArrayList<AnswerUserRelation>();
                    for (Answer answer : answers) {
                        AnswerUserRelation answerUserRelation = new AnswerUserRelation();
                        answerUserRelation.setAnswer(answer);
                        // 回答情報から回答者情報をDBから取得
                        answerUserRelation.setUser(userDao.findUser(answer.getCreateUserId()));
                        answerUserRelations.add(answerUserRelation);
                    }
                    // 回答リストを質問情報と回答情報を持つオブジェクトにセット
                    questionAnswersRelation.setAnswerUserRelation(answerUserRelations);
                    req.setAttribute(Constant.SCOPE_QUE_ANS, questionAnswersRelation);

                    // カテゴリー一覧情報を取得しリクエストスコープにセット
                    ArrayList<Category> categories = new ArrayList<Category>();
                    CategoryDao categoryDao = new CategoryDao();
                    categories = categoryDao.getAllCategories();
                    req.setAttribute(Constant.SCOPE_CATEGORIES, categories);

                    req.getRequestDispatcher(Constant.PATH_DETAIL_QUE_JSP).forward(req, resp);
                    // 質問編集画面表示処理
                } else if (Constant.ACTION_QUE_EDIT.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    QuestionDao questionDao = new QuestionDao();
                    req.setAttribute(Constant.SCOPE_QUE,
                            questionDao.findQuestion(Integer.parseInt(req.getParameter(Constant.PARAM_QUE_ID))));
                    // カテゴリー一覧情報を取得しリクエストスコープにセット
                    ArrayList<Category> categories = new ArrayList<Category>();
                    CategoryDao categoryDao = new CategoryDao();
                    categories = categoryDao.getAllCategories();
                    req.setAttribute(Constant.SCOPE_CATEGORIES, categories);
                    req.getRequestDispatcher(Constant.PATH_UPDATE_QUE_JSP).forward(req, resp);
                    // 質問新規投稿画面表示処理
                } else if (Constant.ACTION_QUE_NEW.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    // カテゴリー一覧情報を取得しリクエストスコープにセット
                    ArrayList<Category> categories = new ArrayList<Category>();
                    CategoryDao categoryDao = new CategoryDao();
                    categories = categoryDao.getAllCategories();
                    req.setAttribute(Constant.SCOPE_CATEGORIES, categories);
                    req.getRequestDispatcher(Constant.PATH_NEW_QUE_JSP).forward(req, resp);
                    // 質問一覧画面表示処理
                } else {
                    // DBから質問情報を全件取得する
                    ArrayList<QuestionUserRelation> questionUserRelations = new ArrayList<QuestionUserRelation>();
                    ArrayList<QuestionAnswersRelation> questionAnswersRelations
                        = new ArrayList<QuestionAnswersRelation>();
                    QuestionDao questionDao = new QuestionDao();
                    ArrayList<Question> questions = new ArrayList<Question>();
                    // もし検索ボタンがおされていたら
                    if (Constant.ACTION_SEARCH.equals(req.getParameter(Constant.PARAM_ACTION))) {
                        // タイトル検索の場合
                        if (req.getParameter(Constant.PARAM_SEARCH_TITLE) != null) {
                            questions = questionDao.getSearchTitleQuestions(
                                    req.getParameter(Constant.PARAM_SEARCH_TITLE));
                            // 更新日検索の場合
                        } else if (req.getParameter(Constant.PARAM_SEARCH_UPDATEAT_BETWEEN) != null
                                && req.getParameter(Constant.PARAM_SEARCH_UPDATEAT_AND) != null) {
                            questions = questionDao.getSearchUpdatedAtQuestions(
                                    req.getParameter(Constant.PARAM_SEARCH_UPDATEAT_BETWEEN),
                                    req.getParameter(Constant.PARAM_SEARCH_UPDATEAT_AND));
                            // カテゴリー絞り込みの場合
                        } else if (req.getParameter(Constant.PARAM_SEARCH_CATEGORY) != null) {
                            questions = questionDao.getSearchCategory(
                                    Integer.parseInt(req.getParameter(Constant.PARAM_SEARCH_CATEGORY)));
                        }
                        // 全件取得
                    } else {
                        questions = questionDao.getAllQuestions();
                    }
                    // 質問者情報を取得しオブジェクトにセット後質問リストに追加
                    UserDao userDao = new UserDao();
                    for (Question question : questions) {
                        QuestionUserRelation questionUserRelation = new QuestionUserRelation();
                        questionUserRelation.setQuestion(question);
                        // 質問の質問者情報をDBから取得
                        questionUserRelation.setUser(userDao.findUser(question.getCreateUserId()));
                        questionUserRelations.add(questionUserRelation);

                        // 質問とその質問に対する回答(複数)をまとめるオブジェクトを生成し、質問情報と質問者情報を持つオブジェクトをセット
                        QuestionAnswersRelation questionAnswersRelation = new QuestionAnswersRelation();
                        questionAnswersRelation.setQuestionUserRelation(questionUserRelation);
                        // 質問に対する回答リストをDBから取得
                        AnswerDao answerDao = new AnswerDao();
                        ArrayList<Answer> answers = answerDao.findAnswerByQuestion(question.getId());
                        // 回答リストに回答情報と回答者情報を持つオブジェクトを追加、
                        ArrayList<AnswerUserRelation> answerUserRelations = new ArrayList<AnswerUserRelation>();
                        for (Answer answer : answers) {
                            AnswerUserRelation answerUserRelation = new AnswerUserRelation();
                            answerUserRelation.setAnswer(answer);
                            // 回答情報から回答者情報をDBから取得
                            answerUserRelation.setUser(userDao.findUser(answer.getCreateUserId()));
                            answerUserRelations.add(answerUserRelation);
                        }

                        // 回答リストを質問情報と回答情報を持つオブジェクトにセット
                        questionAnswersRelation.setAnswerUserRelation(answerUserRelations);
                        questionAnswersRelations.add(questionAnswersRelation);
                    }

                    // ブックマーク情報を取得
                    User loginUser = (User)session.getAttribute(Constant.SCOPE_LOGIN_USER);
                    ArrayList<Integer> loginUserBookmarks = new ArrayList<>();
                    if (loginUser != null) {
                        if (loginUser.getBookmark() != null && loginUser.getBookmark().length() != 0) {
                            String[] strLoginUserBookmarks = loginUser.getBookmark().split(",", 0);
                            for (String strLoginUserBookmark : strLoginUserBookmarks) {
                                loginUserBookmarks.add(Integer.parseInt(strLoginUserBookmark));
                            }
                        }
                    }

                    // カテゴリー一覧情報を取得
                    ArrayList<Category> categories = new ArrayList<Category>();
                    CategoryDao categoryDao = new CategoryDao();
                    categories = categoryDao.getAllCategories();

                    // リクエストスコープに入れて画面遷移
                    req.setAttribute(Constant.SCOPE_QUE_USER, questionUserRelations);
                    req.setAttribute(Constant.SCOPE_QUE_ANS, questionAnswersRelations);
                    req.setAttribute(Constant.SCOPE_LOGIN_USER_BOOKMARKS, loginUserBookmarks);
                    req.setAttribute(Constant.SCOPE_CATEGORIES, categories);

                    req.getRequestDispatcher(Constant.PATH_MAIN_JSP).forward(req, resp);
                }
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
        } else {
            try {
                User loginUser = (User) session.getAttribute(Constant.SCOPE_LOGIN_USER);
                // 質問新規投稿処理（INSERT）
                if (Constant.ACTION_QUE_CREATE.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    Question question = new Question();
                    question.setTitle(req.getParameter(Constant.PARAM_QUE_TITLE));
                    question.setContent(req.getParameter(Constant.PARAM_QUE_CONTENT));
                    question.setCategoryId(Integer.parseInt(req.getParameter(Constant.PARAM_SEARCH_CATEGORY)));
                    question.setCreateUserId(loginUser.getId());
                    QuestionDao questionDao = new QuestionDao();
                    questionDao.saveQuestion(question);
                    resp.sendRedirect(Constant.PATH_WISDOM_CNTL);
                    // 質問更新処理（UPDATE）
                } else if (Constant.ACTION_QUE_UPDATE.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    Question question = new Question();
                    question.setId(Integer.parseInt(req.getParameter(Constant.PARAM_QUE_ID)));
                    question.setTitle(req.getParameter(Constant.PARAM_QUE_TITLE));
                    question.setContent(req.getParameter(Constant.PARAM_QUE_CONTENT));
                    question.setCategoryId(Integer.parseInt(req.getParameter(Constant.PARAM_SEARCH_CATEGORY)));
                    question.setCreateUserId(loginUser.getId());
                    QuestionDao questionDao = new QuestionDao();
                    questionDao.saveQuestion(question);
                    resp.sendRedirect(Constant.PATH_WISDOM_CNTL);
                    // 質問への回答処理
                } else if (Constant.ACTION_ANS_CREATE.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    Answer answer = new Answer();
                    answer.setContent(req.getParameter(Constant.PARAM_ANS_CONTENT));
                    answer.setCreateUserId(loginUser.getId());
                    answer.setQuestionId(Integer.parseInt(req.getParameter(Constant.PARAM_QUE_ID)));
                    AnswerDao answerDao = new AnswerDao();
                    answerDao.saveAnswer(answer);
                    resp.sendRedirect(Constant.PATH_DETAIL_QUE_ID + req.getParameter(Constant.PARAM_QUE_ID));
                    // ベストアンサー登録処理
                } else if (Constant.ACTION_BEST_ANS.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    Question question = new Question();
                    question.setId(Integer.parseInt(req.getParameter(Constant.PARAM_QUE_ID)));
                    question.setBestAnsId(Integer.parseInt(req.getParameter(Constant.PARAM_ANS_ID)));
                    QuestionDao questionDao = new QuestionDao();
                    questionDao.updateBestAns(question);
                    resp.sendRedirect(Constant.PATH_DETAIL_QUE_ID + req.getParameter(Constant.PARAM_QUE_ID));
                    // 助かった！回答の登録処理
                } else if (Constant.ACTION_HELPFUL.equals(req.getParameter(Constant.PARAM_ACTION))) {
                    // 質問情報登録
                    Question question = new Question();
                    question.setId(Integer.parseInt(req.getParameter(Constant.PARAM_QUE_ID)));
                    question.setHelpfulAnsId(Integer.parseInt(req.getParameter(Constant.PARAM_ANS_ID)));
                    // 助かった！押下ユーザー処理
                    if (req.getParameter(Constant.PARAM_ANS_HELPFUL_USER).equals("null")) {
                        question.setHelpfulUser(req.getParameter(Constant.PARAM_USER_ID));
                    } else {
                        question.setHelpfulUser(req.getParameter(Constant.PARAM_ANS_HELPFUL_USER)
                                + "," + req.getParameter(Constant.PARAM_USER_ID));
                    }
                    QuestionDao questionDao = new QuestionDao();
                    questionDao.updateHelpfulAns(question);
                    // 回答情報東特
                    Answer answer = new Answer();
                    answer.setId(Integer.parseInt(req.getParameter(Constant.PARAM_ANS_ID)));
                    String helpfulCount = req.getParameter(Constant.PARAM_ANS_HELPFUL_COUNT);
                    int intHhelpfulCount;
                    if (helpfulCount == null) {
                        intHhelpfulCount = 1;
                    } else {
                        intHhelpfulCount = Integer.parseInt(helpfulCount) + 1;
                    }
                    answer.setHelpfulCount(intHhelpfulCount);

                    AnswerDao answerDao = new AnswerDao();
                    answerDao.updateHelpfulCount(answer);
                    resp.sendRedirect(Constant.PATH_DETAIL_QUE_ID + req.getParameter(Constant.PARAM_QUE_ID));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}