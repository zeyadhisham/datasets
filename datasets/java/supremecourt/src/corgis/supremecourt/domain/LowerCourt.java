package corgis.supremecourt.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import corgis.supremecourt.domain.Reasons;
import corgis.supremecourt.domain.Disposition;

/**
 * 
 */
public class LowerCourt {
	
    private Reasons reasons;
    private Boolean disagreement;
    private String direction;
    private Disposition disposition;
    
    
    /*
     * @return 
     */
    public Reasons getReasons() {
        return this.reasons;
    }
    
    
    
    /*
     * @return 
     */
    public Boolean getDisagreement() {
        return this.disagreement;
    }
    
    
    
    /*
     * @return 
     */
    public String getDirection() {
        return this.direction;
    }
    
    
    
    /*
     * @return 
     */
    public Disposition getDisposition() {
        return this.disposition;
    }
    
    
    
	
	/**
	 * Creates a string based representation of this LowerCourt.
	
	 * @return String
	 */
	public String toString() {
		return "LowerCourt[" +reasons+", "+disagreement+", "+direction+", "+disposition+"]";
	}
	
	/**
	 * Internal constructor to create a LowerCourt from a  representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public LowerCourt(JSONObject json_data) {
        try {// reasons
            this.reasons = new Reasons((JSONObject)json_data.get("reasons"));// disagreement?
            this.disagreement = (Boolean)json_data.get("disagreement?");// direction
            this.direction = (String)json_data.get("direction");// disposition
            this.disposition = new Disposition((JSONObject)json_data.get("disposition"));
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a LowerCourt; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a LowerCourt; a field had the wrong structure.");
    		e.printStackTrace();
        }
	}	
}