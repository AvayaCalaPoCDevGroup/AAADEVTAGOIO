package service.AAADEVTAGOIO;

import java.util.List;

import com.roobroo.bpm.model.BpmNode;
import com.roobroo.bpm.util.WFUtil;

public class TagoModelSet extends BpmNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceToken;
	private String variable;
	private String value;
	private String serie;
	
	
	public TagoModelSet(String name, String id) {
		super(name, id);
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
    public boolean validateProperties(List<String> w, List<String> e) {
        boolean isValid = true;
        if ((!WFUtil.validateMapping(w, e, getDataInputAssociations(), "deviceToken"))
                && (!WFUtil.validateEmptyProperty(deviceToken, "deviceToken", e))) {
            isValid = false;
         
        }
        
        if ((!WFUtil.validateMapping(w, e, getDataInputAssociations(), "variable"))
                && (!WFUtil.validateEmptyProperty(variable, "variable", e))) {
            isValid = false;
            
        }
        
        if ((!WFUtil.validateMapping(w, e, getDataInputAssociations(), "value"))
                && (!WFUtil.validateEmptyProperty(value, "value", e))) {
            isValid = false;
            
        }
        
        return super.validateProperties(w, e) && isValid;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}
	
}
