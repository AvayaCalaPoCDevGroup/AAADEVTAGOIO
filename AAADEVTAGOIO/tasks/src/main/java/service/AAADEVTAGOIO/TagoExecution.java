package service.AAADEVTAGOIO;

import org.json.JSONObject;

import testHttps.Get;
import testHttps.Post;

import com.avaya.app.entity.Instance;
import com.avaya.app.entity.NodeInstance;
import com.roobroo.bpm.model.BpmNode;
import com.avaya.workflow.logger.*;

public class TagoExecution extends NodeInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(TagoExecution.class);

	public TagoExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}

	public Object execute() throws Exception {

		Get get = new Get();
		JSONObject json = new JSONObject();
		TagoModel tagoModel = (TagoModel) getNode();

		String deviceToken = (String) get("deviceToken");
		if (deviceToken == null || deviceToken.isEmpty()) {
			deviceToken = tagoModel.getDeviceToken();
		}

		String variable = (String) get("variable");
		if (variable == null || variable.isEmpty()) {
			variable = tagoModel.getVariable();
		}
		
		String serie = (String) get("serie");
		if (serie == null || serie.isEmpty()) {
			serie = tagoModel.getSerie();
		}

		try {
			String[] result = get.makeGet(deviceToken, variable, serie);
			if(result[0].equals("Error")){
				json.put("error", result[1]);
			}else{
				json.put("variable", result[0]);
				json.put("unit", result[1]);
				json.put("value", result[2]);
				json.put("latitude", result[3]);
				json.put("longitude", result[4]);
				json.put("type", result[5]);
				json.put("serie", result[6]);
				json.put("time", result[7]);
			}
			
		}catch (NullPointerException e) {
			json.put("result", "Empty");
		}catch (Exception e) {
			json.put("error", e.toString());
		}

		return json;
	}

}
