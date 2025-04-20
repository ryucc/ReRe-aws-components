/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws;

import org.rere.api.ReReSettings;
import org.rere.external.aws.serializers.DynamoDbResponseSerde;

public class ReReAws {
    public static ReReSettings defaultSettings() {
        return new ReReSettings().withParameterModding(false).registerSerializer(DynamoDbResponseSerde.class);
    }
}
