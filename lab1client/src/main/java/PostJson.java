import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class PostJson {

	public PostJson() {
		// TODO Auto-generated constructor stub
	}
	public static String postToBootstrapServer(String id) {
		HttpClient httpClient = HttpClientBuilder.create().build(); 
	    try {
	        HttpPost request = new HttpPost("http://cmpe273lab1-1068.appspot.com/bootstrap");
	        StringEntity params =new StringEntity("{\"id\":\"" + id + "\"}", ContentType.create("application/json"));
	        request.addHeader("Content-Type", "application/json");
	        request.addHeader("Accept", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);
	        String responsebody = EntityUtils.toString(response.getEntity());
	        JSONObject jsonobj = new JSONObject(responsebody);
	        return jsonobj.getString("registerServerUrl");
	    }catch (Exception ex) {
	        System.out.println("error");
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	    }
	    return null;
	}
	
	public String post(String id, String num) {
		HttpClient httpClient = HttpClientBuilder.create().build(); 
	    try {
	        HttpPost request = new HttpPost("http://cmpe273lab1-1068.appspot.com/counter");
	        StringEntity params =new StringEntity("{\"id\":\"" + id + "\",\"num\":\"" + num + "\"}", ContentType.create("application/json"));
	        request.addHeader("Content-Type", "application/json");
	        request.addHeader("Accept", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);
	        System.out.println(request);
	        return EntityUtils.toString(response.getEntity());
	    }catch (Exception ex) {
	        System.out.println("error");
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	    }
	    return null;
	}
}
