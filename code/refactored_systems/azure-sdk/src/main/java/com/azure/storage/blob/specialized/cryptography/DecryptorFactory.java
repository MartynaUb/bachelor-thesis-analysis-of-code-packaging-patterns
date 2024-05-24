package com.azure.storage.blob.specialized.cryptography;

import com.azure.core.cryptography.AsyncKeyEncryptionKey;
import com.azure.core.cryptography.AsyncKeyEncryptionKeyResolver;
import com.azure.storage.blob.specialized.cryptography.decryptor.Decryptor;
import com.azure.storage.blob.specialized.cryptography.decryptor.NoOpDecryptor;
import com.azure.storage.blob.specialized.cryptography.decryptor.v1.DecryptorV1;
import com.azure.storage.blob.specialized.cryptography.decryptor.v2.DecryptorV2;
import com.azure.storage.blob.specialized.cryptography.encription.data.EncryptionData;

import static com.azure.storage.blob.specialized.cryptography.util.CryptographyConstants.ENCRYPTION_PROTOCOL_V1;
import static com.azure.storage.blob.specialized.cryptography.util.CryptographyConstants.ENCRYPTION_PROTOCOL_V2;

public class DecryptorFactory {

    public static Decryptor getDecryptor(AsyncKeyEncryptionKeyResolver keyResolver,
                                  AsyncKeyEncryptionKey keyWrapper, EncryptionData encryptionData) {
        if (encryptionData == null) {
            return new NoOpDecryptor(keyResolver, keyWrapper, null);
        }
        switch (encryptionData.getEncryptionAgent().getProtocol()) {
            case ENCRYPTION_PROTOCOL_V1:
                return new DecryptorV1(keyResolver, keyWrapper, encryptionData);
            case ENCRYPTION_PROTOCOL_V2:
                return new DecryptorV2(keyResolver, keyWrapper, encryptionData);
            default:
                throw LOGGER.logExceptionAsError(
                        new IllegalStateException("Encryption protocol not recognized: "
                                + encryptionData.getEncryptionAgent().getProtocol()));
        }
    }
}
