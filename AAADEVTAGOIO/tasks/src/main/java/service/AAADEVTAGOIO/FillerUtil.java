package service.AAADEVTAGOIO;

import java.util.ArrayList;
import java.util.List;

public class FillerUtil {
	
	private static FillerUtil instance = null;
	
	public static FillerUtil getInstance()
	  {
	    if (instance == null) {
	      synchronized (FillerUtil.class)
	      {
	        if (instance == null) {
	          instance = new FillerUtil();
	        }
	      }
	    }
	    return instance;
	  }
	
	public List<String> comboRequest() {
		List<String> contentTypeList = new ArrayList();
		contentTypeList.add("Post");
		contentTypeList.add("Get");
		return contentTypeList;
	}
	

	
	public List<String> booleanType() {
		List<String> booleanTypes = new ArrayList();
		booleanTypes.add("True");
		booleanTypes.add("False");
		return booleanTypes;
	}



}
