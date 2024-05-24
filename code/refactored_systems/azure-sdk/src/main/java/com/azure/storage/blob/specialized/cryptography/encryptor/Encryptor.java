// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.storage.blob.specialized.cryptography.encryptor;

import com.azure.core.util.logging.ClientLogger;
import com.azure.storage.blob.specialized.cryptography.encription.data.EncryptionData;
import com.azure.storage.blob.specialized.cryptography.util.WrappedKeyJson;

import reactor.core.publisher.Flux;

import javax.crypto.SecretKey;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.Map;

import static com.azure.storage.blob.specialized.cryptography.util.CryptographyConstants.ENCRYPTION_MODE;

public abstract class Encryptor {
    private static final ClientLogger LOGGER = new ClientLogger(Encryptor.class);

    protected final SecretKey aesKey;

    protected Encryptor(SecretKey aesKey) {
        this.aesKey = aesKey;
    }

    public abstract byte[] getKeyToWrap();

    protected abstract Flux<ByteBuffer> encrypt(Flux<ByteBuffer> plaintext) throws GeneralSecurityException;

    protected EncryptionData buildEncryptionData(Map<String, String> keyWrappingMetadata,
                                                 WrappedKeyJson wrappedKey) {
        return new EncryptionData()
            .setEncryptionMode(ENCRYPTION_MODE)
            .setKeyWrappingMetadata(keyWrappingMetadata)
            .setWrappedContentKey(wrappedKey);
    }

}
