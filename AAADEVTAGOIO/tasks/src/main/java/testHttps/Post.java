package testHttps;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;

public class Post {

	public String [] makePost(String deviceToken, String variable, String value, String serie) {
		String[] returnString = new String[2];
		try {
			// SSLProtocolType and SSLUtilityFactory it is necessary to use
			// Avaya liberias to make the request through HTTPS
			final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
			final SSLContext sslContextAssistant = SSLUtilityFactory
					.createSSLContext(protocolTypeAssistant);
			// End Point for example...
			final String URI = "https://api.tago.io/data";
			// Configuring the client with SSL
			final HttpClient client = HttpClients.custom()
					.setSslcontext(sslContextAssistant)
					.setHostnameVerifier(new AllowAllHostnameVerifier())
					.build();
			// Defining the type of request (In this case it is a POST)
			final HttpPost postMethod = new HttpPost(URI);
			// The necessary headers are added and according to the tago
			// documentation, the token is included
			postMethod.addHeader("Device-Token", deviceToken);
			postMethod.addHeader("Content-Type", "application/json");

			// In this part, you can include the Json parse to String, or you
			// can create the json with the JSONObject Library
			// Example
			/*
			 * JSONObject json = new JSONObject(); 
			 * json.put("variable","temperature"); json.put("unit", "F"); json.put("value", 80);
			 */
			final String messageBody;
			if(serie == null || serie.isEmpty()){
				messageBody = "{\n"
						+ "    \"variable\": \""+variable+"\",\n"
						+ "    \"value\"   : "+value+"\n"
						+ "}";
			}else{
				messageBody = "{\n"
						+ "    \"variable\": \""+variable+"\",\n"
						+ "    \"value\"   : "+value+",\n"
						+ "	   \"serie\"   : \""+serie+"\"\n"
						+ "}";
			}
			
			final StringEntity tagoEntity = new StringEntity(
					messageBody);
			postMethod.setEntity(tagoEntity);

			final HttpResponse response = client
					.execute(postMethod);

			final BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(response.getEntity()
							.getContent()));

			String line = "";
			final StringBuilder result = new StringBuilder();
			while ((line = inputStream.readLine()) != null) {
				result.append(line);
			}
			
			
			JSONObject json = new JSONObject(result.toString());
			Boolean status = json.getBoolean("status");
			returnString[0] = status.toString();
			returnString[1] = json.getString("result");
			
			return returnString;
			
		} catch (Exception ex) {
			returnString[0] = "Error";
			returnString[1] = ex.toString();
			return returnString;
		}
	}

}
