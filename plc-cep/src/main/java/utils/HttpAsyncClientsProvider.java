package utils;

import java.io.IOException;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public enum HttpAsyncClientsProvider {
	INSTANCE;
	private final CloseableHttpAsyncClient httpAsyncClients = HttpAsyncClients.createDefault();

	public CloseableHttpAsyncClient get() {
		if (!httpAsyncClients.isRunning())
			httpAsyncClients.start();
		return httpAsyncClients;
	}
	
	public void close() {
		try {
			httpAsyncClients.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
