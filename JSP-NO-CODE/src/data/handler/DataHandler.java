package data.handler;

import data.datasource.DataSource;

public interface DataHandler {
    public abstract DataSource[] getDataSources() throws Exception;
}
