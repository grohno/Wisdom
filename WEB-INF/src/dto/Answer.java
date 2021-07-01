package dto;

import java.sql.Timestamp;

/**
 * 回答テーブルデータオブジェクト
 * @author master
 * @version 1.0
 */
public class Answer {
    /**
     * 回答ID
     */
    private int id;
    /**
     * 回答ユーザID
     */
    private int createUserId;
    /**
     * 回答作成日時
     */
    private Timestamp createdAt;
    /**
     * 回答更新日時
     */
    private Timestamp updatedAt;
    /**
     * 回答内容
     */
    private String content;
    /**
     * 質問ID
     */
    private int questionId;
    /**
     * 助かった！カウント
     */
    private int helpfulCount;


    /**
     * @return 回答ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 回答ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return 回答ユーザID
     */
    public int getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId 回答ユーザID
     */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return 回答作成日時
     */
    public Timestamp getCreatedOn() {
        return createdAt;
    }

    /**
     * @param createdAt 回答作成日時
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return 回答更新日時
     */
    public Timestamp getUpdatedOn() {
        return updatedAt;
    }

    /**
     * @param updatedAt 回答更新日時
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return 回答内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 回答内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 質問ID
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId 質問ID
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    /**
     * @return 助かった！カウント
     */
    public int getHelpfulCount() {
        return helpfulCount;
    }

    /**
     * @param helpfulCount 助かった！カウント
     */
    public void setHelpfulCount(int helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

}