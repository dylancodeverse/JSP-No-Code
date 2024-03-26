package data.datasource;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class DataSource {

    String label;
    Attribute[] attributes;

    /**
     * Exemple de key :
     * $datasources$.getLabelUpFirst$
     * $datasources$.getAttributes[0].getAttrName$
     * $datasources$.getX.getAttrName$
     * 
     * @param key
     * @return
     * @throws Exception
     */
    // utile
    public String get(String key)
            throws Exception {
        Object op = this;
        // esorina alony voaloahany indrindra ilay :$datasources$.
        key = key.replace("$datasources$.", "");
        // alaina daholo ny methods ho antsoina tsikelikely
        String[] methods = key.split("\\.");
        if (methods.length == 0) {
            methods = new String[1];
            methods[0] = key;
        }
        for (String string : methods) {
            // initialisation
            Method method = null;
            Object result = null;
            // esorina $ raha misy (rehefa misy de midika izy zay fa farany )
            string = string.replace("$", "");
            // jerena hoe misy index ve
            if (string.contains("[")) {
                // raha misy de alaina le method katreo amle tsisy [
                method = getMethodWithSyntax(string.substring(0, string.indexOf("[")), op);
                result = method.invoke(op);
                // raha tableau
                if (result instanceof Object[]) {
                    Object[] asArray = ((Object[]) result);
                    // maka anle indice
                    int index = Integer.parseInt(string.substring(string.indexOf("[") + 1, string.indexOf("]")).trim());
                    result = asArray[index];
                } else {
                    throw new Exception("Tsy tableau ne");
                }
            } else {
                method = getMethodWithSyntax(string, op);
                result = method.invoke(op);
            }
            op = result;
        }

        if (op == null) {
            return "not defined";
        }

        return op.toString();
    }

    // utile
    public Object getAsObject(String key)
            throws Exception {
        Object op = this;
        // esorina alony voaloahany indrindra ilay :$datasources$.
        key = key.replace("$datasources$.", "");
        // alaina daholo ny methods ho antsoina tsikelikely
        String[] methods = key.split(".");
        if (methods.length == 0) {
            methods = new String[1];
            methods[0] = key;
        }
        for (String string : methods) {
            // initialisation
            Method method = null;
            Object result = null;
            // esorina $ raha misy (rehefa misy de midika izy zay fa farany )
            string = string.replace("$", "");
            // jerena hoe misy index ve
            if (string.contains("[")) {
                // raha misy de alaina le method katreo amle tsisy [
                method = getMethodWithSyntax(string.substring(0, string.indexOf("[")), op);
                result = method.invoke(op);
                // raha tableau
                if (result instanceof Object[]) {
                    Object[] asArray = ((Object[]) result);
                    // maka anle indice
                    int index = Integer.parseInt(string.substring(string.indexOf("[") + 1, string.indexOf("]")).trim());
                    result = asArray[index];
                } else {
                    throw new Exception("Tsy tableau ne");
                }
            } else {
                method = getMethodWithSyntax(string, op);
                result = method.invoke(op);
            }
            op = result;
        }
        if (op == null) {
            return "not defined";
        }
        return op;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributes = attributes;
    }

    // utile
    public static Method getMethodWithSyntax(String str, Object object)
            throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
        Method f = null;
        try {
            f = object.getClass().getDeclaredMethod(str);

        } catch (NoSuchMethodException e) {
            Class<?> cls = object.getClass();
            while (cls.getSuperclass() != null) {
                try {
                    f = cls.getSuperclass().getDeclaredMethod(str);
                    break;
                } catch (NoSuchMethodException e2) {
                    cls = cls.getSuperclass();
                }
            }
            if (f == null)
                throw e;
        }
        f.setAccessible(true);
        return f;
    }

    // implementation specifique :
    public String[] getPossibleImportForDataSources() {
        Set<String> setString = new HashSet<String>();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getPossibleImport() != null) {
                setString.add(attributes[i].getPossibleImport());
            }
        }
        return setString.toArray(new String[setString.size()]);
    }

    public String getLabelUpFirst() {
        return getLabel().substring(0, 1).toUpperCase() + getLabel().substring(1);
    }

}
