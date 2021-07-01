package dto;

import java.util.ArrayList;

/**
 * (質問とユーザ)と(回答とユーザ)の関連データオブジェクト
 * @author master
 * @version 1.0
 */
public class QuestionAnswersRelation {
    /**
     * 質問とユーザの関連オブジェクト
     */
    private QuestionUserRelation questionUserRelation;
    /**
     * 回答とユーザの関連オブジェクト
     */
    private ArrayList<AnswerUserRelation> answerUserRelation;

    /**
     * @return 質問とユーザの関連オブジェクト
     */
    public QuestionUserRelation getQuestionUserRelation() {
        return questionUserRelation;
    }

    /**
     * @param questionUserRelation 質問とユーザの関連オブジェクト
     */
    public void setQuestionUserRelation(QuestionUserRelation questionUserRelation) {
        this.questionUserRelation = questionUserRelation;
    }

    /**
     * @return 回答とユーザの関連オブジェクト
     */
    public ArrayList<AnswerUserRelation> getAnswerUserRelation() {
        return answerUserRelation;
    }

    /**
     * @param answerUserRelation 回答とユーザの関連オブジェクト
     */
    public void setAnswerUserRelation(ArrayList<AnswerUserRelation> answerUserRelation) {
        this.answerUserRelation = answerUserRelation;
    }
}