package service.AAADEVTAGOIO.Shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import service.AAADEVTAGOIO.Constants.Constants;

import com.avaya.app.entity.Instance;
import com.avaya.app.entity.NodeInstance;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.roobroo.bpm.model.BpmNode;

public class TagoIOSharedExcecution extends NodeInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TagoIOSharedExcecution(Instance instance, BpmNode node) {
		super(instance, node);
	}

	public Object execute() throws Exception {
		
		TagoIOSharedModel model = (TagoIOSharedModel)getNode();
		
		String tokenId = (String)get("deviceTokenShared");
		if(tokenId == null || tokenId.isEmpty()){
			tokenId = model.getDeviceTokenShared();
				if(tokenId == null || tokenId.isEmpty()){
					throw new IllegalArgumentException(
							"Invalid Argument! Device Token ID cannot be empty...");
				}
		}
		
		String profileId = (String)get("profileIdShared");
		if(profileId == null || profileId.isEmpty()){
			profileId = model.getProfileIdShared();
			if(profileId == null || profileId.isEmpty()){
				throw new IllegalArgumentException(
						"Invalid Argument! Profile ID cannot be empty...");
			}
		}
		
		try{
			JSONObject json = getSharedPeople(tokenId, profileId);
			if(json.has("status") && json.getBoolean("status")){
				return json.put("error", "none").put("status", "SUCCESS").put("code", "200");
			}else{
				return new JSONObject().put("status", "FAILED").put("error", json.toString()).put("code", "400");
			}
		}catch(Exception e){
			return new JSONObject().put("status", "FAILED").put("error", e.toString()).put("code", "400");
		}
		
		
	}
	
	private JSONObject getSharedPeople(String tokenId, String profileId) throws SSLUtilityException, ClientProtocolException, IOException, JSONException{
		
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContext = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);
		final HttpClient client = HttpClients.custom()
				.setSSLContext(sslContext)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		
		final HttpGet getMethod = new HttpGet(Constants.TAGIO_IO_SHARED_URL_ONE + profileId.trim() + Constants.TAGIO_IO_SHARED_URL_TWO );
		getMethod.addHeader("Content-Type", "application/json");
		getMethod.addHeader("Device-Token", tokenId.trim());
	
		final HttpResponse response = client.execute(getMethod);

		final BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		final StringBuilder result = new StringBuilder();
		while ((line = inputStream.readLine()) != null) {
			result.append(line);
		}
		return new JSONObject(result.toString());
	}
}
