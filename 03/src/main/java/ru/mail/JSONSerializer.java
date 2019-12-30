package ru.mail;

import java.util.Collection;

public class JSONSerializer extends ObjectSerializer {
    @Override
    void visit(Object value) {
        visitValue(value);
    }

    @Override()
    void visitString(String value) {
        write('"');
        // neither the most effecient nor correct way to escape JSON strings
        // but should be compatible if we won't use control characters
        super.visitString(
            value.replaceAll("\"", "\\\"")
        );
        write('"');
    }

    @Override()
    void enterCollection(Collection value) {
        super.enterCollection(value);
        write("[\n");
    }

    @Override()
    void visitIndexValue(int index, Object value, boolean isLast) {
        super.visitIndexValue(index, value, isLast);
        visitValue(value);
        if (!isLast) write(',');
        write('\n');
    }

    @Override()
    void leaveCollection(Collection value) {
        super.leaveCollection(value);
        write(']');
    }
    
    @Override()
    void enterObject(Object value) {
        super.enterObject(value);
        write("{\n");
    }

    @Override()
    void visitKeyValue(String keyName, Object value, boolean isLast) {
        super.visitKeyValue(keyName, value, isLast);
        visitString(keyName);
        write(": ");
        visitValue(value);
        if (!isLast) write(',');
        write('\n');
    }

    @Override()
    void leaveObject(Object value) {
        super.leaveObject(value);
        write('}');
    }
}
