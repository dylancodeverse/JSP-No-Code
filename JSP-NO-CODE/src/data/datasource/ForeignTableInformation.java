package data.datasource;

public class ForeignTableInformation {

    boolean isUnique;

    String tableName;

    String keyColumn;

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

}
