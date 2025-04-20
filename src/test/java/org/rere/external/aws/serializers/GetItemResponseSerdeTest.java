/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetItemResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws Exception {
        // Create test item
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("TestKey", AttributeValue.builder().s("TestValue").build());

        ConsumedCapacity consumedCapacity = ConsumedCapacity.builder()
                .tableName("table")
                .capacityUnits(1.0)
                .readCapacityUnits(1.0)
                .writeCapacityUnits(1.0)
                .build();
        // Create a test response
        GetItemResponse originalResponse = GetItemResponse.builder()
                .item(item)
                .consumedCapacity(consumedCapacity)
                .build();

        // Serialize
        String serialized = serde.serialize(originalResponse);

        assertNotNull(serialized, "Serialization should not return null");

        // Deserialize
        GetItemResponse deserializedResponse = (GetItemResponse)serde.deserialize(serialized);
        assertNotNull(deserializedResponse, "Deserialized response should not be null");

        // Validate item
        assertNotNull(deserializedResponse.item(), "Item should not be null");
        assertTrue(deserializedResponse.item().containsKey("TestKey"));
        assertEquals("TestValue", deserializedResponse.item().get("TestKey").s());
    }
}
