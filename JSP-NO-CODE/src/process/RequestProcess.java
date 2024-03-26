package process;

import java.util.ArrayList;

import data.datasource.DataSource;
import process.results.Result;
import tmplFile.Children;
import tmplFile.Tmpl;

public class RequestProcess {

    Tmpl globalVariable;
    Tmpl instruction;
    String langage;
    DataSource dataSource;

    /**
     * Constructeur
     * 
     * @param dataSource
     * @param global_variable
     * @param template
     * @throws Exception
     */
    public RequestProcess(DataSource dataSource, String global_variable, String template)
            throws Exception {
        // chargement du variable global
        globalVariable = new Tmpl(global_variable);
        // chargement du template de l'utilisateur
        instruction = new Tmpl(template);
        // mi charge hoe inona ilay langage n'ilay template
        this.langage = instruction.getChild().getChildren()[0].getNode();
        // mi charge an'ilay source de donnee (sady mi-config avy hatrany ny type mi
        // correspondre am'ilay langage ho an'ny attribut tsirairay)
        setDataSource(dataSource);

    }

    public String getResultstAsString() throws Exception {
        Result[] results = getResults();
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : results) {
            stringBuilder.append(result.getResult());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Result[] getResults() throws Exception {
        // initialisation de resultat:
        ArrayList<Result> resultsAList = new ArrayList<>();
        // alaina ireo instructions rehetra anatinle template
        Children[] instructions = instruction.getChild().getChildren()[0].getChildren();
        // (amle eo ambony io: child: root>langage>instructions[] )

        // mandeha amzay lay procedure:
        for (int i = 0; i < instructions.length; i++) {
            try {
                // alaina ny syntaxe equivalent alony
                String syntax = search(new String[] { instructions[i].getNode() });
                // raikitra amzay
                resultsAList.add(replaceProcess(instructions[i], syntax));
            } catch (Exception e) {
                throw new Exception("Erreur sur le:" + (i + 1) + "eme objet de template avec " + e);
            }
        }

        return resultsAList.toArray(new Result[resultsAList.size()]);
    }

    private Result replaceProcess(Children children, String syntax) throws Exception {
        Result s = new Result();
        s.setOriginNode(children.getNode());

        // misy instruction de boucle ve
        if (loopInstructionIsPresent(children)) {
            s.setResultWithLoopInstruction(children, dataSource, syntax);
        } else {
            // raha tsy misy
            s.setResultWithoutLoopInstruction(children, dataSource, syntax);
        }

        return s;
    }

    private boolean loopInstructionIsPresent(Children children) {
        String value = children.getValue();
        return value.contains("< loop") || value.contains("<loop");
    }

    public void setDataSource(DataSource dataSource) throws Exception {
        for (int i = 0; i < dataSource.getAttributes().length; i++) {
            try {
                dataSource.getAttributes()[i]
                        .setNewType(
                                search(new String[] { "SQLType", dataSource.getAttributes()[i].getTypeSQL() }).trim());

            } catch (Exception e) {
                System.out.println("Type introuvable  pour: " + dataSource.getAttributes()[i].getTypeSQL());
            }
            try {
                dataSource.getAttributes()[i].setPossibleImport(
                        search(new String[] { "types", dataSource.getAttributes()[i].getNewType() }));

            } catch (Exception e) {
                System.out.println("Pas de import possible pour: " + dataSource.getAttributes()[i].getNewType());
            }
            try {

                dataSource.getAttributes()[i]
                        .setHTMLType(
                                search(new String[] { "HTMLType", dataSource.getAttributes()[i].getTypeSQL() })
                                        .trim());
            } catch (Exception e) {
                System.out.println("Pas de HTMLType  pour: " + dataSource.getAttributes()[i].getTypeSQL());
            }
        }
        this.dataSource = dataSource;
    }

    /**
     * Search method
     * 
     * @param langage
     * @param toSearch
     * @return
     * @throws Exception
     */
    private String search(String[] toSearch) throws Exception {
        String langage = this.langage;
        Children c = globalVariable.search(toSearch);
        try {
            // Trim miala
            // return c.BFS(langage).getValue().trim();
            return c.BFS(langage).getValue();
        } catch (Exception e) {
            while (langage.contains("-")) {
                langage = langage.substring(0, langage.lastIndexOf("-"));
                try {
                    // return c.BFS(langage).getValue().trim();
                    return c.BFS(langage).getValue();

                } catch (Exception e1) {
                }
            }
        }

        // return c.BFS("All").getValue().trim();
        return c.BFS("All").getValue();

    }
}
