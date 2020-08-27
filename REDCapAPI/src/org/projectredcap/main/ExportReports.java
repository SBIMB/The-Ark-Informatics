package org.projectredcap.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class ExportReports
{
	private final List<NameValuePair> params;
	private final HttpPost post;
	private HttpResponse resp;
	private final HttpClient client;
	private int respCode;
	private BufferedReader reader;
	private final StringBuffer result;
	private String line;
	private List<StringBuilder> records;

	public ExportReports(final Config c)
	{
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("token", c.API_TOKEN));
		params.add(new BasicNameValuePair("content", "report"));
		params.add(new BasicNameValuePair("format", c.API_FORMAT));
		params.add(new BasicNameValuePair("report_id", c.API_REPORT_ID.toString()));
		params.add(new BasicNameValuePair("rawOrLabel", c.API_RAWORLABEL));

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
		client = HttpClientBuilder.create().build();
		respCode = -1;
		reader = null;
		line = null;
		records = new ArrayList<StringBuilder>();
	}

	public List doPost()
	{
		resp = null;

		try
		{
			resp = client.execute(post);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		if(resp != null)
		{
			respCode = resp.getStatusLine().getStatusCode();

			try
			{
				reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}

		if(reader != null)
		{
			try
			{
				while((line = reader.readLine()) != null)
				{
					result.append(line);
					records.add(new StringBuilder(line));
				}
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}
		return records;
	}
}
