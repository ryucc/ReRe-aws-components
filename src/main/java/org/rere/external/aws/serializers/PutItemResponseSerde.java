/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.rere.core.serde.PrimitiveSerde;
import org.rere.core.serde.ReReSerde;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.ItemCollectionMetrics;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PutItemResponseSerde implements ReReSerde<PutItemResponse> {

    private static final PrimitiveSerde primitiveSerde = new PrimitiveSerde();

    @Override
    public String serialize(PutItemResponse putItemResponse) throws SerializationException {
        Map<String, AttributeValue> attributes = putItemResponse.attributes();
        ConsumedCapacity consumedCapacity = putItemResponse.consumedCapacity();
        ItemCollectionMetrics itemCollectionMetrics = putItemResponse.itemCollectionMetrics();
        DataHolder dataHolder = new DataHolder(attributes, consumedCapacity, itemCollectionMetrics);
        return primitiveSerde.serialize(dataHolder);
    }

    @Override
    public PutItemResponse deserialize(String s) {
        DataHolder dataHolder = (DataHolder) primitiveSerde.deserialize(s);

        return PutItemResponse.builder()
                .attributes(dataHolder.attributes)
                .consumedCapacity(dataHolder.consumedCapacity)
                .itemCollectionMetrics(dataHolder.itemCollectionMetrics)
                .build();
    }

    public static class DataHolder implements Serializable {
        private final Map<String, AttributeValue> attributes;
        private final ConsumedCapacity consumedCapacity;
        private final ItemCollectionMetrics itemCollectionMetrics;

        public DataHolder(Map<String, AttributeValue> attributes,
                          ConsumedCapacity consumedCapacity,
                          ItemCollectionMetrics itemCollectionMetrics) {
            this.attributes = new HashMap<>(attributes);
            this.consumedCapacity = consumedCapacity;
            this.itemCollectionMetrics = itemCollectionMetrics;
        }
    }
}
