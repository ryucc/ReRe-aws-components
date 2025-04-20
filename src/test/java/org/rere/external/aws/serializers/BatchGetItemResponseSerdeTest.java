package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BatchGetItemResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        Map<String, List<Map<String, AttributeValue>>> responses = Collections.singletonMap(
                "TestTable", Collections.singletonList(Collections.singletonMap(
                        "id", AttributeValue.builder().s("123").build()
                ))
        );

        Map<String, KeysAndAttributes> unprocessedKeys = Collections.singletonMap(
                "TestTable", KeysAndAttributes.builder().build()
        );

        List<ConsumedCapacity> consumedCapacities = Collections.singletonList(
                ConsumedCapacity.builder().tableName("TestTable").capacityUnits(5.0).build()
        );

        BatchGetItemResponse original = BatchGetItemResponse.builder()
                .responses(responses)
                .unprocessedKeys(unprocessedKeys)
                .consumedCapacity(consumedCapacities)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        BatchGetItemResponse deserialized = (BatchGetItemResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.responses(), deserialized.responses());
        assertEquals(original.unprocessedKeys(), deserialized.unprocessedKeys());
        assertEquals(original.consumedCapacity(), deserialized.consumedCapacity());
    }
}
