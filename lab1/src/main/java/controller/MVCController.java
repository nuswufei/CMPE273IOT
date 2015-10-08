package controller;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;



@Controller
@RequestMapping("/*")
public class MVCController {
	Map<String, CounterItem> dic = new HashMap<>();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	public MVCController() {
		// TODO Auto-generated constructor stub
	}
	@RequestMapping(value="test", method= RequestMethod.GET)
	public @ResponseBody CounterItem hello(){
		CounterItem ci = new CounterItem();
		ci.setId("1");
		ci.setNum("222");
		return ci;
	}
	
	@RequestMapping(value = "initalTestData", method= RequestMethod.GET)
	public void initalTestData() {
		Entity bootstrapInfo = new Entity("Boostrap");
		bootstrapInfo.setIndexedProperty("id", "1");
		bootstrapInfo.setIndexedProperty("url", "http://cmpe273lab1-1068.appspot.com/register");
		bootstrapInfo.setIndexedProperty("brands", "Ford");
		bootstrapInfo.setIndexedProperty("model", "focus");
		bootstrapInfo.setIndexedProperty("year", "2013");
		datastore.put(bootstrapInfo);
	}
	
	@RequestMapping(value="register/{id}", method= RequestMethod.POST)
	public @ResponseBody ClientObject register(@PathVariable String id, @RequestBody ClientObject obj) {
		Filter idFilter =
				  new FilterPredicate("id",
				                      FilterOperator.EQUAL,
				                      id);
		Filter nameFilter =
				  new FilterPredicate("name",
				                      FilterOperator.EQUAL,
				                      obj.getName());
		Filter filter =
				  CompositeFilterOperator.and(nameFilter, idFilter);
		Query q = new Query("Objects").setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			datastore.delete(result.getKey());
		}
		Entity objInfo = new Entity("Objects");
		objInfo.setIndexedProperty("id", id);
		objInfo.setIndexedProperty("name", obj.getName());
		datastore.put(objInfo);
		return obj;
	}
	@RequestMapping(value="update/{id}", method= RequestMethod.PUT)
	public @ResponseBody ClientObject update(@PathVariable String id, @RequestBody ClientObject obj) {
		Filter idFilter =
				  new FilterPredicate("id",
				                      FilterOperator.EQUAL,
				                      id);
		Filter nameFilter =
				  new FilterPredicate("name",
				                      FilterOperator.EQUAL,
				                      obj.getName());
		Filter filter =
				  CompositeFilterOperator.and(nameFilter, idFilter);
		Query q = new Query("Objects").setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			datastore.delete(result.getKey());
		}
		Entity objInfo = new Entity("Objects");
		objInfo.setIndexedProperty("id", id);
		objInfo.setIndexedProperty("name", obj.getName());
		datastore.put(objInfo);
		return obj;
	}
	@RequestMapping(value="deregister/{id}", method= RequestMethod.DELETE)
	public void deregister(@PathVariable String id) {
		Filter idFilter =
				  new FilterPredicate("id",
				                      FilterOperator.EQUAL,
				                      id);
		Query q = new Query("Objects").setFilter(idFilter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			datastore.delete(result.getKey());
		}
	}
	@RequestMapping(value="objectInfo/{id}", method= RequestMethod.GET)
	public @ResponseBody Client getClinetObjInfo(@PathVariable String id) {
		Client client = new Client();
		Filter idFilter =
				  new FilterPredicate("id",
				                      FilterOperator.EQUAL,
				                      id);
		Query q = new Query("Objects").setFilter(idFilter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			String name = (String) result.getProperty("name");
			ClientObject obj = new ClientObject(name);
			client.objects.add(obj);
		}
		return client;
	}
	@RequestMapping(value="bootstrap/{id}", method= RequestMethod.GET)
	public @ResponseBody Bootstrap getBootstrap(@PathVariable String id) {
		Filter idFilter =
				  new FilterPredicate("id",
				                      FilterOperator.EQUAL,
				                      id);
		Query q = new Query("Boostrap").setFilter(idFilter);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			Bootstrap bootsTrap = new Bootstrap();
			bootsTrap.setRegisterServerUrl((String)result.getProperty("url"));
			bootsTrap.setBrands((String)result.getProperty("brands"));
			bootsTrap.setModel((String)result.getProperty("model"));
			bootsTrap.setYear((String)result.getProperty("year"));
			return bootsTrap;
		}
		Bootstrap bootsTrap = new Bootstrap();
		bootsTrap.setRegisterServerUrl("unrecognize client");
		return bootsTrap;
	}

}
