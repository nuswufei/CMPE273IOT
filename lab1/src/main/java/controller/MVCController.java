package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/*")
public class MVCController {
	Map<String, CounterItem> dic = new HashMap<>();
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

	@RequestMapping(value = "counter", method = RequestMethod.POST)
	public @ResponseBody CounterItem post(@RequestBody CounterItem item) {
		CounterItem res = dic.get(item.id);
		dic.put(item.id, item);
		return res;
	}
	
}
