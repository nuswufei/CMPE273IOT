
public class Client2 {

	public Client2() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws InterruptedException {
		String user2 = "2";
	    for(int i = 0; i < 50; ++i) {
	    	PostJson postJson = new PostJson();
	    	System.out.println(postJson.post(user2, i + ""));
	    	Thread.sleep(2000);
	    }
	}
	
}
