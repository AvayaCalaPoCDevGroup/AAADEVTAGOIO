package testHttp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class Get {
	public String makeGetHttp() {
		try {
			// If you want to send information through the url it is necessary
			// to encode the string
			// String encodedMessage = URLEncoder.encode("STRING", "UTF-8");
			final String URI = "https://api.tago.io/device";

			HttpClient client = null;
			HttpGet getMethod = new HttpGet(URI);
			// The necessary headers are added and according to the tago
			// documentation, the token is included
			getMethod.addHeader("Device-Token",
					"37b99499-81ec-4a10-96a8-71cbce49a3d9");
			getMethod.addHeader("Content-Type", "application/json");

			final HttpResponse response = client.execute(getMethod);

			final BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			final StringBuilder result = new StringBuilder();
			while ((line = inputStream.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception ex) {
			return null;
		}
	}
}
