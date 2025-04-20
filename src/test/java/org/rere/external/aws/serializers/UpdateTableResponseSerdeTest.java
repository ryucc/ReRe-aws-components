package org.rere.external.aws.serializers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.TableDescription;
import software.amazon.awssdk.services.dynamodb.model.TableStatus;
import software.amazon.awssdk.services.dynamodb.model.UpdateTableResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class UpdateTableResponseSerdeTest {
    private DynamoDbResponseSerde serde;

    @BeforeEach
    void setUp() {
        serde = new DynamoDbResponseSerde();
    }

    @Test
    void testSerializeAndDeserialize() throws SerializationException {
        // Create a sample TableDescription
        TableDescription tableDescription = TableDescription.builder()
                .tableName("TestTable")
                .tableStatus(TableStatus.ACTIVE)
                .build();

        // Create an UpdateTableResponse object
        UpdateTableResponse response = UpdateTableResponse.builder().tableDescription(tableDescription).build();

        // Serialize
        String serialized = serde.serialize(response);
        assertNotNull(serialized, "Serialized output should not be null");

        // Deserialize
        UpdateTableResponse deserializedResponse = (UpdateTableResponse) serde.deserialize(serialized);
        assertNotNull(deserializedResponse, "Deserialized object should not be null");
        assertNotNull(deserializedResponse.tableDescription(), "Table description should not be null");
        assertEquals("TestTable", deserializedResponse.tableDescription().tableName());
        assertEquals(TableStatus.ACTIVE, deserializedResponse.tableDescription().tableStatus());
    }

}
