package org.rere.external.aws.serializers;/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.BatchExecuteStatementResponse;
import software.amazon.awssdk.services.dynamodb.model.BatchStatementResponse;
import software.amazon.awssdk.services.dynamodb.model.ConsumedCapacity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BatchExecuteStatementResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        List<BatchStatementResponse> responses = Collections.singletonList(BatchStatementResponse.builder().build());
        List<ConsumedCapacity> consumedCapacities = Collections.singletonList(ConsumedCapacity.builder().build());

        BatchExecuteStatementResponse original = BatchExecuteStatementResponse.builder()
                .responses(responses)
                .consumedCapacity(consumedCapacities)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        BatchExecuteStatementResponse deserialized = (BatchExecuteStatementResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.responses(), deserialized.responses());
        assertEquals(original.consumedCapacity(), deserialized.consumedCapacity());
    }
}
