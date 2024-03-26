package overriden;

import data.datasource.DataSource;
import data.process.SQLDataHandler;
import process.MainProcess;

public class MainProcessNew extends MainProcess {

    
    @Override
    public DataSource[] getDataSources(String url, String user, String paswd) throws Exception {
        DataSource[] dataSources = new SQLDataHandler(url, user,
                paswd).getDataSources();
        DataSourceNew[] dataSourceNew = new DataSourceNew[dataSources.length];

        for (int i = 0; i < dataSources.length; i++) {
            DataSourceNew dataSourceNew2 = new DataSourceNew();
            dataSourceNew2.setAttributes(dataSources[i].getAttributes());
            dataSourceNew2.setLabel(dataSources[i].getLabel());

            dataSourceNew[i] = dataSourceNew2;
        }

        return dataSourceNew;
    }
}
