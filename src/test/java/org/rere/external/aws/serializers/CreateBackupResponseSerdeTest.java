/*
 * Copyright (c) 2025 <project contributors>
 * This program is made available under the terms of the MIT License.
 */
package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.BackupDetails;
import software.amazon.awssdk.services.dynamodb.model.CreateBackupResponse;

import static org.junit.jupiter.api.Assertions.*;

class CreateBackupResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {
        // Create test data
        BackupDetails backupDetails = BackupDetails.builder()
                .backupArn("arn:aws:dynamodb:us-east-1:123456789012:table/MyTable/backup/01525597630186-1234abcd")
                .backupName("MyBackup")
                .backupSizeBytes(1024L)
                .backupStatus("AVAILABLE")
                .backupType("USER")
                .build();

        CreateBackupResponse original = CreateBackupResponse.builder()
                .backupDetails(backupDetails)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        CreateBackupResponse deserialized = (CreateBackupResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);

        // Assertions
        assertEquals(original.backupDetails().backupArn(), deserialized.backupDetails().backupArn());
        assertEquals(original.backupDetails().backupName(), deserialized.backupDetails().backupName());
        assertEquals(original.backupDetails().backupSizeBytes(), deserialized.backupDetails().backupSizeBytes());
        assertEquals(original.backupDetails().backupStatus(), deserialized.backupDetails().backupStatus());
        assertEquals(original.backupDetails().backupType(), deserialized.backupDetails().backupType());
    }
}
