package data.datasource;

public class ForeignKeyInformation {

    boolean isUnique;

    String tableOrigine;

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public String getTableOrigine() {
        return tableOrigine;
    }

    public String getTableOrigineUpFirst() {
        return tableOrigine.substring(0, 1).toUpperCase() + tableOrigine.substring(1);
    }

    public void setTableOrigine(String tableOrigine) {
        this.tableOrigine = tableOrigine;
    }
}
