/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.CreateGlobalTableResponse;
import software.amazon.awssdk.services.dynamodb.model.GlobalTableDescription;

import static org.junit.jupiter.api.Assertions.*;

class CreateGlobalTableResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        GlobalTableDescription globalTableDescription = GlobalTableDescription.builder()
                .globalTableArn("arn:aws:dynamodb:us-east-1:123456789012:global-table/MyGlobalTable")
                .globalTableStatus("ACTIVE")
                .build();

        CreateGlobalTableResponse original = CreateGlobalTableResponse.builder()
                .globalTableDescription(globalTableDescription)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        CreateGlobalTableResponse deserialized = (CreateGlobalTableResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.globalTableDescription().globalTableArn(), deserialized.globalTableDescription().globalTableArn());
        assertEquals(original.globalTableDescription().globalTableStatus(), deserialized.globalTableDescription().globalTableStatus());
    }
}
