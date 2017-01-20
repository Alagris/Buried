package net.twi.AI.main;

public class MainClassAI
{

	//////////ATTENTION!!!!
	///Code in srcAI 
	///is ugly and was not meant to
	///be developed any further.
	///It was designed to "just work"
	///without wasting time for optimisation etc
	//All code here is just one huge playground
	//where I could freely test my ideas in practice.
	//All the experience I gained here will be used
	//in a brand new project, this time written completely in C++.
	//Stay tuned for more! :D
	//(I leave this code as is in case I needed to come here back
	//again in the future)
	public static void main(String[] args)
	{
		for(int i=0;i<args.length;i++)
			System.out.println(args[i]);
		if (args.length!=0 && args[0].equals("1"))
		{
			new FrameFuncDisplayer();
		}
		else
		{
			new FrameNeuralPixelTest();
		}
	}
}
