package testHttps;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;

@SuppressWarnings("deprecation")
public class Get {
	public String[] makeGet(String deviceToken, String variable, String serie) {
		String[] returnString = new String[8];
		try {
			// SSLProtocolType and SSLUtilityFactory it is necessary to use
			// Avaya liberias to make the request through HTTPS
			final SSLProtocolType protocolType = SSLProtocolType.TLSv1_2;
			final SSLContext sslContext = SSLUtilityFactory
					.createSSLContext(protocolType);
			// If you want to send information through the url it is necessary
			// to encode the string
			// String encodedMessage = URLEncoder.encode("STRING", "UTF-8");
			final String URI = "https://api.tago.io/data?variable=" + variable
					+ "&qty=1&serie=" + serie;

			HttpClient client = HttpClients.custom().setSslcontext(sslContext)
					.setHostnameVerifier(new AllowAllHostnameVerifier())
					.build();
			HttpGet getMethod = new HttpGet(URI);
			// The necessary headers are added and according to the tago
			// documentation, the token is included
			getMethod.addHeader("Device-Token", deviceToken);
			getMethod.addHeader("Content-Type", "application/json");

			final HttpResponse response = client.execute(getMethod);

			final BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			final StringBuilder result = new StringBuilder();
			while ((line = inputStream.readLine()) != null) {
				result.append(line);
			}

			JSONObject json = new JSONObject(result.toString());

			JSONArray resultsArray = json.getJSONArray("result");
			JSONObject locationTago = null;
			Double valueTago = null;
			Double serieTago = null;
			Boolean valueBoolean = null;
			Boolean serieBoolean = null;
			Double[] coordinates = { null, null };

			for (int i = 0; i < resultsArray.length(); ++i) {
				JSONObject resultTago = resultsArray.getJSONObject(i);

				returnString[0] = (resultTago.has("variable")) ? resultTago
						.getString("variable") : null;
				returnString[1] = (resultTago.has("unit")) ? resultTago
						.getString("unit") : null;

				try {
					//Si "value" es String
					returnString[2] = (resultTago.has("value")) ? resultTago
							.getString("value") : null;
				} catch (JSONException ex) {
					try {
						//Si "value" es Double
						valueTago = (resultTago.has("value")) ? resultTago
								.getDouble("value") : null;
						returnString[2] = mostrarNumero(valueTago);
					} catch (JSONException e) {
						//Si "value" es Boolean
						valueBoolean = (resultTago.has("value")) ? resultTago
								.getBoolean("value") : null;
						returnString[2] = valueBoolean.toString();
					}
				}

				locationTago = (resultTago.has("location")) ? resultTago
						.getJSONObject("location") : null;
				if (locationTago != null) {
					JSONArray coordinatessArray = locationTago
							.getJSONArray("coordinates");

					for (int k = 0; k < coordinatessArray.length(); ++k) {
						coordinates[k] = (Double) coordinatessArray.get(k);

					}
					returnString[3] = coordinates[0].toString();
					returnString[4] = coordinates[1].toString();
					returnString[5] = (locationTago.has("type")) ? locationTago
							.getString("type") : null;

				}
				
				if(serie == null || serie.isEmpty()){
					
				}else{
					try {
						//Si "serie" es String
						returnString[6] = (resultTago.has("serie")) ? resultTago
								.getString("serie") : null;
					} catch (JSONException ex) {
						try {
							//Si "serie" es Double
							serieTago = (resultTago.has("serie")) ? resultTago
									.getDouble("serie") : null;
							returnString[6] = mostrarNumero(serieTago);
						} catch (JSONException e) {
							//Si "serie" es Boolean
							serieBoolean = (resultTago.has("serie")) ? resultTago
									.getBoolean("serie") : null;
							returnString[6] = serieBoolean.toString();
						}
					}
				}
				returnString[7] = (resultTago.has("time")) ? resultTago
						.getString("time") : null;
			}

			return returnString;
		} catch (Exception ex) {
			returnString[0] = "Error";
			returnString[1] = ex.toString();
			return returnString;
		}
	}
	
    public static String mostrarNumero(double d) {
        return (d == (long) d) ? String.format("%d", (long) d) : String.format("%s", d);
    }
}
