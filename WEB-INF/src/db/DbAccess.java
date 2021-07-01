package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Constant;

/**
 * DBアクセスデータオブジェクト
 * @author master
 */
public class DbAccess {
    /**
     * 結果データのテーブル
     */
    private ResultSet rs = null;
    /**
     * SQL文オブジェクト
     */
    private PreparedStatement pstmt = null;
    /**
     * データベースとの接続(セッション)オブジェト
     */
    private Connection conn = null;

    /**
     * DBアクセスメソッド
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public final void access() throws SQLException, ClassNotFoundException {
        Class.forName(Constant.DB_DRIVER);
        conn = DriverManager.getConnection(Constant.DB_ACCESS_URL, Constant.DB_ACCESS_NAME, Constant.DB_ACCESS_PASS);
    }

    /**
     * (SELECT)SQL実行メソッド
     * @param pstmt SQL文オブジェクト
     * @return 結果データのテーブル
     * @throws SQLException
     */
    public final ResultSet exeQuery(final PreparedStatement pstmt) throws SQLException {
        rs = pstmt.executeQuery();
        return rs;
    }

    /**
     * sql文変換メソッド
     * @param sql 文字列型のSQL
     * @return SQL文オブジェクト
     * @throws SQLException
     */
    public final PreparedStatement makePreparedStatement(final String sql) throws SQLException {
        pstmt = conn.prepareStatement(sql);
        return pstmt;
    }

    /**
     * (INSERT,UPDATE,DELETE)SQL実行メソッド
     * @param pstmt SQL文オブジェクト
     * @return 変更データ件数
     * @throws SQLException
     */
    public final int exeUpdate(final PreparedStatement pstmt) throws SQLException {
        return pstmt.executeUpdate();
    }

    /**
     * リソース解放メソッド（SELECT）
     */
    public final void closeAll() {
        try {
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * リソース解放メソッド(INSERT,UPDATE,DELETE)
     */
    public final void closeTwo() {
        try {
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}