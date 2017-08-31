package utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RestFunctions {
	final static Logger LOGGER =  LoggerFactory.getLogger(RestFunctions.class);

	public final static void restGet() {
		LOGGER.info("start");

		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		HttpGet request = new HttpGet("http://mas460:8090/test");
		httpclient.execute(request, new FutureCallback<HttpResponse>() {
			
			@Override
			public void failed(Exception arg0) {
				try {
					LOGGER.info("REST failed");
					httpclient.close();
				} catch (IOException e) {
				}
			}
			
			@Override
			public void completed(HttpResponse arg0) {
				try {
					LOGGER.info("REST completed");
					httpclient.close();
				} catch (IOException e) {
				}
			}
			
			@Override
			public void cancelled() {
				try {
					LOGGER.info("REST cancelled");
					httpclient.close();
				} catch (IOException e) {
				}
				
			}
		});

	}
}
