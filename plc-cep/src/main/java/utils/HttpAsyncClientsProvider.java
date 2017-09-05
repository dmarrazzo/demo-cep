package utils;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public enum HttpAsyncClientsProvider {
    INSTANCE;

    // private
    private static final String FEEDBACK_SCOPE = System.getenv("FEEDBACK_SCOPE");
    private static final String FEEDBACK_USER = System.getenv("FEEDBACK_USER");
    private static final String FEEDBACK_PASSWORD = System.getenv("FEEDBACK_PASSWORD");

    private CloseableHttpAsyncClient httpAsyncClient = null;

    public CloseableHttpAsyncClient get() {

        if (httpAsyncClient == null) {
            newHttpAsynchClient();
        }

        if (!httpAsyncClient.isRunning())
            httpAsyncClient.start();
        return httpAsyncClient;
    }

    public void close() {
        try {
            if (httpAsyncClient != null)
                httpAsyncClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void newHttpAsynchClient() {
        if (FEEDBACK_SCOPE == null)
            httpAsyncClient = HttpAsyncClients.createDefault();
        else {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(FEEDBACK_SCOPE, 80),
                    new UsernamePasswordCredentials(FEEDBACK_USER, FEEDBACK_PASSWORD));
            httpAsyncClient = HttpAsyncClients.custom()
                                              .setDefaultCredentialsProvider(credsProvider)
                                              .build();
        }
    }

}
