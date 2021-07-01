package dto;

import java.sql.Timestamp;

/**
 * ユーザデータオブジェクト
 * @author master
 * @version 1.0
 */
public class User {
    /** ユーザID */
    private int id;
    /** ログインEmail */
    private String email;
    /** ユーザ名 */
    private String name;
    /** パスワードハッシュ値 */
    private String encryptPassword;
    /** パスワードSalt値 */
    private String salt;
    /** ブックマーク情報 */
    private String bookmark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** ユーザ作成日時 */
    private Timestamp createdAt;
    /** ユーザ更新日時 */
    private Timestamp updatedAt;


    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

}