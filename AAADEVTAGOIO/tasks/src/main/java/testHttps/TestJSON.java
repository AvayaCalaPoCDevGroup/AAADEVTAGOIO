package testHttps;

import org.json.JSONException;
import org.json.JSONObject;

public class TestJSON {

	public static void main(String[] args) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("variable", "temperature");
		json.put("unit", "F");
		json.put("value", 80);
		
		System.out.println(json);

	}

}
