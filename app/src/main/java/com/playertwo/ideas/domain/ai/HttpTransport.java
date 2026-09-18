package com.playertwo.ideas.domain.ai;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public interface HttpTransport {
    HttpResponse post(URI endpoint, Map<String, String> headers, String body, int timeoutMillis) throws IOException;
}
