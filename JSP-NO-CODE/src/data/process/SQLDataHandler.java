package data.process;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.datasource.Attribute;
import data.datasource.DataSource;
import data.datasource.ForeignKeyInformation;
import data.datasource.ForeignTableInformation;
import data.handler.DataHandler;

public class SQLDataHandler implements DataHandler {

    private String url;
    private String password ;
    private String user;

    public SQLDataHandler(String url , String user ,String password){
        setPassword(password);
        setUrl(url);
        setUser(user);
    }


    @Override
    public DataSource[] getDataSources() throws Exception {
        // Liste pour stocker les sources de données
        List<DataSource> dataSources = new ArrayList<>();

        // Établir une connexion à la base de données
        try (Connection connection = DriverManager.getConnection(getUrl(),getUser(),getPassword())) {
            // Récupérer les métadonnées de la base de données
            DatabaseMetaData metaData = connection.getMetaData();

            // Récupérer les noms de toutes les tables dans la base de données
            ResultSet tables = metaData.getTables(null, null, "%", new String[] { "TABLE" });
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");

                // Créer une nouvelle source de données
                DataSource dataSource = new DataSource();
                dataSource.setLabel(tableName);

                // Récupérer les colonnes de la table
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                List<Attribute> attributes = new ArrayList<>();
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");

                    // Créer un nouvel attribut avec les informations récupérées
                    Attribute attribute = new Attribute();
                    attribute.setAttrName(columnName);
                    attribute.setTypeSQL(columnType);

                    // Vérifier si l'attribut fait partie de la clé primaire
                    ResultSet primaryKey = metaData.getPrimaryKeys(null, null, tableName);
                    while (primaryKey.next()) {
                        String primaryKeyColumnName = primaryKey.getString("COLUMN_NAME");
                        if (columnName.equals(primaryKeyColumnName)) {
                            attribute.setPrimaryKey(true);
                            break;
                        }
                    }

                    // Vérifier si l'attribut fait partie d'une clé étrangère
                    ResultSet importedKeys = metaData.getImportedKeys(null, null, tableName);
                    while (importedKeys.next()) {
                        String foreignKeyName = importedKeys.getString("FKCOLUMN_NAME");
                        if (columnName.equals(foreignKeyName)) {
                            ForeignKeyInformation foreignKeyInformation = new ForeignKeyInformation();
                            // Utilisez la méthode isColumnUnique pour vérifier l'unicité de la colonne
                            boolean isUnique = isColumnUnique(connection, tableName, columnName);
                            foreignKeyInformation.setUnique(isUnique);
                            foreignKeyInformation.setTableOrigine(importedKeys.getString("PKTABLE_NAME"));
                            attribute.setForeignKeyInformation(foreignKeyInformation);
                            break;
                        }
                    }

                    // Ajouter l'attribut à la liste des attributs de la source de données
                    attributes.add(attribute);
                }

                // Recuperer les tables etrangeres

                ResultSet exportedKeysResultSet = metaData.getExportedKeys(null, null, tableName);

                while (exportedKeysResultSet.next()) {
                    String foreignTableName = exportedKeysResultSet.getString("FKTABLE_NAME");
                    String foreignColumnName = exportedKeysResultSet.getString("FKCOLUMN_NAME");

                    // Vérifier si la colonne est unique dans la table étrangère
                    boolean isUnique = isColumnUnique(connection, foreignTableName, foreignColumnName);

                    Attribute attribute = new Attribute();
                    ForeignTableInformation f = new ForeignTableInformation();
                    f.setKeyColumn(foreignColumnName);
                    f.setTableName(foreignTableName);
                    f.setUnique(isUnique);
                    attribute.setForeignTableName(f);

                    attributes.add(attribute);
                }
                // Convertir la liste d'attributs en tableau et l'ajouter à la source de données
                dataSource.setAttributes(attributes.toArray(new Attribute[attributes.size()]));

                // Ajouter la source de données à la liste des sources de données
                dataSources.add(dataSource);
            }
        }

        // Convertir la liste de sources de données en tableau et le retourner
        return dataSources.toArray(new DataSource[dataSources.size()]);
    }

    private static boolean isColumnUnique(Connection connection, String tableName, String columnName) throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet uniqueColumnsResultSet = metaData.getIndexInfo(null, null, tableName, false, true);

        while (uniqueColumnsResultSet.next()) {
            String uniqueColumnName = uniqueColumnsResultSet.getString("COLUMN_NAME");
            if (uniqueColumnName.equals(columnName)) {
                return true;
            }
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
