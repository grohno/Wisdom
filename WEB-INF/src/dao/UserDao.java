package dao;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DbAccess;
import dto.User;
import util.Constant;

/**
 * ユーザテーブルアクセスオブジェクト
 * @author master
 * @version 1.0
 */
public class UserDao {
    /**
     *
     * @param userId ユーザID
     * @return ユーザデータオブジェクト
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public User findUser(int userId) throws ClassNotFoundException, SQLException {

        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_USER_FIND);
        pstmt.setInt(1, userId);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt(Constant.COLUMN_ID));
            user.setName(replaceInput(rs.getString(Constant.COLUMN_USER_NAME)));
            user.setEmail(rs.getString(Constant.COLUMN_USER_EMAIL));
        }
        dbAccess.closeAll();
        return user;
    }

    /**
     *
     * @param loginId ログインユーザID
     * @param password ログインパスワード
     * @return ユーザデータオブジェクト
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public User login(String loginEmail, String password) throws SQLException, ClassNotFoundException {
        User user = null;
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_LOGIN_CHECK);
        pstmt.setString(1, loginEmail);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        if (rs.next()) {
            if (getSaltedPassword(password, rs.getString(Constant.COLUMN_USER_SALT))
                    .equals(rs.getString(Constant.COLUMN_USER_PASS))) {
                user = new User();
                user.setId(rs.getInt(Constant.COLUMN_ID));
                user.setName(replaceInput(rs.getString(Constant.COLUMN_USER_NAME)));
                user.setEmail(replaceInput(rs.getString(Constant.COLUMN_USER_EMAIL)));
                user.setEncryptPassword(rs.getString(Constant.COLUMN_USER_PASS));
                user.setSalt(rs.getString(Constant.COLUMN_USER_SALT));
                user.setCreatedAt(rs.getTimestamp(Constant.COLUMN_CREATE));
                user.setUpdatedAt(rs.getTimestamp(Constant.COLUMN_UPDATE));
                user.setBookmark(rs.getString(Constant.COLUMN_USER_BOOKMARK));
            }
        }
        return user;
    }

    /**
     * ユーザを作成
     * @param user 作成するユーザデータ
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void createUser(User user) throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();

        //ソルト値
        String salt = getSalt();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_USER_INSERT);
        pstmt.setString(1, user.getEmail());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, getSaltedPassword(user.getEncryptPassword(), salt));
        pstmt.setString(4, salt);
        dbAccess.exeUpdate(pstmt);
    }

    /**
     * ユーザを更新
     * @param user 作成するユーザデータ
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateUser(User user) throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_USER_UPDATE);
        pstmt.setString(1, user.getEmail());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getBookmark());
        pstmt.setInt(4, user.getId());
        dbAccess.exeUpdate(pstmt);
    }

    /**
     * パスワードを更新
     * @param user 更新するユーザーデータ
     * @param sessionUser セッションに登録されている現在のユーザーデータ
     * @param oldPassword 確認のためユーザーに入力を求めた現在のパスワード
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean updateUserPassword(User user, User sessionUser, String oldPassword)
            throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();

        if (getSaltedPassword(oldPassword, sessionUser.getSalt()).equals(sessionUser.getEncryptPassword())) {
            //ソルト値
            String salt = getSalt();
            PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_USER_UPDATE_PASSWORD);
            pstmt.setString(1, getSaltedPassword(user.getEncryptPassword(), salt));
            pstmt.setString(2, salt);
            pstmt.setInt(3, user.getId());
            dbAccess.exeUpdate(pstmt);
            return true;
        } else {
            return false;
        }
    }


    /**
     * ユーザを削除
     * @param user 削除するユーザデータ
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void deleteUser(User user) throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();

        //ソルト値
        String salt = getSalt();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_USER_RESIGN);
        pstmt.setString(1, "resign@example.com");
        pstmt.setString(2, "退会済");
        pstmt.setString(3, getSaltedPassword("resign000", salt));
        pstmt.setString(4, salt);
        pstmt.setString(5, null);
        pstmt.setInt(6, user.getId());
        dbAccess.exeUpdate(pstmt);
    }

    /**
     * ランダムな文字列（ソルト値）を生成
     * @return
     */
    public String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return String.valueOf(salt);
    }

    /**
     *
     * @param password ログインパスワード(平文)
     * @param salt ソルト
     * @return 生成された安全なパスワード
     */
    public String getSaltedPassword(String password, String salt) {
        return getSha256(salt + password);
    }

    // 文字列から SHA256 のハッシュ値を取得
    private String getSha256(String target) {
        MessageDigest md = null;
        StringBuffer buf = new StringBuffer();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(target.getBytes());
            byte[] digest = md.digest();
            for (int i = 0; i < digest.length; i++) {
                buf.append(String.format("%02x", digest[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    //スクリプト記号を特殊文字へ変換
    public String replaceInput(String inputData) {
        String outputData = inputData;
        outputData = outputData.replace("&", "&amp;");
        outputData = outputData.replace("\"", "&quot;");
        outputData = outputData.replace("<", "&lt;");
        outputData = outputData.replace(">", "&gt;");
        outputData = outputData.replace("'", "&#39;");
        return outputData;
    }

}