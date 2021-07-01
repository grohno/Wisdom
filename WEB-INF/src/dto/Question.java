package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * 質問テーブルデータオブジェクト
 * @author master
 * @version 1.0
 */
public class Question {
    /**
     * 質問ID
     */
    private int id;
    /**
     * 質問作成ユーザID
     */
    private int createUserId;
    /**
     * ベストアンサーID
     */
    private int bestAnsId;
    /**
     * 助かった！回答ID
     */
    private int helpfulAnsId;
    /**
     * 助かった！押下ユーザー
     */
    private String helpfulUser;
    /**
     * カテゴリーID
     */
    private int categoryId;
    /**
     * 質問作成日時
     */
    private Timestamp createdAt;
    /**
     * 質問更新日時
     */
    private Timestamp updatedAt;
    /**
     * 質問タイトル
     */
    private String title;
    /**
     * 質問内容
     */
    private String content;
    /**
     * 回答内容
     */
    private ArrayList<Answer> answers;

    /**
     * @return 質問ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 質問ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return 質問作成ユーザID
     */
    public int getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId 質問作成ユーザID
     */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return ベストアンサーID
     */
    public int getBestAnsId() {
        return bestAnsId;
    }

    /**
     * @param bestAnsId ベストアンサーID
     */
    public void setBestAnsId(int bestAnsId) {
        this.bestAnsId = bestAnsId;
    }

    /**
     * @return 助かった！回答ID
     */
    public int getHelpfulAnsId() {
        return helpfulAnsId;
    }

    /**
     * @param helpfulAnsId 助かった！回答ID
     */
    public void setHelpfulAnsId(int helpfulAnsId) {
        this.helpfulAnsId = helpfulAnsId;
    }

    /**
     * @return 助かった！押下ユーザー
     */
    public String getHelpfulUser() {
        return helpfulUser;
    }

    /**
     * @param helpfulCount 助かった！押下ユーザー
     */
    public void setHelpfulUser(String helpfulUser) {
        this.helpfulUser = helpfulUser;
    }

    /**
     * @return カテゴリーID
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId カテゴリーID
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return 質問作成日時
     */
    public Timestamp getCreatedOn() {
        return createdAt;
    }

    /**
     * @param createdAt 質問作成日時
     */
    public void setCreatedOn(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return 質問更新日時
     */
    public Timestamp getUpdatedOn() {
        return updatedAt;
    }

    /**
     * @param updatedAt 質問更新日時
     */
    public void setUpdatedOn(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return 質問タイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 質問タイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 質問内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 質問内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 回答内容
     */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    /**
     * @param answers 回答内容
     */
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}