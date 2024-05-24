// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

module com.azure.storage.blob.cryptography {
    requires transitive com.azure.storage.blob;

    requires com.fasterxml.jackson.dataformat.xml;

    exports com.azure.storage.blob.specialized.cryptography;

    opens com.azure.storage.blob.specialized.cryptography to
        com.fasterxml.jackson.databind,
        com.azure.core;
    exports com.azure.storage.blob.specialized.cryptography.encryptor;
    opens com.azure.storage.blob.specialized.cryptography.encryptor to com.azure.core, com.fasterxml.jackson.databind;
    exports com.azure.storage.blob.specialized.cryptography.decryptor;
    opens com.azure.storage.blob.specialized.cryptography.decryptor to com.azure.core, com.fasterxml.jackson.databind;
    exports com.azure.storage.blob.specialized.cryptography.encryptor.agent;
    opens com.azure.storage.blob.specialized.cryptography.encryptor.agent to com.azure.core, com.fasterxml.jackson.databind;
    exports com.azure.storage.blob.specialized.cryptography.encription.data;
    opens com.azure.storage.blob.specialized.cryptography.encription.data to com.azure.core, com.fasterxml.jackson.databind;
    exports com.azure.storage.blob.specialized.cryptography.blob;
    opens com.azure.storage.blob.specialized.cryptography.blob to com.azure.core, com.fasterxml.jackson.databind;
    exports com.azure.storage.blob.specialized.cryptography.blob.client;
    opens com.azure.storage.blob.specialized.cryptography.blob.client to com.azure.core, com.fasterxml.jackson.databind;
    exports com.azure.storage.blob.specialized.cryptography.util;
    opens com.azure.storage.blob.specialized.cryptography.util to com.azure.core, com.fasterxml.jackson.databind;
}
