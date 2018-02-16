package org.projectredcap.main;

public class Config
{
	
	String API_TOKEN = "";
	String API_URL = "";
	String API_TYPE = "";
	String API_FORMAT = "";
	String API_CONTENT = "";
	String API_RAWORLABEL = "";
	String API_FIELDS = "";
	Integer API_REPORT_ID = 0;
	
	public String getAPI_TOKEN() {
		return API_TOKEN;
	}
	
	public void setAPI_TOKEN(String aPI_TOKEN) {
		API_TOKEN = aPI_TOKEN;
	}
	
	public String getAPI_URL() {
		return API_URL;
	}
	
	public void setAPI_URL(String aPI_URL) {
		API_URL = aPI_URL;
	}

	public String getAPI_TYPE() {
		return API_TYPE;
	}

	public void setAPI_TYPE(String aPI_TYPE) {
		API_TYPE = aPI_TYPE;
	}

	public String getAPI_FORMAT() {
		return API_FORMAT;
	}

	public void setAPI_FORMAT(String aPI_FORMAT) {
		API_FORMAT = aPI_FORMAT;
	}

	public String getAPI_CONTENT() {
		return API_CONTENT;
	}

	public void setAPI_CONTENT(String aPI_CONTENT) {
		API_CONTENT = aPI_CONTENT;
	}

	public String getAPI_RAWORLABEL() {
		return API_RAWORLABEL;
	}

	public void setAPI_RAWORLABEL(String aPI_RAWORLABEL) {
		API_RAWORLABEL = aPI_RAWORLABEL;
	}

	public String getAPI_FIELDS() {
		return API_FIELDS;
	}

	public void setAPI_FIELDS(String aPI_FIELDS) {
		API_FIELDS = aPI_FIELDS;
	}

	public Integer getAPI_REPORT_ID() {
		return API_REPORT_ID;
	}

	public void setAPI_REPORT_ID(Integer aPI_REPORT_ID) {
		API_REPORT_ID = aPI_REPORT_ID;
	}		
}
