/* Parsing the json file from the requested URL, and
 * parsing data with regex. */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class JsonWebReader
{
	/* parsing the json file from the requested URL with regex,
	 * and return a JSONArray which stores multiple json entries. */
	public JSONArray readJsonFromURL( String url, DataRequest request )
		throws IOException, JSONException
	{
		InputStream input = new URL(url).openStream();

		try
		{
			BufferedReader reader = new BufferedReader(
					new InputStreamReader( input, Charset.forName("UTF-8") ) );
			JSONArray json = new JSONArray( readWithRegex( reader, request ) );
			return json;
		}
		finally
		{
			input.close();
		}
	}

	private String readWithRegex( BufferedReader reader, DataRequest request )
		throws IOException
	{
		StringBuilder sb = new StringBuilder();  // Store the parsed string
		String buffer;     // Buffer the string read from BufferedReader
		boolean noMatch = true;  // If there is no content matched

		/* Generate regex string from input argument */
		String regexStr = "(?dm)\\{.*" + request.zone + ".*"
			+ request.zone + request.road + ".*\\}";

		/* The regex */
		Pattern pattern = Pattern.compile( regexStr );
		Matcher matcher;

		// Append left bucket to indicate tha begin of Json array
		sb.append( '[' );

		/* Read one line one time, and then parse the string with regex.
		 * Append the parsed result to a StringBuilder. */
		while ( ( buffer = reader.readLine() ) != null )
		{
			matcher = pattern.matcher( buffer );
			while ( matcher.find() )
			{
				noMatch = false;  // Matched

				sb.append( matcher.group() );
				sb.append( ',' );
			}
		}

		/* Remove the trail comma, and append right bucket to indicate
		 * the end of Json array. */
		if ( !noMatch )
			sb.deleteCharAt( sb.length() - 1 );
		sb.append( ']' );

		return sb.toString();
	}
}	// end of JsonWebReader class
