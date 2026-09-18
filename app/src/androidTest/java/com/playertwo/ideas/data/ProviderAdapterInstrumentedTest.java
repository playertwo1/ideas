package com.playertwo.ideas.data;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.playertwo.ideas.domain.ai.AiRequest;
import com.playertwo.ideas.domain.ai.AiResult;
import com.playertwo.ideas.domain.ai.CancellationToken;
import com.playertwo.ideas.domain.ai.HttpAiProviderAdapter;
import com.playertwo.ideas.domain.ai.HttpResponse;
import com.playertwo.ideas.domain.ai.HttpTransport;
import com.playertwo.ideas.domain.ai.InMemorySecretStore;
import com.playertwo.ideas.domain.ai.ProviderConfiguration;
import java.net.URI;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ProviderAdapterInstrumentedTest {
    @Test public void configuredAdapterParsesStructuredResponseWithoutProviderSdk() {
        InMemorySecretStore secrets = new InMemorySecretStore(); secrets.put("key", "secret-value");
        HttpTransport transport = (endpoint, headers, body, timeout) -> new HttpResponse(200,
            "{\"content\":{\"suggestion\":\"ok\"},\"references\":{\"projectId\":\"P1\",\"inputRevision\":\"3\",\"schemaVersion\":\"0.1\"},\"hypotheses\":[\"h\"]}");
        AiRequest request = new AiRequest("INTERPRET", "P1", 3, "0.1", Collections.singletonMap("idea", "texto"),
            Collections.singletonMap("mode", "STANDARD"), 128, "req-1");
        AiResult result = new HttpAiProviderAdapter(new ProviderConfiguration(URI.create("https://provider.invalid"), "p", "m", "key", 100), secrets, transport)
            .generate(request, CancellationToken.never());
        assertEquals(AiResult.Outcome.SUCCESS, result.outcome());
        assertEquals("ok", result.content().get("suggestion"));
        assertEquals("P1", result.references().get("projectId"));
    }
}
