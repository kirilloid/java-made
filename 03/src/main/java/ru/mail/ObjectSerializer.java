package ru.mail;

import java.util.Collection;

public abstract class ObjectSerializer extends ObjectVisitor implements Serializer {
    private StringBuilder sb;
    private static final String INDENT = "  ";
    private int depth = 0;
    private Exception lastError = null;

    public String serialize(Object value) {
        sb = new StringBuilder();
        visit(value);
        return sb.toString();
    }

    protected void write(String s) {
        sb.append(s);
    }

    protected void write(char c) {
        sb.append(c);
    }

    abstract void visit(Object value);

    @Override
    void visitError(Exception e) {
        lastError = e;
    }

    @Override()
    void visitNull() {
        if (lastError != null) {
            write(lastError.getMessage());
            lastError = null;
        } else {
            write("null");
        }
    }

    @Override()
    void visitBoolean(Boolean value) {
        write(value.toString());
    }

    @Override()
    void visitNumber(Number value) {
        write(value.toString());
    }

    @Override()
    void visitString(String value) {
        write(value);
    }

    void enterCollection(Collection value) {
        depth++;
    }

    void visitIndexValue(int index, Object value, boolean isLast) {
        writePadding();
    }

    void leaveCollection(Collection value) {
        depth--;
        writePadding();
    }

    void enterObject(Object value) {
        depth++;
    }

    void visitKeyValue(String keyName, Object value, boolean isLast) {
        writePadding();
    }

    void leaveObject(Object value) {
        depth--;
        writePadding();
    }

    private void writePadding() {
        for (int i = 0; i < depth; i++) {
            write(INDENT);
        }
    }
}
