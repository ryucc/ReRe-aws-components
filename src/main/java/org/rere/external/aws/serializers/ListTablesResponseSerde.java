/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.rere.core.serde.PrimitiveSerde;
import org.rere.core.serde.ReReSerde;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListTablesResponseSerde implements ReReSerde {

    private static final PrimitiveSerde primitiveSerde = new PrimitiveSerde();

    @Override
    public String serialize(Object o) throws SerializationException {
        ListTablesResponse listTablesResponse = (ListTablesResponse) o;
        List<String> tableNames = listTablesResponse.tableNames();
        String lastEvaluatedTableName = listTablesResponse.lastEvaluatedTableName();

        DataHolder dataHolder = new DataHolder(tableNames, lastEvaluatedTableName);
        return primitiveSerde.serialize(dataHolder);
    }

    @Override
    public ListTablesResponse deserialize(String s) {
        DataHolder dataHolder = (DataHolder) primitiveSerde.deserialize(s);
        return ListTablesResponse.builder()
                .tableNames(dataHolder.tableNames)
                .lastEvaluatedTableName(dataHolder.lastEvaluatedTableName)
                .build();
    }

    @Override
    public boolean accept(Class<?> clazz) {
        return true;
    }

    public static class DataHolder implements Serializable {
        private final List<String> tableNames;
        private final String lastEvaluatedTableName;

        public DataHolder(List<String> tableNames, String lastEvaluatedTableName) {
            this.tableNames = new ArrayList<>(tableNames);
            this.lastEvaluatedTableName = lastEvaluatedTableName;
        }

    }
}
