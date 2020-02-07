package service.AAADEVTAGOIO.Shared;

import java.util.List;

import com.roobroo.bpm.model.BpmNode;
import com.roobroo.bpm.util.WFUtil;

public class TagoIOSharedModel extends BpmNode {

	public TagoIOSharedModel(String name, String id) {
		super(name, id);
	}

	private static final long serialVersionUID = 1L;

	private String profileIdShared;
	private String deviceTokenShared;

	public String getProfileIdShared() {
		return profileIdShared;
	}

	public void setProfileIdShared(String profileIdShared) {
		this.profileIdShared = profileIdShared;
	}

	public String getDeviceTokenShared() {
		return deviceTokenShared;
	}

	public void setDeviceTokenShared(String deviceTokenShared) {
		this.deviceTokenShared = deviceTokenShared;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean validateProperties(List<String> w, List<String> e) {
		boolean isValid = true;
		if ((!WFUtil.validateMapping(w, e, getDataInputAssociations(),
				"profileIdShared"))
				&& (!WFUtil.validateEmptyProperty(profileIdShared,
						"profileIdShared", e))) {
			isValid = false;

		}
		if ((!WFUtil.validateMapping(w, e, getDataInputAssociations(),
				"deviceTokenShared"))
				&& (!WFUtil.validateEmptyProperty(deviceTokenShared,
						"deviceTokenShared", e))) {
			isValid = false;

		}
		return super.validateProperties(w, e) && isValid;

	}
}
