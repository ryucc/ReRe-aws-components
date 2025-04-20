/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.ItemCollectionMetrics;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BatchWriteItemResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        Map<String, List<WriteRequest>> unprocessedItems = Collections.singletonMap(
                "TestTable", Collections.singletonList(WriteRequest.builder().build())
        );
        Map<String, List<ItemCollectionMetrics>> itemCollectionMetrics = Collections.singletonMap(
                "TestTable", Collections.singletonList(ItemCollectionMetrics.builder().build())
        );
        List<ConsumedCapacity> consumedCapacities = Collections.singletonList(
                ConsumedCapacity.builder().build()
        );

        BatchWriteItemResponse original = BatchWriteItemResponse.builder()
                .unprocessedItems(unprocessedItems)
                .itemCollectionMetrics(itemCollectionMetrics)
                .consumedCapacity(consumedCapacities)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        BatchWriteItemResponse deserialized = (BatchWriteItemResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.unprocessedItems(), deserialized.unprocessedItems());
        assertEquals(original.itemCollectionMetrics(), deserialized.itemCollectionMetrics());
        assertEquals(original.consumedCapacity(), deserialized.consumedCapacity());
    }
}
