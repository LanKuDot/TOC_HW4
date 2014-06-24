/* The data structure stores the maximum and
 * minimum of the sale-price and the distinct
 * time of saled time, which in the same road.
 */
import java.util.Vector;

public class DataRequest
{
	public int maxSalePrice;
	public int minSalePrice;
	public Vector< Integer > months;

	// Constructor
	public DataRequest()
	{
		maxSalePrice = 0;
		minSalePrice = 999999999;
		months = new Vector< Integer >();
	}
}
