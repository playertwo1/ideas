package com.playertwo.ideas.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import com.playertwo.ideas.domain.ai.SecretStore;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** Android adapter: AES-GCM key held by AndroidKeyStore; plaintext never enters logs. */
public final class AndroidSecretStore implements SecretStore {
    private static final String KEY_ALIAS = "ideas.ai.secret.v1";
    private static final String PREFS = "ideas_secure_secrets";
    private final SharedPreferences preferences;
    private final KeyStore keyStore;

    public AndroidSecretStore(Context context) {
        try {
            preferences = context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator generator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                generator.init(new android.security.keystore.KeyGenParameterSpec.Builder(KEY_ALIAS,
                    android.security.keystore.KeyProperties.PURPOSE_ENCRYPT |
                    android.security.keystore.KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build());
                generator.generateKey();
            }
        } catch (Exception error) { throw new IllegalStateException("secure secret store unavailable", error); }
    }

    @Override public synchronized void put(String reference, String secret) {
        String ref = required(reference); String value = required(secret);
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key());
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            byte[] combined = new byte[cipher.getIV().length + encrypted.length];
            System.arraycopy(cipher.getIV(), 0, combined, 0, cipher.getIV().length);
            System.arraycopy(encrypted, 0, combined, cipher.getIV().length, encrypted.length);
            if (!preferences.edit().putString(ref, Base64.encodeToString(combined, Base64.NO_WRAP)).commit())
                throw new IllegalStateException("secret write failed");
        } catch (Exception error) { throw new IllegalStateException("secret write failed", error); }
    }

    @Override public synchronized String get(String reference) {
        String encoded = preferences.getString(required(reference), null);
        if (encoded == null) return null;
        try {
            byte[] combined = Base64.decode(encoded, Base64.NO_WRAP);
            byte[] iv = new byte[12]; byte[] ciphertext = new byte[combined.length - iv.length];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, iv.length, ciphertext, 0, ciphertext.length);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key(), new GCMParameterSpec(128, iv));
            return new String(cipher.doFinal(ciphertext), StandardCharsets.UTF_8);
        } catch (Exception error) { throw new IllegalStateException("secret read failed", error); }
    }

    @Override public synchronized void remove(String reference) {
        if (!preferences.edit().remove(required(reference)).commit()) throw new IllegalStateException("secret removal failed");
    }

    @Override public synchronized boolean contains(String reference) { return preferences.contains(required(reference)); }

    private SecretKey key() throws Exception { return ((KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null)).getSecretKey(); }
    private static String required(String value) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException("reference/value required");
        return value;
    }
}
