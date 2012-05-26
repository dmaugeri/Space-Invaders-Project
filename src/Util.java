/*
 * Noninstantiable utility class
 */
public class Util
{
	public static boolean debug = false;

	/*
	 * Suppress default constructor for noninstantiability
	 */
	private Util()
	{
		throw new AssertionError();
	}

	/*
	 * Print object if debug is true
	 */
	public static void debugObject(Object o)
	{
		if (debug)
			System.out.println(o);
	}

	/*
	 * Print string with newline if debug if true
	 */
	public static void debugPrint(String s)
	{
		if (debug)
			System.out.print(s);
	}

	/*
	 * Print string with newline if debug if true
	 */
	public static void debugPrintln(String s)
	{
		if (debug)
			System.out.println(s);
	}
}
