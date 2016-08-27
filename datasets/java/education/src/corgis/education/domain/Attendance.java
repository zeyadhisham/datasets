package corgis.education.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * 
 */
public class Attendance {
	
    private Integer averageStudentRate;
    private Double averageTeacherRate;
    private Double averageMinutes;
    
    
    /*
     * @return 
     */
    public Integer getAverageStudentRate() {
        return this.averageStudentRate;
    }
    
    
    
    /*
     * @return 
     */
    public Double getAverageTeacherRate() {
        return this.averageTeacherRate;
    }
    
    
    
    /*
     * @return 
     */
    public Double getAverageMinutes() {
        return this.averageMinutes;
    }
    
    
    
	
	/**
	 * Creates a string based representation of this Attendance.
	
	 * @return String
	 */
	public String toString() {
		return "Attendance[" +averageStudentRate+", "+averageTeacherRate+", "+averageMinutes+"]";
	}
	
	/**
	 * Internal constructor to create a Attendance from a  representation.
	 * @param json_data The raw json data that will be parsed.
	 */
    public Attendance(JSONObject json_data) {
        try {// average student rate
            this.averageStudentRate = new Integer(((Long)json_data.get("average student rate")).intValue());// average teacher rate
            this.averageTeacherRate = (Double)json_data.get("average teacher rate");// average minutes
            this.averageMinutes = (Double)json_data.get("average minutes");
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Attendance; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Attendance; a field had the wrong structure.");
    		e.printStackTrace();
        }
	}	
}