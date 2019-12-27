package ru.mail;

import java.util.Collection;
import java.lang.reflect.Field;

/**
 * abstract walker for JSON-compatible hierarchical structures
 */
public abstract class ObjectVisitor {
    public void walk(Object value) {
        visitValue(value);
    }

    void visitValue(Object value) {
        if (value == null) {
            visitNull();
        } else if (value instanceof Boolean) {
            visitBoolean((Boolean)value);
        } else if (value instanceof Number) {
            visitNumber((Number)value);
        } else if (value instanceof String) {
            visitString((String)value);
        } else if (value instanceof Collection) {
            visitCollection((Collection)value);
        } else {
            visitObject(value);
        }
    }

    abstract void visitNull();
    abstract void visitBoolean(Boolean value);
    abstract void visitNumber(Number value);
    abstract void visitString(String value);
    abstract void enterCollection(Collection value);
    abstract void visitIndexValue(int index, Object value, boolean isLast);
    abstract void leaveCollection(Collection value);
    abstract void enterObject(Object value);
    abstract void visitKeyValue(String keyName, Object value, boolean isLast);
    abstract void leaveObject(Object value);
    abstract void visitError(Exception e);

    void visitCollection(Collection value) {
        enterCollection(value);
        int i = 0;
        final int size = value.size();
        for (Object item : value) {
            visitIndexValue(i, item, i == size - 1);
            i++;
        }
        leaveCollection(value);
    }

    void visitObject(Object value) {
        enterObject(value);
        Class cls = value.getClass();
        Field[] allFields = cls.getDeclaredFields();
        final int length = allFields.length;
        for (int i = 0; i < length; i++) {
            Field field = allFields[i];
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                Object item = field.get(value);
                visitKeyValue(fieldName, item, i == length - 1);
            } catch (IllegalAccessException e) {
                visitError(e);
                visitKeyValue(fieldName, null, i == length - 1);
            }
        }
        leaveObject(value);
    }
}