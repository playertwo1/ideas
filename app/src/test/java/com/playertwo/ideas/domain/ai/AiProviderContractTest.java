package com.playertwo.ideas.domain.ai;

import org.junit.Test;
import java.util.Collections;
import static org.junit.Assert.*;

public class AiProviderContractTest {
    private AiRequest request() {
        return new AiRequest("INTERPRET", "P1", 3, "0.1",
            Collections.singletonMap("idea", "texto"),
            Collections.singletonMap("mode", "STANDARD"), 128, "req-1");
    }

    @Test public void fakeIsDeterministicForSameRequest() {
        AiProvider provider = new FakeAiProvider();
        AiResult first = provider.generate(request(), CancellationToken.never());
        AiResult second = provider.generate(request(), CancellationToken.never());
        assertEquals(AiResult.Outcome.SUCCESS, first.outcome());
        assertEquals(first, second);
        assertEquals("P1", first.references().get("projectId"));
        assertEquals("3", first.references().get("inputRevision"));
        assertTrue(first.hypotheses().contains("fake-provider"));
        assertFalse(first.content().containsKey("gate"));
    }

    @Test public void cancellationProducesExplicitResultWithoutContent() {
        AiResult result = new FakeAiProvider().generate(request(), () -> true);
        assertEquals(AiResult.Outcome.CANCELLED, result.outcome());
        assertEquals("CANCELLED", result.failureCode());
        assertTrue(result.content().isEmpty());
    }

    @Test public void requestRejectsMissingRequiredIdentity() {
        try {
            new AiRequest("", "P1", 1, "0.1", Collections.emptyMap(),
                Collections.emptyMap(), 1, "req");
            fail("missing operation accepted");
        } catch (IllegalArgumentException expected) { }
    }
}
