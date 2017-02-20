package custom.valve;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

public class MyValve extends ValveBase {

	String server_path = "localhost:92";
	String invalid_ext_prop = ".png,.jpg,.gif,.jpeg,.js,.css";
	String blacklist_prop = "examples,anotherone";

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {

		String[] invalid_ext = invalid_ext_prop.toString().split(",");
		String[] blacklist = blacklist_prop.toString().split(",");


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
