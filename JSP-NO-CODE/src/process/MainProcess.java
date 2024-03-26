package process;

import java.util.ArrayList;

import data.datasource.DataSource;
import data.process.SQLDataHandler;
import process.file.TextProcess;
import tmplFile.Children;
import tmplFile.Tmpl;

public class MainProcess {

    /**
     * Raha te hanampy fonctionnalites amle DataSource (Donc mi extends de mi creer
     * fonction)
     * dia mila overridena ity getDataSources dia atao mi return anle classe avy no
     * creer-na
     * dia iny no ampiasainle
     * criptProcess amzay
     * 
     * @param url
     * @param user
     * @param paswd
     * @return
     * @throws Exception
     */
    public DataSource[] getDataSources(String url, String user, String paswd) throws Exception {
        DataSource[] dataSources = new SQLDataHandler(url, user,
                paswd).getDataSources();
        return dataSources;
    }

    public void scriptProcessOnePage(String generationFile, String templateName, String[] table, String pagePath,
            String ref, String outPutName) throws Exception {
        Tmpl tmpl = new Tmpl(generationFile);
        // from configuration
        String url = tmpl.getChild().BFS("url").getValue().trim();
        String user = tmpl.getChild().BFS("user").getValue().trim();
        String paswd = tmpl.getChild().BFS("password").getValue().trim();
        String globV = tmpl.getChild().BFS("global_variable").getValue().trim();
        String templateFile = tmpl.search("template", templateName).getValue().trim();
        String fileExtension = search(tmpl, new String[] { "fileExtension" }, templateName).trim();
        String outputPath = search(tmpl, new String[] { "outputPath" }, templateName).trim();
        String fileNameSuffix = search(tmpl, new String[] { "fileNameSuffix" }, templateName).trim();
        // ----------------------------------------
        // process
        DataSource[] dataSources = getDataSources(url, user, paswd);

        if (table.length == 0) {
            System.out.println("No file generated because no table given");
        }
        ArrayList<String> lsResult = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (!table[i].equals("*")) {
                DataSource dataSource = null;
                for (int j = 0; j < dataSources.length; j++) {
                    if (dataSources[j].getLabel().equalsIgnoreCase(table[i]))
                        dataSource = dataSources[j];
                }
                if (dataSource == null) {
                    throw new Exception("No table named " + table[i] + " found");
                }
                RequestProcess requestProcess = new RequestProcess(dataSource,
                        globV,
                        templateFile);
                String fileName = dataSource.getLabelUpFirst();
                if (!fileNameSuffix.isBlank()) {
                    fileName = fileName + fileNameSuffix;
                }
                lsResult.add(requestProcess.getResultstAsString());
                System.out.println(fileName + " build ok!");

            } else {
                lsResult = new ArrayList<>();
                for (int j = 0; j < dataSources.length; j++) {
                    RequestProcess requestProcess = new RequestProcess(dataSources[j],
                            globV,
                            templateFile);
                    String fileName = dataSources[j].getLabelUpFirst();
                    if (!fileNameSuffix.isBlank()) {
                        fileName = fileName + fileNameSuffix;
                    }
                    lsResult.add(requestProcess.getResultstAsString());
                }
                break;
            }
        }
        // fusionner les resultats:
        String x = "";
        for (String string : lsResult) {
            x += string;
        }
        // charger le contenu template
        String finall = TextProcess.readFromFile(pagePath);
        finall = finall.replaceAll(ref, x);
        TextProcess.writeTextToFile(finall, outputPath + outPutName + fileExtension);

    }

    public void scriptProcess(String generationFile, String templateName, String[] table) throws Exception {

        Tmpl tmpl = new Tmpl(generationFile);
        // from configuration
        String url = tmpl.getChild().BFS("url").getValue().trim();
        String user = tmpl.getChild().BFS("user").getValue().trim();
        String paswd = tmpl.getChild().BFS("password").getValue().trim();
        String globV = tmpl.getChild().BFS("global_variable").getValue().trim();
        String templateFile = tmpl.search("template", templateName).getValue().trim();
        String fileExtension = search(tmpl, new String[] { "fileExtension" }, templateName).trim();
        String outputPath = search(tmpl, new String[] { "outputPath" }, templateName).trim();
        String fileNameSuffix = search(tmpl, new String[] { "fileNameSuffix" }, templateName).trim();
        // ----------------------------------------
        // process
        DataSource[] dataSources = getDataSources(url, user, paswd);

        if (table.length == 0) {
            System.out.println("No file generated because no table given");
        }

        for (int i = 0; i < table.length; i++) {
            if (!table[i].equals("*")) {
                DataSource dataSource = null;
                for (int j = 0; j < dataSources.length; j++) {
                    if (dataSources[j].getLabel().equalsIgnoreCase(table[i]))
                        dataSource = dataSources[j];
                }
                if (dataSource == null) {
                    throw new Exception("No table named " + table[i] + " found");
                }
                RequestProcess requestProcess = new RequestProcess(dataSource,
                        globV,
                        templateFile);
                String fileName = dataSource.getLabelUpFirst();
                if (!fileNameSuffix.isBlank()) {
                    fileName = fileName + fileNameSuffix;
                }
                TextProcess.writeTextToFile(requestProcess.getResultstAsString(),
                        outputPath + fileName + fileExtension);
                System.out.println(fileName + fileExtension + " generated successfully!");

            } else {
                for (int j = 0; j < dataSources.length; j++) {
                    RequestProcess requestProcess = new RequestProcess(dataSources[j],
                            globV,
                            templateFile);
                    String fileName = dataSources[j].getLabelUpFirst();
                    if (!fileNameSuffix.isBlank()) {
                        fileName = fileName + fileNameSuffix;
                    }
                    TextProcess.writeTextToFile(requestProcess.getResultstAsString(),
                            outputPath + fileName + fileExtension);
                }
                break;
            }
        }
        System.out.println("Generated successfully!!!");

    }

    private String search(Tmpl tmpl, String[] toSearch, String templateName) throws Exception {
        Children c = tmpl.search(toSearch);
        try {
            return c.BFS(templateName).getValue();
        } catch (Exception e) {
            while (templateName.contains("-")) {
                templateName = templateName.substring(0, templateName.lastIndexOf("-"));
                try {
                    // return c.BFS(templateName).getValue().trim();
                    return c.BFS(templateName).getValue();

                } catch (Exception e1) {
                }
            }
        }
        return c.BFS("All").getValue();
    }
}
