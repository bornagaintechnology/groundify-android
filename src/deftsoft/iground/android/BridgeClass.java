package deftsoft.iground.android;

import java.util.ArrayList;

public class BridgeClass {

	private BridgeClass() {
	}

	static BridgeClass obj = null;

	public static BridgeClass instance() {
         if (obj == null)
        	 
        	 obj = new BridgeClass();
         
          return obj;
    }

	public ArrayList<String> childIDList,childFirstNameList;
}
