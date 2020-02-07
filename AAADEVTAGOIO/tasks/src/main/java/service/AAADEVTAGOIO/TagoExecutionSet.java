package service.AAADEVTAGOIO;

import org.json.JSONObject;

import testHttps.Post;

import com.avaya.app.entity.Instance;
import com.avaya.app.entity.NodeInstance;
import com.avaya.workflow.logger.Logger;
import com.avaya.workflow.logger.LoggerFactory;
import com.roobroo.bpm.model.BpmNode;

public class TagoExecutionSet extends NodeInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TagoExecutionSet(Instance instance, BpmNode node) {
		super(instance, node);
	}

	public Object execute() throws Exception {
		Post post = new Post();
		JSONObject json = new JSONObject();
		TagoModelSet tagoModel = (TagoModelSet) getNode();
		
		
		String deviceToken = (String) get("deviceToken");
		if (deviceToken == null || deviceToken.isEmpty()) {
			deviceToken = tagoModel.getDeviceToken();
		}

		String variable = (String) get("variable");
		if (variable == null || variable.isEmpty()) {
			variable = tagoModel.getVariable();
		}
		
		String value = (String) get("value");
		if (value == null || value.isEmpty()) {
			value = tagoModel.getValue();
		}
		
		String serie = (String) get("serie");
		if (serie == null || serie.isEmpty()) {
			serie = tagoModel.getSerie();
		}
		
		try {
			String[] result = post.makePost(deviceToken, variable, value, serie);
			if(result[0].equals("Error")){
				json.put("error", result[1]);
			}else{
				json.put("status", result[0]);
				json.put("result", result[1]);
			}
			
		}catch (NullPointerException e) {
			json.put("result", "Empty");
		}catch (Exception e) {
			json.put("error", e.toString());
		}

		return json;
	}

}
