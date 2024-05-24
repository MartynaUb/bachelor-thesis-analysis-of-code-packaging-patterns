package com.azure.storage.blob.specialized.cryptography;


import com.azure.core.util.logging.ClientLogger;
import com.azure.storage.blob.specialized.cryptography.encryptor.Encryptor;
import com.azure.storage.blob.specialized.cryptography.encryptor.v1.EncryptorV1;
import com.azure.storage.blob.specialized.cryptography.encryptor.v2.EncryptorV2;
import com.azure.storage.blob.specialized.cryptography.util.EncryptionVersion;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

public class EncryptorFactory {
    private static final ClientLogger LOGGER = new ClientLogger(EncryptorFactory.class);

    public static Encryptor getEncryptor(EncryptionVersion version, SecretKey aesKey) throws GeneralSecurityException {
        switch (version) {
            case V1:
                return new EncryptorV1(aesKey);
            case V2:
                return new EncryptorV2(aesKey);
            default:
                throw LOGGER.logExceptionAsError(new IllegalArgumentException("Invalid encryption version: "
                        + version));
        }
    }
}
