package org.afoninav.repository.jdbc;

import org.afoninav.model.Status;
import org.afoninav.repository.AttributeConverter;

public class StatusBooleanConverter implements AttributeConverter<Status, Boolean> {

    @Override
    public Boolean convertToDataBaseAttribute(Status field) {
        return field == Status.ACTIVE;
    }

    @Override
    public Status convertToJavaClassField(Boolean attribute) {
        return attribute ? Status.ACTIVE : Status.INACTIVE;
    }
}
