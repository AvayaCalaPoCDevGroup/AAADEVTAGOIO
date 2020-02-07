package testHttp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class Post {
	public String makePostHttp() {
		try {
			// End Point for example...
			final String URI = "https://api.tago.io/data";
			// Configuring the client with SSL
			final HttpClient client = null;
			// Defining the type of request (In this case it is a POST)
			final HttpPost postMethod = new HttpPost(URI);
			// The necessary headers are added and according to the tago
			// documentation, the token is included
			postMethod.addHeader("Device-Token",
					"e4fb994d-5d10-4fa1-a58b-e75b378f248b");
			postMethod.addHeader("Content-Type", "application/json");

			// In this part, you can include the Json parse to String, or you
			// can create the json with the JSONObject Library
			// Example
			/*
			 * JSONObject json = new JSONObject(); 
			 * json.put("variable","temperature"); json.put("unit", "F"); json.put("value", 80);
			 */
			final String messageBodyAssistant = "{\n"
					+ "    \"variable\": \"temperature\",\n"
					+ "    \"unit\"    : \"F\",\n" 
					+ "    \"value\"   : 75\n"
					+ "}";
			final StringEntity conversationEntityAssistant = new StringEntity(
					messageBodyAssistant);
			postMethod.setEntity(conversationEntityAssistant);

			final HttpResponse responseAssistant = client
					.execute(postMethod);

			final BufferedReader inputStreamAssistant = new BufferedReader(
					new InputStreamReader(responseAssistant.getEntity()
							.getContent()));

			String line = "";
			final StringBuilder result = new StringBuilder();
			while ((line = inputStreamAssistant.readLine()) != null) {
				result.append(line);
			}
			
			return result.toString();
			
		} catch (Exception ex) {
			return null;
		}
	}
}
