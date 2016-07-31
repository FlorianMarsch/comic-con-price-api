import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

	public static void main(String[] args) {

		new Main().init();

	}

	public void init() {
		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		get("/api/comiccon/2017/", (request, response) -> {

			JSONObject data = new Eventbrite().getContents();

			Map<String, Object> attributes = new HashMap<>();

			attributes.put("data", data.toString());

			return new ModelAndView(attributes, "json.ftl");
		} , new FreeMarkerEngine());
	}

}
