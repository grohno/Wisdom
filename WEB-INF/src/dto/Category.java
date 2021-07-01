package dto;

/**
 * カテゴリーテーブルデータオブジェクト
 * @author master
 * @version 1.0
 */

public class Category {

    /**
     * カテゴリーID
     */
    private int id;

    /**
    * カテゴリー名
    */
    private String name;

    /**
     * @return カテゴリーID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id カテゴリーID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return カテゴリー名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name カテゴリー名
     */
    public void setName(String name) {
        this.name = name;
    }

}
