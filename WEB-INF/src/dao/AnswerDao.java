package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbAccess;
import dto.Answer;
import util.Constant;

/**
 * 回答テーブルアクセスオブジェクト
 * @author master
 * @version 1.0
 */
public class AnswerDao {
    /**
     * 質問IDに対する回答データを取得するメソッド
     * @param id 質問ID
     * @return 回答データリスト
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Answer> findAnswerByQuestion(int id) throws ClassNotFoundException, SQLException {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_ANS_FIND);
        pstmt.setInt(1, id);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            Answer answer = new Answer();
            answer = new Answer();
            answer.setId(rs.getInt(Constant.COLUMN_ID));
            answer.setContent(replaceInput(rs.getString(Constant.COLUMN_CONTENT)));
            answer.setCreateUserId(rs.getInt(Constant.COLUMN_CREATE_USER));
            answer.setCreatedAt(rs.getTimestamp(Constant.COLUMN_CREATE));
            answer.setUpdatedAt(rs.getTimestamp(Constant.COLUMN_UPDATE));
            answer.setHelpfulCount(rs.getInt(Constant.COLUMN_HELPFUL_COUNT));
            answers.add(answer);
        }
        dbAccess.closeAll();
        return answers;
    }

    /**
     * 回答データを登録/更新するメソッド
     * @param answer 回答データオブジェクト
     * @return 登録/更新データ件数
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int saveAnswer(Answer answer) throws SQLException, ClassNotFoundException {
        if (answer.getId() != 0) {
            return updateAnswer(answer);
        } else {
            return insertAnswer(answer);
        }
    }

    //データ更新メソッド
    private int updateAnswer(Answer answer) throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_ANS_UPDATE);
        pstmt.setString(1, answer.getContent());
        pstmt.setInt(2, answer.getCreateUserId());
        pstmt.setInt(3, answer.getQuestionId());
        pstmt.setInt(4, answer.getId());
        int cnt = dbAccess.exeUpdate(pstmt);
        dbAccess.closeTwo();
        return cnt;
    }

    //データ登録メソッド
    private int insertAnswer(Answer answer) throws SQLException, ClassNotFoundException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_ANS_INSERT);
        pstmt.setString(1, answer.getContent());
        pstmt.setInt(2, answer.getCreateUserId());
        pstmt.setInt(3, answer.getQuestionId());
        int cnt = dbAccess.exeUpdate(pstmt);
        dbAccess.closeTwo();
        return cnt;
    }

    //助かった！情報を登録するメソッド
    public void updateHelpfulCount(Answer answer) throws ClassNotFoundException, SQLException {
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_HELPFUL_COUNT_ANS_UPDATE);
        pstmt.setInt(1, answer.getHelpfulCount());
        pstmt.setInt(2, answer.getId());
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