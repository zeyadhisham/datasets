package corgis.classics.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import corgis.classics.domain.Statistics;
import corgis.classics.domain.Bibliography;
import corgis.classics.domain.Metadata;

/**
 * 
 */
public class Book {
	
    private Statistics statistics;
    private Bibliography bibliography;
    private Metadata metadata;
    
    
    /*
     * @return 
     */
    public Statistics getStatistics() {
        return this.statistics;
    }
    
    
    
    /*
     * @return 
     */
    public Bibliography getBibliography() {
        return this.bibliography;
    }
    
    
    
    /*
     * @return 
     */
    public Metadata getMetadata() {
        return this.metadata;
    }
    
    
    
	
	/**
	 * Creates a string based representation of this Book.
	
	 * @return String
	 */
	public String toString() {
		return "Book[" +statistics+", "+bibliography+", "+metadata+"]";
	}
	
	/**
	 * Internal constructor to create a Book from a  representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Book(JSONObject json_data) {
        try {// Statistics
            this.statistics = new Statistics((JSONObject)json_data.get("Statistics"));// Bibliography
            this.bibliography = new Bibliography((JSONObject)json_data.get("Bibliography"));// Metadata
            this.metadata = new Metadata((JSONObject)json_data.get("Metadata"));
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Book; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Book; a field had the wrong structure.");
    		e.printStackTrace();
        }
	}	
}