import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

			Map<String, Object> attributes = new HashMap<>();

			try {
				JSONObject data = new JSONObject();
				data.put("SamstagTicket", 0);
				data.put("SontagTicket", 0);
				data.put("WochenendTicket", 0);
				data.put("VIPTicket", 0);
				for (int i = 0; i < 25; i++) {
					data.put("Fotoshoot " + i, 0);
				}
				attributes.put("data", data.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return new ModelAndView(attributes, "json.ftl");
		} , new FreeMarkerEngine());
	}

}
