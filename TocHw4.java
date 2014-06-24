/* Analyze the real-price housing information in Taiwan.
 * (http://www.datagarage.io/datasets/ktchuang/realprice/A)
 * The data format is JSON. Scan the date and find out which
 * road in the city has house trading records spread in max
 * distinct month. And print out the roads name and their
 * citys with maximum sale price and minimum sale price.
 *
 * Usage: java -cp ".:json.jar" TOC_HW4 \
 * http://www.datagarage.io/api/538447a07122e8a77dfe2d86
 */

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Vector;

import org.json.*;

public class TocHw4
{
	public static void main( String[] args )
	{
		JsonWebReader dataInput = new JsonWebReader();

		try
		{
			// args[0] is the requesting URL
			JSONArray data = dataInput.readJsonFromURL( args[0] );
			dataAnalysis( data );
		}
		catch ( JSONException e )
		{
			System.out.println( e.toString() );
		}
		catch ( IOException e )
		{
			System.out.println( e.toString() );
		}
	}

	/* Categorize the data by the road name. Analyze the maximum and
	 * minimun of the sale-price and the distinct time of saled
	 * time. Store the result to the hash table. The key of the hash
	 * table is the road name and the value of it is the result.
	 *
	 * - data: [in] The JSON array that stores the information of the
	 *   house saleing.
	 */
	public static void dataAnalysis( JSONArray data )
		throws JSONException
	{
		Hashtable< String, DataRequest > result =
			new Hashtable< String, DataRequest >();
		String location, roadName;
		DataRequest request;
		int salePrice, saleMonth;
		JSONObject element;

		// The regex for parsing the road name
		String regexStr = "(?dm)(.*[路街])|(.*大道)|(.*[^0-9]巷).*?";
		Pattern pattern = Pattern.compile( regexStr );
		Matcher matcher;

		// Scan each JSON element
		for ( int i = 0; i < data.length(); ++i )
		{
			if ( !data.isNull(i) )
			{
				element = data.getJSONObject(i);
				// Match the vaild road name
				location = (String)element.get( "土地區段位置或建物區門牌" );
				matcher = pattern.matcher( location );
				// Get vaild data
				if ( matcher.find() )
				{
					roadName = matcher.group();
					// Is the new here?
					if ( !result.containsKey( roadName ) )
					{
						// Create new element for the hash table
						request = new DataRequest();
					}
					else
					{
						// Get the element from the hash table
						request = result.get( roadName );
					}

					// Compare the sale-price and sale-month
					salePrice = ((Integer)element.get( "總價元" )).intValue();
					saleMonth = (Integer)element.get( "交易年月" );
					// Maximun sale-price
					if ( salePrice > request.maxSalePrice )
						request.maxSalePrice = salePrice;
					// Minimun sale-price
					if ( salePrice < request.minSalePrice )
						request.minSalePrice = salePrice;
					// The distinct month
					if ( !request.months.contains( saleMonth ) )
						request.months.add( saleMonth );

					// Update the hash table
					result.put( roadName, request );
				}	// end of if ( matcher.find() ) func
			}	// end of ( vaild JSON element )
		}	// end of for ( JSON element )

		// Find the max distinct month
		Enumeration keys = result.keys();
		String output = "";
		int maxDistinctMonth = 0;

		while ( keys.hasMoreElements() )
		{
			String key = keys.nextElement().toString();
			request = (DataRequest)result.get( key );

			if ( request.months.size() > maxDistinctMonth )
			{
				maxDistinctMonth = request.months.size();
				// Reset String
				output = appendResult( key, request );
			}
			// There is not only one case having max distinct month.
			else if ( request.months.size() == maxDistinctMonth )
			{
				output = output + "\n" + appendResult( key, request );
			}
		}

		// Output the result
		System.out.println( output );

	}	// end of dataAnalysis() func

	/* Append the road name and the maximun and the minimum of the saled
	 * price, and transform the string to the proper format.
	 *
	 * - roadName: [in] The road name
	 * - request: [in] The data structure of the road
	 *
	 * Return the procesed string.
	 */
	public static String appendResult( String roadName, DataRequest request )
	{
		return roadName + ", 最高成交價: " + request.maxSalePrice +
			", 最低成交價: " + request.minSalePrice;
	}

}	// end of TOCHw4 class
