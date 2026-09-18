package com.playertwo.ideas.data;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.playertwo.ideas.domain.ai.SecretStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SecretStoreInstrumentedTest {
    @Test public void encryptedStoreSupportsPutReplaceAndRemoveWithoutPlaintextPreference() {
        Context context = ApplicationProvider.getApplicationContext();
        SecretStore store = new AndroidSecretStore(context);
        store.remove("provider-key");
        store.put("provider-key", "secret-value");
        assertEquals("secret-value", store.get("provider-key"));
        SharedPreferences preferences = context.getSharedPreferences("ideas_secure_secrets", Context.MODE_PRIVATE);
        assertFalse(preferences.getString("provider-key", "").contains("secret-value"));
        store.put("provider-key", "replacement");
        assertEquals("replacement", store.get("provider-key"));
        store.remove("provider-key");
        assertFalse(store.contains("provider-key"));
    }
}
