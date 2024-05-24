// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.storage.blob.specialized.cryptography.decryptor;

import com.azure.core.cryptography.AsyncKeyEncryptionKey;
import com.azure.core.cryptography.AsyncKeyEncryptionKeyResolver;
import com.azure.storage.blob.specialized.cryptography.blob.EncryptedBlobRange;
import com.azure.storage.blob.specialized.cryptography.encription.data.EncryptionData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.util.concurrent.atomic.AtomicLong;

public class NoOpDecryptor extends Decryptor {

    public NoOpDecryptor(AsyncKeyEncryptionKeyResolver keyResolver, AsyncKeyEncryptionKey keyWrapper, EncryptionData encryptionData) {
        super(keyResolver, keyWrapper, encryptionData);
    }

    @Override
    public Cipher getCipher(byte[] contentEncryptionKey, byte[] iv, boolean padding) throws InvalidKeyException {
        return null;
    }

    @Override
    public Flux<ByteBuffer> decrypt(Flux<ByteBuffer> encryptedFlux, EncryptedBlobRange encryptedBlobRange, boolean padding,
                                    String requestUri, AtomicLong totalInputBytes, byte[] contentEncryptionKey) {
        return encryptedFlux;
    }

    @Override
    public Mono<byte[]> getKeyEncryptionKey() {
        return Mono.just(new byte[0]);
    }
}
