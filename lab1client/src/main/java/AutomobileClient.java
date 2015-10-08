import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
public class AutomobileClient {
	final static private String BOOTSTRAPSERVER_URL = "http://cmpe273lab1-1068.appspot.com/bootstrap";
	final static private String REGISTERSERVER_URL = "http://cmpe273lab1-1068.appspot.com/register";
	final static private String LOCALDB = "jdbc:derby:localdb;create=true";
	
	private String id;
	private Bootstrap bootstrap = new Bootstrap();
	private List<ClientObject> objectList = new ArrayList<ClientObject>();
	
	String getBootstrapInfo() {
		return bootstrap.toString();
	}
	
	public AutomobileClient(String id) {	
		this.id = id;
	}
	void addObject(ClientObject obj) {
		objectList.add(obj);
		writeObjectToLocalDB(obj);
	}
	void writeObjectToLocalDB(ClientObject obj) {
		try {
			Connection conn = DriverManager.getConnection(LOCALDB);
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM OBJECT WHERE id = '" + id 
					+ "' and name = '" + obj.getName() + "'");
            stmt.execute("INSERT INTO OBJECT VALUES('"
            		+ id + "', '"
            		+ obj.getName() + "')");
            stmt.close();
            conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	void clientInitalBootstrap() {
		HttpClient httpClient = HttpClientBuilder.create().build(); 
	    try {
	    	HttpGet request = new HttpGet("http://cmpe273lab1-1068.appspot.com/bootstrap/" + id);
	        HttpResponse response = httpClient.execute(request);
	        String responsebody = EntityUtils.toString(response.getEntity());
	        JSONObject jsonobj = new JSONObject(responsebody);
	        bootstrap.setBrands(jsonobj.getString("brands"));
	        bootstrap.setModel(jsonobj.getString("model"));
	        bootstrap.setYear(jsonobj.getString("year"));
	        bootstrap.setRegisterServerUrl(jsonobj.getString("registerServerUrl"));
	        writeBootstrapToLocalDB();
	    }catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	    }
	}
	void requestBootstrap() {
		String bootstrapURL = PostJson.postToBootstrapServer(id);
		bootstrap.setRegisterServerUrl(bootstrapURL);
		System.out.println(bootstrapURL);
	}
	
	void initialFactoryBootstrap() {
		bootstrap.setBrands("honda");
		bootstrap.setModel("civic");
		bootstrap.setRegisterServerUrl(REGISTERSERVER_URL);
		bootstrap.setYear("2015");
		writeBootstrapToLocalDB();
	}
	void writeBootstrapToLocalDB() {
		if(bootstrap.getBrands() == null) { //have not been initialized
			System.out.println("no bootstrap initialized yet");
			return;
		}
		try {
			Connection conn = DriverManager.getConnection(LOCALDB);
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM BOOTSTRAP WHERE id = '" + id + "'");
            stmt.execute("INSERT INTO BOOTSTRAP VALUES('"
            		+ id + "', '"
            		+ REGISTERSERVER_URL + "', '"
            		+ bootstrap.getBrands() + "', '"
            		+ bootstrap.getModel() + "', '"
            		+ bootstrap.getYear() + "')");
            stmt.close();
            conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	

	void register() {
		for(ClientObject obj : objectList) {
			postObjects(obj);
		}
	}
	void update() {
		for(ClientObject obj : objectList) {
			postObjects(obj);
		}
	}
	void deregister() {
		HttpClient httpClient = HttpClientBuilder.create().build(); 
	    try {
	    	HttpDelete request = new HttpDelete("http://cmpe273lab1-1068.appspot.com/deregister/" + id);
	        HttpResponse response = httpClient.execute(request);
	    }catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	    }
	}
	void printObjInfoOnServer() {
		HttpClient httpClient = HttpClientBuilder.create().build(); 
	    try {
	    	HttpGet request = new HttpGet("http://cmpe273lab1-1068.appspot.com/objectInfo/" + id);
	        HttpResponse response = httpClient.execute(request);
	        String responsebody = EntityUtils.toString(response.getEntity());
	        JSONObject jsonobj = new JSONObject(responsebody);
	        System.out.println(jsonobj.get("objects"));
	    }catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	    }
	}
	private void postObjects(ClientObject obj) {
		HttpClient httpClient = HttpClientBuilder.create().build(); 
	    try {
	    	String jsonString = "{\"name\":\"" + obj.getName() + "\"}";
	        HttpPost request = new HttpPost(bootstrap.getRegisterServerUrl() + "/" + id);
	        StringEntity params =new StringEntity(jsonString, ContentType.create("application/json"));
	        request.addHeader("Content-Type", "application/json");
	        request.addHeader("Accept", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);
	    }catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	       
	    }
	}
}
