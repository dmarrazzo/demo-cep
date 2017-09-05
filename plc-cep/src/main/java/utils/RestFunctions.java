package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RestFunctions {
	final static Logger LOGGER = LoggerFactory.getLogger(RestFunctions.class);
	private static String FEEDBACK_URL = System.getenv("FEEDBACK_URL");
	private static final String CHARSET = "UTF-8";
	private static CloseableHttpAsyncClient httpclient;

	private static class RestCallback implements FutureCallback<HttpResponse> {
		@Override
		public void failed(Exception ex) {
			LOGGER.info("REST failed");
		}

		@Override
		public void completed(HttpResponse res) {
			LOGGER.info("REST completed");
			LOGGER.debug("Response " + res);
		}

		@Override
		public void cancelled() {
			LOGGER.info("REST cancelled");
		}
	};

	public final static void restGet() {
		LOGGER.info("restGet: start");

		httpclient = HttpAsyncClientsProvider.INSTANCE.get();

		if (FEEDBACK_URL == null)
		    FEEDBACK_URL = "http://mas460:8090/test";
		
		HttpGet request = new HttpGet(FEEDBACK_URL);
		httpclient.execute(request, new RestCallback());
	}

	public final static void restPost(String payload) {
		LOGGER.info("restPost: start");

		httpclient = HttpAsyncClientsProvider.INSTANCE.get();

		if (FEEDBACK_URL == null)
            FEEDBACK_URL = "http://mas460:8090/test";

		HttpPost request = new HttpPost(FEEDBACK_URL);

		request.setEntity(new StringEntity(payload, CHARSET));
		httpclient.execute(request, new RestCallback());
	}
}
