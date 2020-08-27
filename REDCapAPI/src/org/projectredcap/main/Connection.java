/*******************************************************************************
 * Copyright (c) 2011  University of Witwatersrand. All rights reserved.
 * 
 * This file is part of The Ark.
 * 
 * The Ark is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * The Ark is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.projectredcap.main;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author Freedom Mukomana
 *
 */
public class Connection {
	private List<NameValuePair> params;
	private HttpPost post;
	private HttpResponse resp;
	private HttpClient client;
	private int respCode;
	private BufferedReader reader;
	private StringBuffer result;
	private String line;
	
	
	public HttpResponse getResp() {
		return resp;
	}
	public void setResp(HttpResponse resp) {
		this.resp = resp;
	}
	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public List<NameValuePair> getParams() {
		return params;
	}
	public HttpClient getClient() {
		return client;
	}
	public StringBuffer getResult() {
		return result;
	}
	public BufferedReader getReader() {
		return reader;
	}
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}
	public HttpPost getPost() {
		return post;
	}
	
	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}
	public void setPost(HttpPost post) {
		this.post = post;
	}
	public void setClient(HttpClient client) {
		this.client = client;
	}
	public void setResult(StringBuffer result) {
		this.result = result;
	}
	
	public void configure(Config c){
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("token", c.API_TOKEN));
		params.add(new BasicNameValuePair("content", "version"));

		post = new HttpPost(c.API_URL);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try
		{
			post.setEntity(new UrlEncodedFormEntity(params));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		result = new StringBuffer();
		this.
		client = HttpClientBuilder.create().build();
		this.setRespCode(-1);
		reader = null;
		line = null;
	}
	
	public int test() {
		try{
			respCode = client.execute(post).getStatusLine().getStatusCode();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return respCode;
	}
		
	public int test(Config c) {
		
		this.configure(c);
		
		try{
			respCode = client.execute(post).getStatusLine().getStatusCode();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return respCode;
	}

}
