/* The data structure stores the maximum and
 * minimum of the sale-price and the the earliest
 * and latest time of the saled time, which in
 * the same road.
 */

public class DataRequest
{
	public int maxSalePrice;
	public int minSalePrice;
	public int maxSaleMonth;
	public int minSaleMonth;

	// Constructor
	public DataRequest()
	{
		maxSalePrice = 0;
		minSalePrice = 999999999;
		maxSaleMonth = 0;
		minSaleMonth = 20000;
	}
}
