package overriden;

import java.util.ArrayList;


import data.datasource.Attribute;
import data.datasource.DataSource;

public class DataSourceNew extends DataSource {
    public Attribute[] getAttributesNoPrimaryKey() {
        Attribute[] aaAttributes = getAttributes();
        ArrayList<Attribute> a = new ArrayList<>();
        for (int i = 0; i < aaAttributes.length; i++) {
            if (!aaAttributes[i].isPrimaryKey() && aaAttributes[i].getForeignTableName() == null
                    && aaAttributes[i].getForeignKeyInformation() == null) {
                a.add(aaAttributes[i]);
            }
        }
        return a.toArray(new Attribute[a.size()]);
    }

    public Attribute getAttributeWithPK() {
        Attribute[] aaAttributes = getAttributes();
        for (int i = 0; i < aaAttributes.length; i++) {
            if (aaAttributes[i].isPrimaryKey()) {
                return aaAttributes[i];
            }
        }
        return null;
    }

    public Attribute[] getAttributesWithLabelFirst() {
        Attribute[] aaAttributes = getAttributes();
        for (int i = 0; i < aaAttributes.length; i++) {
            if (aaAttributes[i].getAttrName() != null)
                aaAttributes[i].setAttrName(upfirst(aaAttributes[i].getAttrName()));
        }
        return aaAttributes;
    }

    public Attribute[] getAttributesWithLabelFirstNoForeignTable() {
        Attribute[] aaAttributes = getAttributes();
        ArrayList<Attribute> ls = new ArrayList<>();
        for (int i = 0; i < aaAttributes.length; i++) {
            if (aaAttributes[i].getAttrName() != null && aaAttributes[i].getForeignTableName() == null
                    && aaAttributes[i].getForeignKeyInformation() == null) {
                aaAttributes[i].setAttrName(upfirst(aaAttributes[i].getAttrName()));
                ls.add(aaAttributes[i]);

            }
        }
        return ls.toArray(new Attribute[ls.size()]);
    }

    public String upfirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public Attribute[] getAttributesForeignClass() {
        Attribute[] aaAttributes = getAttributes();
        ArrayList<Attribute> arrayList = new ArrayList<>();
        for (int i = 0; i < aaAttributes.length; i++) {
            if (aaAttributes[i].getForeignKeyInformation() != null) {
                arrayList.add(aaAttributes[i]);
            }
        }
        return arrayList.toArray(new Attribute[arrayList.size()]);
    }
}
