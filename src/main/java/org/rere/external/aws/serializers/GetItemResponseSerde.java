/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.rere.core.serde.PrimitiveSerde;
import org.rere.core.serde.ReReSerde;
import org.rere.core.serde.exceptions.SerializationException;
import org.rere.external.aws.dataHolders.AttributeValueHolder;
import software.amazon.awssdk.core.util.DefaultSdkAutoConstructList;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ItemCollectionMetrics;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GetItemResponseSerde implements ReReSerde<GetItemResponse> {

    private static final PrimitiveSerde primitiveSerde = new PrimitiveSerde();

    @Override
    public String serialize(GetItemResponse getItemResponse) throws SerializationException {
        Map<String, AttributeValueHolder> item = new HashMap<>();
        for(String key :getItemResponse.item().keySet()) {
            AttributeValue av = getItemResponse.item().get(key);
            item.put(key, new AttributeValueHolder(av));
        }
        ConsumedCapacity consumedCapacity = getItemResponse.consumedCapacity();
        DataHolder dataHolder = new DataHolder(item, consumedCapacity);
        return primitiveSerde.serialize(dataHolder);
    }

    @Override
    public GetItemResponse deserialize(String s) {
        DataHolder dataHolder = (DataHolder) primitiveSerde.deserialize(s);

        Map<String, AttributeValue> m = new HashMap<>();
        for (String key: dataHolder.item.keySet()) {
            m.put(key, dataHolder.item.get(key).revert());
        }

        return GetItemResponse.builder()
                .item(m)
                .consumedCapacity(dataHolder.consumedCapacity)
                .build();
    }

    public static class DataHolder implements Serializable {
        private final Map<String, AttributeValueHolder> item;
        private final ConsumedCapacity consumedCapacity;

        public DataHolder(Map<String, AttributeValueHolder> item,
                          ConsumedCapacity consumedCapacity) {
            this.item = item;
            this.consumedCapacity = consumedCapacity;
        }
    }
}
