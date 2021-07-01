package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbAccess;
import dto.Question;
import util.Constant;

public class MyPageDao {

    public ArrayList<Question> selectMyQue(int userId, int max, String sql)
            throws ClassNotFoundException, SQLException {
        // レコードデータ格納用リストを生成
        ArrayList<Question> questions = new ArrayList<Question>();

        // DB接続情報を取得する
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();

        // DB接続情報から、SQL情報を取得する
        PreparedStatement pstmt = dbAccess.makePreparedStatement(sql);

        // SQL情報にユーザIDを設定する
        pstmt.setInt(1, userId);
        // SQL情報に取得条件数を設定する
        pstmt.setInt(2, max);

        //SQLの実行結果をrsインスタンスに格納
        ResultSet rs = dbAccess.exeQuery(pstmt);

        // 1レコードずつ取り出す
        while (rs.next()) {
            Question question = new Question();
            question.setId(rs.getInt(Constant.COLUMN_ID));
            question.setTitle(rs.getString(Constant.COLUMN_QUE_TITLE));
            question.setContent(rs.getString(Constant.COLUMN_CONTENT));
            question.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            question.setCreatedOn(rs.getTimestamp(Constant.COLUMN_CREATE));
            question.setUpdatedOn(rs.getTimestamp(Constant.COLUMN_UPDATE));
            question.setCategoryId(rs.getInt(Constant.COLUMN_CATE_USER));
            // 格納用リストにレコードデータを格納
            questions.add(question);
        }

        dbAccess.closeAll();

        // レコードデータ格納用リストを戻す
        return questions;

    }


    public ArrayList<Question> selectBookmarkQue(int userId, int max, String bookmarkIds)
            throws ClassNotFoundException, SQLException {
        // レコードデータ格納用リストを生成
        ArrayList<Question> questions = new ArrayList<Question>();

        // DB接続情報を取得する
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();

        String sql = "select id, title, content, created_at, updated_at, create_user_id, category_id"
                + " from questions where id in(";
        String sql2 = "?";
        String sql3 = ") order by updated_at desc limit ?;";


        String[] bookmarkIdsArray = bookmarkIds.split(",", 0);
        if (bookmarkIdsArray.length >= 1) {
            for (int i = 1; i < bookmarkIdsArray.length; i++) {
                sql2 += ",?";
            }
        }

        // DB接続情報から、SQL情報を取得する
        PreparedStatement pstmt = dbAccess.makePreparedStatement(sql + sql2 + sql3);

        int cnt = 1;
        for (String bookmarkId: bookmarkIdsArray) {
            pstmt.setInt(cnt, Integer.parseInt(bookmarkId));
            cnt++;
        }
        pstmt.setInt(cnt, max);

        //SQLの実行結果をrsインスタンスに格納
        ResultSet rs = dbAccess.exeQuery(pstmt);

        // 1レコードずつ取り出す
        while (rs.next()) {
            Question question = new Question();
            question.setId(rs.getInt(Constant.COLUMN_ID));
            question.setTitle(rs.getString(Constant.COLUMN_QUE_TITLE));
            question.setContent(rs.getString(Constant.COLUMN_CONTENT));
            question.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            question.setCreatedOn(rs.getTimestamp(Constant.COLUMN_CREATE));
            question.setUpdatedOn(rs.getTimestamp(Constant.COLUMN_UPDATE));
            question.setCategoryId(rs.getInt(Constant.COLUMN_CATE_USER));
            // 格納用リストにレコードデータを格納
            questions.add(question);
        }

        dbAccess.closeAll();

        // レコードデータ格納用リストを戻す
        return questions;

    }

}
