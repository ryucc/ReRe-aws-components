package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.DescribeEndpointsResponse;
import software.amazon.awssdk.services.dynamodb.model.Endpoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DescribeEndpointsResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        Endpoint endpoint1 = Endpoint.builder().address("https://dynamodb.endpoint1.com").build();
        Endpoint endpoint2 = Endpoint.builder().address("https://dynamodb.endpoint2.com").build();
        List<Endpoint> endpoints = Arrays.asList(endpoint1, endpoint2);

        DescribeEndpointsResponse original = DescribeEndpointsResponse.builder()
                .endpoints(endpoints)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        DescribeEndpointsResponse deserialized = (DescribeEndpointsResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.endpoints(), deserialized.endpoints());
        assertTrue(deserialized.hasEndpoints());
        assertEquals(2, deserialized.endpoints().size());
        assertEquals("https://dynamodb.endpoint1.com", deserialized.endpoints().get(0).address());
        assertEquals("https://dynamodb.endpoint2.com", deserialized.endpoints().get(1).address());
    }
}
