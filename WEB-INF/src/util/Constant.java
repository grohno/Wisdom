package util;

public class Constant {

    /** 全体共通 */
    public static final String ENCODE = "UTF-8";

    /** URL/PATH */
    public static final String PATH_LOGIN_JSP = "jsp/login.jsp";
    public static final String PATH_GATE_JSP = "jsp/gate.jsp";
    public static final String PATH_MAIN_JSP = "jsp/list_que.jsp";
    public static final String PATH_DETAIL_QUE_JSP = "jsp/detail_que.jsp";
    public static final String PATH_UPDATE_QUE_JSP = "jsp/update_que.jsp";
    public static final String PATH_NEW_QUE_JSP = "jsp/new_que.jsp";
    public static final String PATH_NEW_USER_JSP = "jsp/new_user.jsp";
    public static final String PATH_MYPAGE_JSP = "jsp/mypage.jsp";
    public static final String PATH_USER_DATA_JSP = "jsp/user_data.jsp";

    public static final String PATH_WISDOM_CNTL = "WisdomController";
    public static final String PATH_SESSION_CNTL = "SessionController";
    public static final String PATH_MYPAGE_CNTL = "MyPageController";
    public static final String PATH_USER_CNTL = "UserController";
    public static final String PATH_DETAIL_QUE_ID = "WisdomController?action=detailQue&que_id=";

    /** アクション名 */
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_LOGOUT = "logout";
    public static final String ACTION_VISITOR = "visitor";
    public static final String ACTION_SEARCH = "search";
    public static final String ACTION_VISITOR_LOGOUT = "visitorLogout";
    public static final String ACTION_QUE_NEW = "newQue";
    public static final String ACTION_QUE_CREATE = "createQue";
    public static final String ACTION_QUE_EDIT = "editQue";
    public static final String ACTION_QUE_DETAIL = "detailQue";
    public static final String ACTION_QUE_DELETE = "deleteQue";
    public static final String ACTION_QUE_UPDATE = "updateQue";
    public static final String ACTION_ANS_CREATE = "createAns";
    public static final String ACTION_USER_NEW = "newUser";
    public static final String ACTION_USER_CREATE = "createUser";
    public static final String ACTION_USER_EDIT = "editUser";
    public static final String ACTION_USER_UPDATE = "updateUser";
    public static final String ACTION_USER_UPDATE_PASSWORD = "updatePassword";
    public static final String ACTION_USER_RESIGN = "resign";
    public static final String ACTION_USER_BOOKMARK = "bookmark";
    public static final String ACTION_BEST_ANS = "bestAns";
    public static final String ACTION_HELPFUL = "helpful";

    /** パラメータ */
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_LOGIN_EMAIL = "email";
    public static final String PARAM_USER_ID = "id";
    public static final String PARAM_USER_NAME = "name";
    public static final String PARAM_LOGIN_PASS = "loginPass";
    public static final String PARAM_OLD_LOGIN_PASS = "old_LoginPass";
    public static final String PARAM_QUE = "que";
    public static final String PARAM_QUE_ID = "que_id";
    public static final String PARAM_QUE_TITLE = "que_title";
    public static final String PARAM_QUE_CONTENT = "que_content";
    public static final String PARAM_SEARCH_TITLE = "searchTitle";
    public static final String PARAM_SEARCH_UPDATEAT_BETWEEN = "searchUpdatedAt_between";
    public static final String PARAM_SEARCH_CATEGORY = "searchCategory";
    public static final String PARAM_SEARCH_UPDATEAT_AND = "searchUpdatedAt_and";
    public static final String PARAM_ANS_ID = "ans_id";
    public static final String PARAM_ANS_CONTENT = "ans_content";
    public static final String PARAM_ANS_HELPFUL_COUNT = "helpful_count";
    public static final String PARAM_ANS_HELPFUL_USER = "helpful_user";

    /** スコープ名 */
    public static final String SCOPE_LOGIN_USER = "loginUser";
    public static final String SCOPE_VISITOR_USER = "visitorUser";
    public static final String SCOPE_LOGIN_USER_BOOKMARKS = "loginUserBookmarks";
    public static final String SCOPE_CATEGORIES = "categories";
    public static final String SCOPE_MYPAGE_USER = "mypageUser";
    public static final String SCOPE_MSG = "msg";
    public static final String SCOPE_MSG_ERR = "errMsg";
    public static final String SCOPE_QUE = "que";
    public static final String SCOPE_QUE_ANS = "questionAnswersRelation";
    public static final String SCOPE_QUE_USER = "questionUserRelations";

    /** メッセージ */
    public static final String MSG_SIGN_UP = "登録が完了しました。";
    public static final String MSG_ERR_SIGN_UP = "登録に失敗しました。";
    public static final String MSG_ERR_LOGIN = "ユーザIDかパスワードが不正です";
    public static final String MSG_UPDATE = "更新が完了しました。";
    public static final String MSG_ERR_UPDATE = "更新に失敗しました。";
    public static final String MSG_ERR_RESIGN = "退会処理に失敗しました。";

    /** DB関係 */
    public static final String DB_ACCESS_NAME = "postgres";
    public static final String DB_ACCESS_PASS = "password";
    public static final String DB_ACCESS_URL = "jdbc:postgresql://localhost/wisdomdb";
    public static final String DB_DRIVER = "org.postgresql.Driver";

    /** SQL */
    public static final String SQL_QUE_SELECT = "select id, title, content, create_user_id,"
            + " created_at , updated_at , best_ans_id , helpful_ans_id , helpful_user , category_id from questions;";
    public static final String SQL_QUE_SEARCH_CATEGORY = "SELECT id, title, content, create_user_id, created_at ,"
            + " updated_at , best_ans_id , helpful_ans_id , helpful_user , category_id"
            + " FROM questions WHERE category_id = ?;";
    public static final String SQL_QUE_SEARCH_TITLE = "SELECT id, title, content, create_user_id, created_at ,"
            + " updated_at , best_ans_id , helpful_ans_id , helpful_user , category_id"
            + " FROM questions WHERE title LIKE ?;";
    public static final String SQL_QUE_SEARCH_UPDATEDAT = "SELECT id, title, content, create_user_id,"
            + " created_at , updated_at , best_ans_id , helpful_ans_id , helpful_user , category_id FROM questions"
            + " WHERE  updated_at BETWEEN ? AND ?;";
    public static final String SQL_QUE_FIND = "select id, title, content, create_user_id"
            + ", created_at , updated_at , best_ans_id , helpful_ans_id , helpful_user , category_id"
            + " from questions where id = ?;";
    public static final String SQL_QUE_INSERT = "insert into questions (title, content,"
            + " create_user_id,created_at , updated_at , category_id) values(?,?,?,now(),now(),?);";
    public static final String SQL_QUE_UPDATE = "update questions set title = ?, content = ?,"
            + " create_user_id = ?, updated_at = now(), category_id = ? where id = ?;";
    public static final String SQL_QUE_BEST_ANS_UPDATE = "update questions set best_ans_id = ? where id = ?;";
    public static final String SQL_QUE_HELPFUL_ANS_UPDATE
            = "update questions set helpful_ans_id = ? , helpful_user = ? where id = ?;";
    public static final String SQL_ANS_FIND = "select id, content, create_user_id, created_at ,"
            + " updated_at , helpful_count from answers where question_id = ?;";
    public static final String SQL_ANS_UPDATE = "update answers set content = ?, create_user_id = ?,"
            + " question_id = ?, updated_at = now() where id = ?;";
    public static final String SQL_ANS_INSERT = "insert into answers (content, create_user_id,"
            + " question_id, created_at , updated_at) values(?,?,?,now(),now());";
    public static final String SQL_HELPFUL_COUNT_ANS_UPDATE
            = "update answers set helpful_count = ? where id = ?;";
    public static final String SQL_USER_FIND = "select id, name, email from users where id = ?;";
    public static final String SQL_LOGIN_CHECK = "select id, name, email, encrypt_password,"
            + " salt,created_at, updated_at, bookmark  from users where email = ?;";
    public static final String SQL_USER_INSERT = "insert into users (email, name, encrypt_password,"
            + " salt ,created_at, updated_at) values(?,?,?,?,now(),now());";
    public static final String SQL_USER_UPDATE =
            "UPDATE users SET email = ?, name = ?, created_at = now(), updated_at = now(), bookmark = ? WHERE id = ?;";
    public static final String SQL_USER_UPDATE_PASSWORD =
            "UPDATE users SET encrypt_password = ?, salt = ?,"
            + " created_at = now(), updated_at = now() WHERE id = ?;";
    public static final String SQL_USER_RESIGN = "UPDATE users SET email = ?, name = ?, "
            + "encrypt_password = ?, salt = ?, created_at = now(), updated_at = now(), bookmark = ? WHERE id = ?;";
    public static final String SQL_FIND_MY_QUE = "select id, title, content, created_at, updated_at, "
            + "create_user_id , category_id from questions where create_user_id = ? "
            + "order by updated_at desc limit ?;";
    public static final String SQL_FIND_BOOLMARK_QUE = "select id, title, content, created_at, updated_at, "
            + "create_user_id , category_id from questions where id in(?) order by updated_at desc limit ?;";
    public static final String SQL_FIND_MY_ANS_QUE = "select q.id, q.title, q.content, q.created_at, "
            + "q.updated_at, q.create_user_id , q.category_id from questions as q inner join answers as a "
            + "ON q.id = a.question_id where a.create_user_id = ? "
            + "order by a.updated_at desc limit ?;";
    public static final String SQL_CATE_SELECT = "select id, name from categories;";

    public static final String SQL_CATE_FIND = "select name from categories where id = ?;";



    /** カラム */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_CREATE = "created_at";
    public static final String COLUMN_UPDATE = "updated_at";
    public static final String COLUMN_CREATE_USER = "create_user_id";
    public static final String COLUMN_BEAT_ANS_ID = "best_ans_id";
    public static final String COLUMN_HELPFUL_ANS_ID = "helpful_ans_id";
    public static final String COLUMN_HELPFUL_COUNT = "helpful_count";
    public static final String COLUMN_HELPFUL_USER = "helpful_user";

    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASS = "encrypt_password";
    public static final String COLUMN_USER_SALT = "salt";
    public static final String COLUMN_USER_BOOKMARK = "bookmark";
    public static final String COLUMN_LOGIN_ID = "login_id";
    public static final String COLUMN_QUE_TITLE = "title";
    public static final String COLUMN_QUE_ID = "question_id";
    public static final String COLUMN_CATE_NAME = "name";
    public static final String COLUMN_CATE_USER = "category_id";

}
