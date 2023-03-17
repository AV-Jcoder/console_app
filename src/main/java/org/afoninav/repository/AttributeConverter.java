package org.afoninav.repository;

public interface AttributeConverter<Java,DB> {

    DB convertToDataBaseAttribute(Java field);
    Java convertToJavaClassField(DB attribute);
}
