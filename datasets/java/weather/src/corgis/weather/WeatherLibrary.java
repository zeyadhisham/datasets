package corgis.weather;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import corgis.weather.domain.*;

import java.sql.*;

/**
 * {'overview': 'Under the National Oceanic and Atmpospheric Administration, the National Weather Service provides daily weather reports for cities across the county.  This is done through the use of 122 different Weather Forcast Offices throughout the country.  These WFOs are responsible for the daily weather reports for serveral cities throughout their region of coverage.  This data set takes the information from these WFO reports for cities across the country through the months of April to June of 2016.\n', 'short': 'Weather records through the months of April and June of 2016 across the U.S.', 'citation': 'http://w2.weather.gov/climate/'}
 */
public class WeatherLibrary {
    private String databasePath;
	private Connection connection;
    private JSONParser parser;
    private final int HARDWARE = 1000;
    
    public static void main(String[] args) {
        System.out.println("Testing Weather");
        WeatherLibrary weatherLibrary = new WeatherLibrary();
        
        
        System.out.println("Testing production GetWeather");
        ArrayList<Report> list_of_report_1_production = weatherLibrary.getWeather(false);
        
        
        System.out.println("Testing test GetWeather");
        ArrayList<Report> list_of_report_1_test = weatherLibrary.getWeather(true);
        
        
        
        System.out.println("Testing production GetWeatherByDay");
        ArrayList<Report> list_of_report_2_production = weatherLibrary.getWeatherByDay("6-1-16", false);
        
        
        System.out.println("Testing test GetWeatherByDay");
        ArrayList<Report> list_of_report_2_test = weatherLibrary.getWeatherByDay("6-1-16", true);
        
        
        
        System.out.println("Testing production GetWeatherByCity");
        ArrayList<Report> list_of_report_3_production = weatherLibrary.getWeatherByCity("Blacksburg", false);
        
        
        System.out.println("Testing test GetWeatherByCity");
        ArrayList<Report> list_of_report_3_test = weatherLibrary.getWeatherByCity("Blacksburg", true);
        
        
    }
    
    private void connectToDatabase(String databasePath) {
        this.connection = null;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:"+databasePath);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        this.parser =  new JSONParser();
    }
	
    /**
     * Create a connection to the database file in its standard position.
     */
	public WeatherLibrary() {
        this.databasePath = "weather.db";
        this.connectToDatabase(this.databasePath);
	}
	
    /**
     * Create a connection to the database file, stored explicitly somewhere.
     * @param databasePath The filename of the database file.
     */
	public WeatherLibrary(String databasePath) {
        this.databasePath = databasePath;
        this.connectToDatabase(this.databasePath);
	}
    
    
    
    /**
     * Returns weather reports from the dataset.
    
     * @return a list[report]
     */
	public ArrayList<Report> getWeather() {
        return this.getWeather(true);
    }
    
    
    /**
     * Returns weather reports from the dataset.
    
     * @return a list[report]
     */
	public ArrayList<Report> getWeather(boolean test) {
        String query;
        if (test) {
            query = String.format("SELECT data FROM weather LIMIT %d", this.HARDWARE);
        } else {
            query = "SELECT data FROM weather";
        }
        PreparedStatement preparedQuery = null;
        ResultSet rs = null;
        try {
            preparedQuery = this.connection.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println("Could not build SQL query for local database.");
    		e.printStackTrace();
        }
        try {
            rs = preparedQuery.executeQuery();
        } catch (SQLException e) {
            System.err.println("Could not execute query.");
    		e.printStackTrace();
        }
        
        ArrayList<Report> result = new ArrayList<Report>();
        try {
            while (rs.next()) {
                String raw_result = rs.getString(1);
                Report parsed = null;
                if (test) {
                    parsed = new Report((JSONObject)this.parser.parse(raw_result));
                    
                } else {
                    parsed = new Report((JSONObject)this.parser.parse(raw_result));
                    
                }
                
                result.add(parsed);
                
            }
        } catch (SQLException e) {
            System.err.println("Could not iterate through query.");
    		e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Could not convert the response from getWeather; a parser error occurred.");
    		e.printStackTrace();
        }
        return result;
	}
    
    
    /**
     * Given a day, returns all the weather reports for that day in the database.
    
     * @param day The day to get reports.
     * @return a list[report]
     */
	public ArrayList<Report> getWeatherByDay(String day) {
        return this.getWeatherByDay(day, true);
    }
    
    
    /**
     * Given a day, returns all the weather reports for that day in the database.
    
     * @param day The day to get reports.
     * @return a list[report]
     */
	public ArrayList<Report> getWeatherByDay(String day, boolean test) {
        String query;
        if (test) {
            query = String.format("SELECT data FROM weather WHERE year=? LIMIT %d", this.HARDWARE);
        } else {
            query = "SELECT data FROM weather WHERE year=?";
        }
        PreparedStatement preparedQuery = null;
        ResultSet rs = null;
        try {
            preparedQuery = this.connection.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println("Could not build SQL query for local database.");
    		e.printStackTrace();
        }
        try {
            preparedQuery.setString(1, day);
        } catch (SQLException e) {
            System.err.println("Could not build prepare argument: day");
    		e.printStackTrace();
        }
        try {
            rs = preparedQuery.executeQuery();
        } catch (SQLException e) {
            System.err.println("Could not execute query.");
    		e.printStackTrace();
        }
        
        ArrayList<Report> result = new ArrayList<Report>();
        try {
            while (rs.next()) {
                String raw_result = rs.getString(1);
                Report parsed = null;
                if (test) {
                    parsed = new Report((JSONObject)this.parser.parse(raw_result));
                    
                } else {
                    parsed = new Report((JSONObject)this.parser.parse(raw_result));
                    
                }
                
                result.add(parsed);
                
            }
        } catch (SQLException e) {
            System.err.println("Could not iterate through query.");
    		e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Could not convert the response from getWeatherByDay; a parser error occurred.");
    		e.printStackTrace();
        }
        return result;
	}
    
    
    /**
     * Given a city, returns all the weather reports for that city in the database.
    
     * @param city The city to get reports.
     * @return a list[report]
     */
	public ArrayList<Report> getWeatherByCity(String city) {
        return this.getWeatherByCity(city, true);
    }
    
    
    /**
     * Given a city, returns all the weather reports for that city in the database.
    
     * @param city The city to get reports.
     * @return a list[report]
     */
	public ArrayList<Report> getWeatherByCity(String city, boolean test) {
        String query;
        if (test) {
            query = String.format("SELECT data FROM weather WHERE year=? LIMIT %d", this.HARDWARE);
        } else {
            query = "SELECT data FROM weather WHERE year=?";
        }
        PreparedStatement preparedQuery = null;
        ResultSet rs = null;
        try {
            preparedQuery = this.connection.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println("Could not build SQL query for local database.");
    		e.printStackTrace();
        }
        try {
            preparedQuery.setString(1, city);
        } catch (SQLException e) {
            System.err.println("Could not build prepare argument: city");
    		e.printStackTrace();
        }
        try {
            rs = preparedQuery.executeQuery();
        } catch (SQLException e) {
            System.err.println("Could not execute query.");
    		e.printStackTrace();
        }
        
        ArrayList<Report> result = new ArrayList<Report>();
        try {
            while (rs.next()) {
                String raw_result = rs.getString(1);
                Report parsed = null;
                if (test) {
                    parsed = new Report((JSONObject)this.parser.parse(raw_result));
                    
                } else {
                    parsed = new Report((JSONObject)this.parser.parse(raw_result));
                    
                }
                
                result.add(parsed);
                
            }
        } catch (SQLException e) {
            System.err.println("Could not iterate through query.");
    		e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Could not convert the response from getWeatherByCity; a parser error occurred.");
    		e.printStackTrace();
        }
        return result;
	}
    
}