package ru.mail;

import java.util.Collection;

public class XMLSerializer extends ObjectSerializer {
    public void visit(Object value) {
        String root = "root";
        if (value != null) {
            Class cls = value.getClass();
            root = cls.getName();
        }
        writeKeyed(root, value, true);
    }

    @Override()
    void visitString(String value) {
        // neither the most effecient nor correct way to escape
        // but should be enough for printable characters
        write(
            value
                .replaceAll("&", "&amp;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
        );
    }

    void writeKeyed(String keyName, Object value, boolean isLast) {
        write("<" + keyName + ">");
        visitValue(value);
        write("</" + keyName + ">");
        if (!isLast) write('\n');
    }

    @Override()
    void enterCollection(Collection value) {
        super.enterCollection(value);
        write('\n');
    }

    @Override()
    void leaveCollection(Collection value) {
        write('\n');
        super.leaveCollection(value);
    }

    @Override()
    void visitIndexValue(int index, Object value, boolean isLast) {
        super.visitIndexValue(index, value, isLast);
        writeKeyed(Integer.toString(index + 1), value, isLast);
    }

    @Override()
    void enterObject(Object value) {
        super.enterObject(value);
        write('\n');
    }

    @Override()
    void leaveObject(Object value) {
        write('\n');
        super.leaveObject(value);
    }

    @Override()
    void visitKeyValue(String keyName, Object value, boolean isLast) {
        super.visitKeyValue(keyName, value, isLast);
        writeKeyed(keyName, value, isLast);
    }
}
