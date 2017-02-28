package custom.valve;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

public class MyValve extends ValveBase {

	static Properties prop = new Properties();

	public static boolean init_valve() {
		InputStream input = null;
		try {
			String configFile = "/config.properties";

			URL ucConfig = MyValve.class.getResource(configFile);
			File f = new File(configFile);
			if (ucConfig != null) {
				f = new File(ucConfig.getFile());
			}
			if (f.exists()) {
				try {
					FileInputStream fis = new FileInputStream(f);
					prop.load(fis);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return false;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;

				}
			}
		}

		return true;

	}

	protected void initInternal() {
		init_valve();

	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {

		String[] invalid_ext = prop.get("invalid_ext").toString().split(",");
		String[] blacklist = prop.get("blacklist").toString().split(",");
		String server_path = prop.getProperty("server_path");
		// System.out.println("inint value a7a " + server_path);

		String uri = request.getRequestURI().toString();

		if (in_list(blacklist, request.getContextPath())
				|| (uri.contains(".") && Arrays.asList(invalid_ext).contains(
						uri.substring(uri.lastIndexOf('.'), uri.length())))) {

			getNext().invoke(request, response);

		} else {

			String script = "<script type='text/javascript' src=";
			script += "\"";
			script += server_path + "/thrift.js\"></script>";
			script += "<script type='text/javascript' src=";
			script += "\"";
			script += server_path + "/Types_types.js\"></script>";
			script += "<script type='text/javascript' src=";
			script += "\"";
			script += server_path + "/TAny2Pmp.js\"></script>";

			script += "<script type='text/javascript' src=";
			script += "\"";
			script += server_path + "/myscript.js\"></script>";

			PrintWriter out = response.getWriter();

			CharResponseWrapper responseWrapper = new CharResponseWrapper(
					(Response) response);

			String servletResponse = new String(responseWrapper.toString());

			out.write(servletResponse + script);
			getNext().invoke(request, response);

		}
	}

	public static void main(String[] args) {
		// System.out.println("adasdas");

		init_valve();
		String[] blacklist = prop.getProperty("blacklist").toString()
				.split(",");
		System.out.println(prop.getProperty("blacklist").toString());

		// if (in_list(blacklist, "   /examples   ")) {
		// System.out.println("in BL  ");
		//
		// } else {
		// System.out.println("NOT BL  ");
		// }

	}

	public static boolean in_list(String[] blacklist, String app_name) {

		try {
			app_name = app_name.replaceAll("\\s+", "").substring(1)
					.toLowerCase();
		} catch (Exception e) {
			return false;
		}
		for (String str : blacklist) {
			if (str.trim().toLowerCase().contains(app_name))
				return true;
		}
		return false;

	}

}
