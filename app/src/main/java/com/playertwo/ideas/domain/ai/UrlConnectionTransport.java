package com.playertwo.ideas.domain.ai;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public final class UrlConnectionTransport implements HttpTransport {
    @Override public HttpResponse post(URI endpoint, Map<String, String> headers, String body, int timeoutMillis) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) endpoint.toURL().openConnection();
        connection.setRequestMethod("POST"); connection.setConnectTimeout(timeoutMillis); connection.setReadTimeout(timeoutMillis);
        connection.setDoOutput(true); connection.setRequestProperty("Content-Type", "application/json");
        for (Map.Entry<String, String> header : headers.entrySet()) connection.setRequestProperty(header.getKey(), header.getValue());
        connection.getOutputStream().write(body.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        int status = connection.getResponseCode();
        InputStream stream = status >= 400 ? connection.getErrorStream() : connection.getInputStream();
        return new HttpResponse(status, read(stream));
    }

    private static String read(InputStream stream) throws IOException {
        if (stream == null) return "";
        try (InputStream input = stream; ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024]; int count;
            while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
            return output.toString("UTF-8");
        }
    }
}
