package net.swing.src.data;

public class Test
{
	
	private static long	time1, time2, measuredDelay, defaultDelay, record = 0;
	
	/** Starts testing code performance */
	public static void start()
	{
		// measuring how much time takes nanoTime() method (called twice){
		time1 = System.nanoTime();
		time2 = System.nanoTime();
		defaultDelay = time2 - time1;
		// }
		
		time1 = System.nanoTime();
	}
	
	/** Finishes testing code performance and displays results */
	public static void end()
	{
		time2 = System.nanoTime();
		measuredDelay = time2 - time1;
		measuredDelay -= defaultDelay;
		String delayToText = "";
		for (int rest; measuredDelay != 0; measuredDelay = rest)
		{
			rest = (int) (measuredDelay / 1000);
			delayToText = "," + (measuredDelay - rest * 1000) + delayToText;
		}
		delayToText = delayToText.replaceFirst(",", "");
		System.out.println("=============================");
		System.out.println("=======Code performance======");
		System.out.println("==start time: " + time1);
		System.out.println("==end  time:  " + time2);
		System.out.println("==delay: " + delayToText + " nanosec");
		System.out.println("=============================");
	}
	
	/**
	 * Displays the lowest performance (highest delay) ever recorded. Call it
	 * after end() method
	 */
	public static void collectTop()
	{
		if (measuredDelay > record)
		{
			record = measuredDelay;
		}
		System.out.println("==higest recorded delay:" + record);
		System.out.println("=============================");
	}
	
	public static void eraseRecords()
	{
		record = 0;
	}
	
}
