/* Parsing the json file from the requested URL, and
 * parsing data. */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.*;

public class JsonWebReader
{
	/* parsing the json file from the requested URL,
	 * and return a JSONArray which stores multiple json entries. */
	public JSONArray readJsonFromURL( String url )
		throws IOException, JSONException
	{
		InputStream input = new URL(url).openStream();

		try
		{
			BufferedReader reader = new BufferedReader(
					new InputStreamReader( input, Charset.forName("UTF-8") ) );
			JSONArray json = new JSONArray( new JSONTokener( reader ) );
			return json;
		}
		finally
		{
			input.close();
		}
	}

}	// end of JsonWebReader class
