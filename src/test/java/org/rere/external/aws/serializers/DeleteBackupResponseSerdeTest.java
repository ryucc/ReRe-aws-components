package org.rere.external.aws.serializers;

import org.junit.jupiter.api.Test;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.BackupDescription;
import software.amazon.awssdk.services.dynamodb.model.BackupDetails;
import software.amazon.awssdk.services.dynamodb.model.BackupStatus;
import software.amazon.awssdk.services.dynamodb.model.BackupType;
import software.amazon.awssdk.services.dynamodb.model.DeleteBackupResponse;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DeleteBackupResponseSerdeTest {

    private final DynamoDbResponseSerde serde = new DynamoDbResponseSerde();

    @Test
    void testSerializationAndDeserialization() throws SerializationException {

        BackupDetails backupDetails = BackupDetails.builder()
                .backupArn("arn:aws:dynamodb:us-west-2:123456789012:table/Books/backup/015453216717")
                .backupName("BooksBackup")
                .backupSizeBytes(1024L)
                .backupStatus(BackupStatus.AVAILABLE)
                .backupType(BackupType.USER)
                .backupCreationDateTime(Instant.parse("2023-01-01T10:00:00Z"))
                .backupExpiryDateTime(Instant.parse("2023-12-31T10:00:00Z"))
                .build();
        // Create test data
        BackupDescription backupDescription = BackupDescription.builder()
                .backupDetails(backupDetails)
                .build();

        DeleteBackupResponse original = DeleteBackupResponse.builder()
                .backupDescription(backupDescription)
                .build();

        // Serialize
        String serialized = serde.serialize(original);
        assertNotNull(serialized);

        // Deserialize
        DeleteBackupResponse deserialized = (DeleteBackupResponse) serde.deserialize(serialized);
        assertNotNull(deserialized);
        assertEquals(deserialized.backupDescription(), backupDescription);
    }
}
