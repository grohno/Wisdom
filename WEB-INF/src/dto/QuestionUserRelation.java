package dto;

/**
 * 質問とユーザの関連データオブジェクト
 * @author master
 * @version 1.0
 */
public class QuestionUserRelation {
    /**
     * 質問データオブジェクト
     */
    private Question question;
    /**
     * ユーザデータオブジェクト
     */
    private User user;

    /**
     * @return 質問データオブジェクト
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * @param question 質問データオブジェクト
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * @return ユーザデータオブジェクト
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user ユーザデータオブジェクト
     */
    public void setUser(User user) {
        this.user = user;
    }
}