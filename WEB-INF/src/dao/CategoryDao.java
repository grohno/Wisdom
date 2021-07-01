package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbAccess;
import dto.Category;
import util.Constant;

/**
 * カテゴリーテーブルアクセスオブジェクト
 * @author master
 * @version 1.0
 */

public class CategoryDao {

    /**
     * カテゴリー一覧を取得するメソッド
     * @param id カテゴリーID
     * @return カテゴリー一覧リスト
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Category> getAllCategories() throws SQLException, ClassNotFoundException {
        ArrayList<Category> categories = new ArrayList<Category>();
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_CATE_SELECT);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt(Constant.COLUMN_ID));
            category.setName(rs.getString(Constant.COLUMN_CATE_NAME));
            categories.add(category);
        }
        dbAccess.closeAll();
        return categories;
    }


    /**
     * カテゴリー名取得メソッド
     * @param id カテゴリーID
     * @return カテゴリー名
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String categoryName(int id) throws ClassNotFoundException, SQLException {
        String categoryName = null;
        DbAccess dbAccess = new DbAccess();
        dbAccess.access();
        PreparedStatement pstmt = dbAccess.makePreparedStatement(Constant.SQL_CATE_FIND);
        pstmt.setInt(1, id);
        ResultSet rs = dbAccess.exeQuery(pstmt);
        while (rs.next()) {
            categoryName = rs.getString(Constant.COLUMN_CATE_NAME);
        }
        dbAccess.closeAll();
        return categoryName;
    }

}
