/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.TableDescription;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateTableResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        TableDescription tableDescription = TableDescription.builder()
                .tableName("TestTable")
                .tableArn("arn:aws:dynamodb:us-east-1:123456789012:table/TestTable")
                .tableStatus("ACTIVE")
                .build();

        CreateTableResponse original = CreateTableResponse.builder().tableDescription(tableDescription).build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        CreateTableResponse deserialized = (CreateTableResponse) serde.deserialize(
                serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.tableDescription().tableName(), deserialized.tableDescription().tableName());
        assertEquals(original.tableDescription().tableArn(), deserialized.tableDescription().tableArn());
        assertEquals(original.tableDescription().tableStatus(), deserialized.tableDescription().tableStatus());
    }
}
