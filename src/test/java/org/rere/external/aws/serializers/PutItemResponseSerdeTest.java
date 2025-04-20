package org.rere.external.aws.serializers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PutItemResponseSerdeTest {
    private DynamoDbResponseSerde serde;

    @BeforeEach
    void setUp() {
        serde = new DynamoDbResponseSerde();
    }

    @Test
    void testSerializeAndDeserialize() throws SerializationException {
        // Create sample attributes
        Map<String, AttributeValue> attributes = new HashMap<>();
        attributes.put("id", AttributeValue.builder().s("123").build());
        attributes.put("name", AttributeValue.builder().s("TestItem").build());

        // Create sample ConsumedCapacity
        ConsumedCapacity consumedCapacity = ConsumedCapacity.builder()
                .tableName("TestTable")
                .capacityUnits(5.0)
                .build();

        // Create sample ItemCollectionMetrics
        ItemCollectionMetrics itemCollectionMetrics = ItemCollectionMetrics.builder()
                .itemCollectionKey(attributes)
                .build();

        // Create a PutItemResponse object
        PutItemResponse response = PutItemResponse.builder()
                .attributes(attributes)
                .consumedCapacity(consumedCapacity)
                .itemCollectionMetrics(itemCollectionMetrics)
                .build();

        // Serialize
        String serialized = serde.serialize(response);
        assertNotNull(serialized, "Serialized output should not be null");

        // Deserialize
        PutItemResponse deserializedResponse = (PutItemResponse) serde.deserialize(serialized);
        assertNotNull(deserializedResponse, "Deserialized object should not be null");

        // Validate attributes
        assertNotNull(deserializedResponse.attributes(), "Attributes should not be null");
        assertEquals("123", deserializedResponse.attributes().get("id").s());
        assertEquals("TestItem", deserializedResponse.attributes().get("name").s());

        // Validate ConsumedCapacity
        assertNotNull(deserializedResponse.consumedCapacity(), "ConsumedCapacity should not be null");
        assertEquals("TestTable", deserializedResponse.consumedCapacity().tableName());
        assertEquals(5.0, deserializedResponse.consumedCapacity().capacityUnits());

        // Validate ItemCollectionMetrics
        assertNotNull(deserializedResponse.itemCollectionMetrics(), "ItemCollectionMetrics should not be null");
        assertEquals("123", deserializedResponse.itemCollectionMetrics().itemCollectionKey().get("id").s());
    }

}
