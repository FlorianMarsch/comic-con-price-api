import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Eventbrite {

	private static final String url = "http://www.eventbrite.de/e/comic-con-germany-01-bis-02-juli-2017-stuttgart-tickets-26657854360?aff=es2";

	public JSONObject getContents() {
		JSONObject data = new JSONObject();
		try {
			data.put("saturday", 0);
			data.put("sunday", 0);
			data.put("weekend", 0);
			data.put("vip", 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String content = loadFile(url);

		Document doc = Jsoup.parse(content);
		Elements lines = doc.select(".ticket_row");
		for (int i = 0; i < lines.size(); i++) {
			Element line = lines.get(i);
			if (!line.toString().contains("KIND")) {
				String param = null;
				if (line.toString().contains("TAGESTICKET SAMSTAG")) {
					param = "saturday";
				} else if (line.toString().contains("TAGESTICKET SONNTAG")) {
					param = "sunday";
				} else if (line.toString().contains("WOCHENENDTICKET")) {
					param = "weekend";
				}
				if (param != null) {

					int value = 0;

					Elements elementsByTag = line.getElementsByTag("td");
					Element last = null;
					for (int j = 0; j < elementsByTag.size(); j++) {
						last = elementsByTag.get(j);
					}
					if (last.toString().contains("None")) {
						value = 0;
					} else {
						System.out.println(param + " : " + last.toString());
						value = 0;
						// TODO parse
					}

					try {
						data.put(param, value);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else {
			}
		}

		return data;
	}

	private String loadFile(String url) {

		StringBuffer tempReturn = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String s;

			while ((s = dis.readLine()) != null) {
				tempReturn.append(s);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempReturn.toString();
	}

}
