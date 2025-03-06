/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws;

import org.rere.api.ReReSettings;
import org.rere.external.aws.serializers.GetItemResponseSerde;
import org.rere.external.aws.serializers.ListTablesResponseSerde;
import org.rere.external.aws.serializers.PutItemResponseSerde;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

public class ReReAws {
    public static ReReSettings defaultSettings() {
        return new ReReSettings().withParameterModding(false)
                .registerSerializer(GetItemResponse.class, GetItemResponseSerde.class)
                .registerSerializer(ListTablesResponse.class, ListTablesResponseSerde.class)
                .registerSerializer(PutItemResponse.class, PutItemResponseSerde.class);
    }
}
