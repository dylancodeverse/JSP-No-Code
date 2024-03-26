package process.results;

import java.util.List;

import data.datasource.DataSource;
import tmplFile.Children;

public class Result {

    String originNode;
    String result;

    public String getOriginNode() {
        return originNode;
    }

    public void setOriginNode(String originNode) {
        this.originNode = originNode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setResultWithoutLoopInstruction(Children children, DataSource dataSource, String syntax)
            throws Exception {
        // initialisation de result par rapport au syntaxe
        setResult(syntax);
        // recuperer toutes les lignes
        String s = children.getValue();
        List<String> lines = s.lines().toList();
        // remplacement pour chaque ligne
        for (String string : lines) {
            // exemple : #package# = modeles
            // alaina chaque membre anle egalite
            String[] membres = string.split("=", 2);
            membres[0] = membres[0].trim();
            membres[1] = membres[1].trim();
            // raha toa ka mampiasa anle datasource:
            if (string.contains("$datasources$")) {
                String finalSyntax = "";
                while (!membres[1].isBlank()) {
                    if (membres[1].startsWith("$datasources$.")) {
                        membres[1] = membres[1].replaceFirst("\\$datasources\\$\\.", "");
                        String methodsToInvoke = membres[1].substring(0, membres[1].indexOf("$"));
                        membres[1] = membres[1].substring(membres[1].indexOf("$") + 1);
                        String resultMethod = dataSource.get(methodsToInvoke);
                        finalSyntax += resultMethod;
                    } else {
                        if (membres[1].contains("$")) {
                            finalSyntax += membres[1].substring(0, membres[1].indexOf("$"));
                            membres[1] = membres[1].substring(membres[1].indexOf("$"));
                        } else {
                            finalSyntax += membres[1];
                            membres[1] = "";
                        }
                    }
                }
                setResult(getResult().replaceAll(membres[0], finalSyntax));
            } else {
                setResult(getResult().replaceAll(membres[0], membres[1]));
            }
        }
    }

    public void setResultWithLoopInstruction(Children children, DataSource dataSource, String syntax) throws Exception {

        setResult("");
        String[] resultatDuBoucle = null;
        // jerena hoe mi boucle datasource ve sa tsia:

        String s = children.getValue();
        if (s.contains("loop each")) {
            // alaina ilay zavatra ho bouclena :
            String var2 = s.substring(s.indexOf("$"), s.indexOf(":")).trim();
            // alaina tao anaty datasources tao ilay liste
            Object[] objects = ((Object[]) dataSource.getAsObject(var2));
            // initialisation resultat
            resultatDuBoucle = new String[objects.length];
            for (int i = 0; i < resultatDuBoucle.length; i++) {
                resultatDuBoucle[i] = syntax;
            }
            // initialisation du model
            String[] resultatDuBoucleModel = new String[objects.length];
            String alias = s.substring(s.indexOf(":") + 1, s.indexOf(">")).trim();
            String resultatAsString = s.substring(s.indexOf(">") + 1, s.indexOf("<loop/>")).trim();
            // initialisation du resultatduboucle model
            for (int i = 0; i < resultatDuBoucleModel.length; i++) {
                resultatDuBoucleModel[i] = resultatAsString.replace(alias, var2 + "[" + i + "]");
            }
            // MANDEHA AMZAY NY TENA PROCESSUS DE CHANGEMENT POUR CHAQUE
            // resulatDuBoucleModel
            for (int i = 0; i < resultatDuBoucleModel.length; i++) {
                // mbola tsy implementeko alony ny cas hoe misy IF ilay izy
                resultatDuBoucleModel[i] = resultatDuBoucleModel[i].trim();
                List<String> lines = resultatDuBoucleModel[i].lines().toList();
                for (String string : lines) {
                    if (!string.contains("="))
                        continue;

                    String[] splited = string.split("=", 2);
                    splited[0] = splited[0].trim();
                    splited[1] = splited[1].trim();
                    if (splited[1].contains("$datasources$")) {
                        String finalSyntax = "";
                        while (!splited[1].isBlank()) {
                            if (splited[1].startsWith("$datasources$.")) {
                                splited[1] = splited[1].replace("$datasources$.", "");
                                String methodsToInvoke = splited[1].substring(0, splited[1].indexOf("$"));
                                splited[1] = splited[1].substring(splited[1].indexOf("$") + 1);
                                String resultMethod = dataSource.get(methodsToInvoke);
                                finalSyntax += resultMethod;
                            } else {
                                if (splited[1].contains("$")) {
                                    finalSyntax += splited[1].substring(0, splited[1].indexOf("$"));
                                    splited[1] = splited[1].substring(splited[1].indexOf("$"));

                                } else {
                                    finalSyntax += splited[1];
                                    splited[1] = "";
                                }
                            }
                        }
                        resultatDuBoucle[i] = resultatDuBoucle[i].replaceAll(splited[0], finalSyntax);
                    } else {
                        resultatDuBoucle[i] = resultatDuBoucle[i].replaceAll(splited[0], splited[1]);
                    }
                }

            }

            
        } else {
            // alaina alony ny information ana lignes anle loop
            String var1 = s.substring(s.indexOf("loop"));
            // alaina ny ligne voalohany amn io
            String var2 = var1.lines().toList().get(0);
            // Alaina ny nombre anle boucle:
            Integer nbLoop = Integer.parseInt(var2.split("=", 2)[1].trim());
            // initialisation de resultat:
            resultatDuBoucle = new String[nbLoop];
            for (int i = 0; i < resultatDuBoucle.length; i++) {
                resultatDuBoucle[i] = syntax;
            }

            // tant que misy # sy <loop/>
            String copyOfs = s;
            while (copyOfs.contains("#") && copyOfs.contains("<loop/>")) {
                String stringToOperateNow = copyOfs.substring(copyOfs.indexOf("#"), copyOfs.indexOf("<loop/>"));
                // fafaina daholo ireo zay tsy ao anatinle stringToOperateNow :
                copyOfs = stringToOperateNow;

                String[] membres = stringToOperateNow.split("=", 2);
                membres[0] = membres[0].trim();
                List<String> lss = membres[1].lines().toList();
                int index = 0;
                for (String string : lss) {
                    if (!string.contains("<loop>") && !string.isBlank()) {
                        try {
                            resultatDuBoucle[index] = resultatDuBoucle[index].replaceAll(membres[0], string.trim());
                            index++;
                        } catch (IndexOutOfBoundsException e) {
                            throw new Exception("Tsy normal anie ny isana ligne anareo zany e:" + e.getMessage());
                        }
                    }
                }
            }
        }

        // jerena amzay ny separateur
        String var1;
        try {
            var1 = s.substring(s.indexOf("separator"));

        } catch (java.lang.StringIndexOutOfBoundsException e) {
            throw new Exception("Separator not present");
        }
        // alaina ny ligne voalohany amn io
        String var2 = var1.lines().toList().get(0);
        // alaina le type de separateur
        String separateur = var2.split("=", 2)[1];

        if (separateur.trim().equalsIgnoreCase("concat")) {
            for (String string : resultatDuBoucle) {
                setResult(getResult() + string);
            }
        } else if (separateur.trim().equalsIgnoreCase("line")) {
            for (String string : resultatDuBoucle) {
                setResult(getResult() + string + "\n");
            }
        }
    }
}
