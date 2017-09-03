package utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RestFunctions {
	final static Logger LOGGER =  LoggerFactory.getLogger(RestFunctions.class);
	private static final String FEEDBACK_URL = System.getProperty("FEEDBACK_URL", "http://mas460:8090/test");
	private static final String CHARSET = "UTF-8";
	private static CloseableHttpAsyncClient httpclient;

	private static class RestCallback implements FutureCallback<HttpResponse> {
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
	}; 
	
	public final static void restGet() {
		LOGGER.info("start");

		httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		
		HttpGet request = new HttpGet(FEEDBACK_URL);
		httpclient.execute(request, new RestCallback());
	}
	
	public final static void restPost(String payload) {
		LOGGER.info("start");

		httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		
		HttpPost request = new HttpPost(FEEDBACK_URL);
		
		request.setEntity(new StringEntity(payload, CHARSET));
		httpclient.execute(request, new RestCallback());
	}
}
