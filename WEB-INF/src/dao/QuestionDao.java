package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbAccess;
import dto.Question;
import util.Constant;

/**
 * 質問テーブルアクセスオブジェクト
 * @author master
 * @version 1.0
 */
public class QuestionDao {
    /**
     * 質問一覧取得メソッド
     * @return 質問一覧(全件)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Question> getAllQuestions() throws SQLException, ClassNotFoundException {
        ArrayList<Question> questions = new ArrayList<Question>();
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_SELECT);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            Question question = new Question();
            question.setId(rs.getInt(Constant.COLUMN_ID));
            question.setTitle(replaceInput(rs.getString(Constant.COLUMN_QUE_TITLE)));
            question.setContent(replaceInput(rs.getString(Constant.COLUMN_CONTENT)));
            question.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            question.setCreatedOn(rs.getTimestamp(Constant.COLUMN_CREATE));
            question.setUpdatedOn(rs.getTimestamp(Constant.COLUMN_UPDATE));
            question.setBestAnsId(rs.getInt(Constant.COLUMN_BEAT_ANS_ID));
            question.setHelpfulUser(rs.getString(Constant.COLUMN_HELPFUL_USER));
            question.setCategoryId(rs.getInt(Constant.COLUMN_CATE_USER));
            questions.add(question);
        }
        dbAccess.closeAll();
        return questions;
    }

    /**
     * タイトル検索質問一覧取得メソッド
     * @return 質問一覧(タイトル検索)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Question> getSearchTitleQuestions(String searchTitle) throws SQLException, ClassNotFoundException {
        ArrayList<Question> questions = new ArrayList<Question>();
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_SEARCH_TITLE);
        pstmt.setString(1, '%' + searchTitle + '%');
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            Question question = new Question();
            question.setId(rs.getInt(Constant.COLUMN_ID));
            question.setTitle(replaceInput(rs.getString(Constant.COLUMN_QUE_TITLE)));
            question.setContent(replaceInput(rs.getString(Constant.COLUMN_CONTENT)));
            question.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            question.setCreatedOn(rs.getTimestamp(Constant.COLUMN_CREATE));
            question.setUpdatedOn(rs.getTimestamp(Constant.COLUMN_UPDATE));
            question.setBestAnsId(rs.getInt(Constant.COLUMN_BEAT_ANS_ID));
            question.setHelpfulUser(rs.getString(Constant.COLUMN_HELPFUL_USER));
            question.setCategoryId(rs.getInt(Constant.COLUMN_CATE_USER));
            questions.add(question);
        }
        dbAccess.closeAll();
        return questions;
    }

    /**
     * 更新日検索質問一覧取得メソッド
     * @return 質問一覧(更新日検索)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Question> getSearchUpdatedAtQuestions(String between, String and)
            throws SQLException, ClassNotFoundException {
        ArrayList<Question> questions = new ArrayList<Question>();
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_SEARCH_UPDATEDAT);
        pstmt.setDate(1, Date.valueOf(between));
        pstmt.setDate(2, Date.valueOf(and));
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            Question question = new Question();
            question.setId(rs.getInt(Constant.COLUMN_ID));
            question.setTitle(replaceInput(rs.getString(Constant.COLUMN_QUE_TITLE)));
            question.setContent(replaceInput(rs.getString(Constant.COLUMN_CONTENT)));
            question.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            question.setCreatedOn(rs.getTimestamp(Constant.COLUMN_CREATE));
            question.setUpdatedOn(rs.getTimestamp(Constant.COLUMN_UPDATE));
            question.setBestAnsId(rs.getInt(Constant.COLUMN_BEAT_ANS_ID));
            question.setHelpfulUser(rs.getString(Constant.COLUMN_HELPFUL_USER));
            question.setCategoryId(rs.getInt(Constant.COLUMN_CATE_USER));
            questions.add(question);
        }
        dbAccess.closeAll();
        return questions;
    }

    /**
     * カテゴリー絞り込み質問一覧取得メソッド
     * @return 質問一覧(カテゴリー絞り込み)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Question> getSearchCategory(int id) throws SQLException, ClassNotFoundException {
        ArrayList<Question> questions = new ArrayList<Question>();
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_SEARCH_CATEGORY);
        pstmt.setInt(1, id);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            Question question = new Question();
            question.setId(rs.getInt(Constant.COLUMN_ID));
            question.setTitle(replaceInput(rs.getString(Constant.COLUMN_QUE_TITLE)));
            question.setContent(replaceInput(rs.getString(Constant.COLUMN_CONTENT)));
            question.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            question.setCreatedOn(rs.getTimestamp(Constant.COLUMN_CREATE));
            question.setUpdatedOn(rs.getTimestamp(Constant.COLUMN_UPDATE));
            question.setBestAnsId(rs.getInt(Constant.COLUMN_BEAT_ANS_ID));
            question.setHelpfulUser(rs.getString(Constant.COLUMN_HELPFUL_USER));
            question.setCategoryId(rs.getInt(Constant.COLUMN_CATE_USER));
            questions.add(question);
        }
        dbAccess.closeAll();
        return questions;
    }

    /**
     * 質問データ取得メソッド
     * @param id 質問ID
     * @return 質問データオブジェクト
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Question findQuestion(int id) throws ClassNotFoundException, SQLException {
        Question question = null;
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_FIND);
        pstmt.setInt(1, id);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            question = new Question();
            question.setId(rs.getInt(Constant.COLUMN_ID));
            question.setTitle(replaceInput(rs.getString(Constant.COLUMN_QUE_TITLE)));
            question.setContent(replaceInput(rs.getString(Constant.COLUMN_CONTENT)));
            question.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            question.setCreatedOn(rs.getTimestamp(Constant.COLUMN_CREATE));
            question.setUpdatedOn(rs.getTimestamp(Constant.COLUMN_UPDATE));
            question.setBestAnsId(rs.getInt(Constant.COLUMN_BEAT_ANS_ID));
            question.setHelpfulAnsId(rs.getInt(Constant.COLUMN_HELPFUL_ANS_ID));
            question.setHelpfulUser(rs.getString(Constant.COLUMN_HELPFUL_USER));
            question.setCategoryId(rs.getInt(Constant.COLUMN_CATE_USER));
        }
        dbAccess.closeAll();
        return question;
    }

    /**
     * 質問の投稿、更新メソッド
     * @param question 質問データオブジェクト
     * @return 変更データ件数
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int saveQuestion(Question question) throws ClassNotFoundException, SQLException {
        if (question.getId() != 0) {
            return updateQuestion(question);
        } else {
            return insertQuestion(question);
        }
    }

    //質問を更新するメソッド
    private int updateQuestion(Question question) throws ClassNotFoundException, SQLException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_UPDATE);
        pstmt.setString(1, question.getTitle());
        pstmt.setString(2, question.getContent());
        pstmt.setInt(3, question.getCreateUserId());
        pstmt.setInt(4, question.getCategoryId());
        pstmt.setInt(5, question.getId());
        int cnt = dbAccess.exeUpdate(pstmt);
        dbAccess.closeTwo();
        return cnt;
    }

    //質問を投稿するメソッド
    private int insertQuestion(Question question) throws ClassNotFoundException, SQLException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_INSERT);
        pstmt.setString(1, question.getTitle());
        pstmt.setString(2, question.getContent());
        pstmt.setInt(3, question.getCreateUserId());
        pstmt.setInt(4, question.getCategoryId());
        int cnt = dbAccess.exeUpdate(pstmt);
        dbAccess.closeTwo();
        return cnt;
    }

    //ベストアンサー情報を登録するメソッド
    public void updateBestAns(Question question) throws ClassNotFoundException, SQLException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_BEST_ANS_UPDATE);
        pstmt.setInt(1, question.getBestAnsId());
        pstmt.setInt(2, question.getId());
        dbAccess.exeUpdate(pstmt);
    }

    //助かった！情報を登録するメソッド
    public void updateHelpfulAns(Question question) throws ClassNotFoundException, SQLException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_QUE_HELPFUL_ANS_UPDATE);
        pstmt.setInt(1, question.getHelpfulAnsId());
        pstmt.setString(2, question.getHelpfulUser());
        pstmt.setInt(3, question.getId());
        dbAccess.exeUpdate(pstmt);
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