import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;


public class Tester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		factoryBootstrapTest();
		System.out.println("\n");
		clientInitialBootstrapTest();
		System.out.println("\n");
		registerTest();
		registerTestWithoutDeregiter();
	}
	
	private static void registerTestWithoutDeregiter() {
		AutomobileClient autoMobile = new AutomobileClient("1");
		autoMobile.initialFactoryBootstrap();
		
		ClientObject gps = new ClientObject("gps");
		autoMobile.addObject(gps);
		autoMobile.register();
	}

	private static void registerTest() {
		System.out.println("register test \n");
		AutomobileClient autoMobile = new AutomobileClient("1");
		autoMobile.initialFactoryBootstrap();
		
		ClientObject gps = new ClientObject("gps");
		autoMobile.addObject(gps);
		autoMobile.register();
		System.out.println("register result");
		autoMobile.printObjInfoOnServer();
		
		ClientObject radio = new ClientObject("radio");
		autoMobile.addObject(radio);
		autoMobile.update();
		System.out.println("update result");
		autoMobile.printObjInfoOnServer();
		
	
		autoMobile.deregister();
		System.out.println("deregister result");
		autoMobile.printObjInfoOnServer();
	}
	private static void clientInitialBootstrapTest() {
		System.out.println("clientInitialBootstrap test");
		AutomobileClient autoMobile = new AutomobileClient("1");
		autoMobile.clientInitalBootstrap();
		System.out.println(autoMobile.getBootstrapInfo());
	}
	private static void factoryBootstrapTest() {
		System.out.println("factoryBootstrap test");
		AutomobileClient autoMobile = new AutomobileClient("1");
		autoMobile.initialFactoryBootstrap();
		System.out.println(autoMobile.getBootstrapInfo());
	}
	private static void createBootstrapTable() {
		String LOCALDB = "jdbc:derby:localdb;create=true";	
		try {
			Connection conn = DriverManager.getConnection(LOCALDB);
			Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE BOOTSTRAP "
            		+ "(ID varchar(255) NOT NULL,"
            		+ "URL varchar(255),"
            		+ "BRANDS varchar(255),"
            		+ "MODEL varchar(255),"
            		+ "PRODUCEYEAR varchar(255))");
            stmt.close();
            conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	private static void createObjectTable() {
		String LOCALDB = "jdbc:derby:localdb;create=true";	
		try {
			Connection conn = DriverManager.getConnection(LOCALDB);
			Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE OBJECT "
            		+ "(ID varchar(255) NOT NULL,"
            		+ "NAME varchar(255))");
            stmt.close();
            conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
