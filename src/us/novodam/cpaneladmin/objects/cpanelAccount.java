package us.novodam.cpaneladmin.objects;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import android.util.Log;


public class cpanelAccount {
	private String siteName;
	private String userName;
	private String accessHash;
	private String fullURL;
	private String headerType;
	private String headerText;
	private long id;
	private ArrayList<String> accountFunctions;
	
	public cpanelAccount() {
		this.siteName = "";
		this.userName = "";
		this.accessHash = "";
		this.accountFunctions = new ArrayList<String>();
		this.accountFunctions.clear();
		this.id = -1;
	}

	public cpanelAccount(String siteName, String userName, String accessHash) {
		this.fullURL = "http://";
		this.siteName = siteName;
		this.fullURL += this.siteName + ":2086/json-api/";
		
		this.headerType = "Authorization";
		this.headerText = "WHM ";
		this.userName = userName;
		this.accessHash = accessHash;
		this.accessHash = this.accessHash.replaceAll("\n", "");
		this.accessHash = this.accessHash.replaceAll("\r", "");
		this.headerText += this.userName + ":" + this.accessHash;
		
		this.accountFunctions = new ArrayList<String>();
		this.accountFunctions.clear();
		
		this.id = -1;
	}
	
	public cpanelAccount(long id, String siteName, String userName, String accessHash) {
		this.fullURL = "https://";
		this.siteName = siteName;
		this.fullURL += this.siteName + ":2087/json-api/";
		
		this.headerType = "Authorization";
		this.headerText = "WHM ";
		this.userName = userName;
		this.accessHash = accessHash;
		this.accessHash = this.accessHash.replaceAll("\n", "");
		this.accessHash = this.accessHash.replaceAll("\r", "");
		this.headerText += this.userName + ":" + this.accessHash;
		
		this.accountFunctions = new ArrayList<String>();
		this.accountFunctions.clear();
		
		this.id = id;
	}
	
	public String queryAccount(String func) {
		//make sure to convert this to SSL/TLS after P.o.C. stage
		String response = "";
		String siteFunction = this.fullURL + func;
		//String siteFunction = "https://gmail.com";
		try {
			InputStream content = null;
			HttpGet httpGet = new HttpGet(siteFunction);
			httpGet.addHeader(this.headerType, this.headerText);
			HttpClient httpclient = new DefaultHttpClient();
			// Execute HTTP Get Request
			HttpResponse webresponse = httpclient.execute(httpGet);
			Log.d("conn type: ", webresponse.getProtocolVersion().toString());
			Log.d("connection: ", httpclient.getConnectionManager().getSchemeRegistry().getSchemeNames().toString());
			content = webresponse.getEntity().getContent();
			
			
			
			//beyond this point should stay the same
			BufferedReader rd = new BufferedReader(new InputStreamReader(content), 4096);
			String line;
			StringBuilder sb =  new StringBuilder();
			while ((line = rd.readLine()) != null) {
					sb.append(line);
			}
			rd.close();
			response = sb.toString();
			
        } catch (Exception e) {
        	response = e.toString();
		}
        return response;
	}
	
	public String checkFunctionality(boolean saveFlag) {
		String result = "";
		String response = "";
		String function = "applist";
		
		response = queryAccount(function);
		response = response.substring(7);
		
        try {
        	JSONArray entries = new JSONArray(response);
        		int i;
        		for (i=0;i<entries.length();i++) {
        			result += entries.getString(i) + "\n";
        			if (saveFlag) {
        				accountFunctions.add(entries.getString(i));
        			}
        		}
        }
        catch (Exception je) {
        	result = "";
        	result = "Error w/file: " + je.getMessage();
        	if (saveFlag) {
        		accountFunctions.clear();
        	}
        }
        return result;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccessHash() {
		return accessHash;
	}

	public void setAccessHash(String accessHash) {
		this.accessHash = accessHash;
	}

//	public String getFullURL() {
//		return fullURL;
//	}
//
//	public void setFullURL(String fullURL) {
//		this.fullURL = fullURL;
//	}
//
//	public String getHeaderType() {
//		return headerType;
//	}
//
//	public void setHeaderType(String headerType) {
//		this.headerType = headerType;
//	}
//
//	public String getHeaderText() {
//		return headerText;
//	}
//
//	public void setHeaderText(String headerText) {
//		this.headerText = headerText;
//	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArrayList<String> getAccountFunctions() {
		return accountFunctions;
	}

	public void setAccountFunctions(ArrayList<String> accountFunctions) {
		this.accountFunctions = accountFunctions;
	}
}
