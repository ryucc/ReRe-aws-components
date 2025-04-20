package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListTablesResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        List<String> tableNames = Arrays.asList("Table1", "Table2", "Table3");
        String lastEvaluatedTableName = "Table3";

        ListTablesResponse original = ListTablesResponse.builder()
                .tableNames(tableNames)
                .lastEvaluatedTableName(lastEvaluatedTableName)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        ListTablesResponse deserialized = (ListTablesResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.tableNames(), deserialized.tableNames());
        assertEquals(original.lastEvaluatedTableName(), deserialized.lastEvaluatedTableName());
    }
}
