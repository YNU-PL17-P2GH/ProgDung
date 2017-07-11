package jp.ac.ynu.pp2.gh.progdung.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SaveData implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8023480072967308681L;
	
	private String userName;
	private String nickName;

	private Map<String, String> flags;
	
	public SaveData() {
		flags = new HashMap<String, String>();
	}
	
	public String getFlag(String pKey) {
		return flags.get(pKey);
	}
	
	public void setFlag(String pKey, String pFlag) {
		flags.put(pKey, pFlag);
	}
}
