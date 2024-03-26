package data.datasource;

public class Attribute {

    String attrName;

    String typeSQL;

    String newType;

    String possibleImport;

    String HTMLType;

    ForeignKeyInformation foreignKeyInformation;

    ForeignTableInformation foreignTableName;

    boolean isPrimaryKey;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getTypeSQL() {
        return typeSQL;
    }

    public void setTypeSQL(String typeSQL) {
        this.typeSQL = typeSQL;
    }

    public String getNewType() {
        if (newType == null)
            return "Not defined for :" + getTypeSQL();
        return newType;
    }

    public void setNewType(String newType) {

        this.newType = newType.trim();
    }

    public String getPossibleImport() {
        return possibleImport;
    }

    public void setPossibleImport(String possibleImport) {
        this.possibleImport = possibleImport.trim();
    }

    public ForeignKeyInformation getForeignKeyInformation() {
        return foreignKeyInformation;
    }

    public void setForeignKeyInformation(ForeignKeyInformation foreignKeyInformation) {
        this.foreignKeyInformation = foreignKeyInformation;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public ForeignTableInformation getForeignTableName() {
        return foreignTableName;
    }

    public void setForeignTableName(ForeignTableInformation foreignTableName) {
        this.foreignTableName = foreignTableName;
    }

    public String getHTMLType() {
        return HTMLType;
    }

    public void setHTMLType(String HTMLType) {
        this.HTMLType = HTMLType;
    }

    public String getAttrNameSimple() {
        return attrName.substring(0, 1).toLowerCase() + attrName.substring(1);
    }

    public String getAttrNameUpFirst() {
        return attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    }

}
