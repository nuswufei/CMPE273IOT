import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class Client1 {

	public Client1() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws InterruptedException {
		String user1 = "1";
	    for(int i = 0; i < 50; ++i) {
	    	PostJson postJson = new PostJson();
	    	System.out.println(postJson.post(user1, i + ""));
	    	Thread.sleep(2000);
	    }
	}

}
