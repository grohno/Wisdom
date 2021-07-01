package dto;

/**
 * 回答とユーザの関連データオブジェクト
 * @author master
 * @version 1.0
 */
public class AnswerUserRelation {
    /**
     * 回答データオブジェクト（Answerクラスのインスタンス化）
     */
    private Answer answer;
    /**
     * ユーザデータオブジェクト
     */
    private User user;

    /**
     * @return 回答データオブジェクト
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * @param answer 回答データオブジェクト
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
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