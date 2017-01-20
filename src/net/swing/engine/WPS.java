package net.swing.engine;

public class WPS
{
	public double	a, b;
	
	public WPS()
	{
	}
	
	public WPS(double A, double B)
	{
		set(A, B);
	}
	
	public WPS(WPS wps)
	{
		set(wps);
	}
	
	public void set(double A, double B)
	{
		setA(A);
		setB(B);
	}
	
	public void set(WPS anotherWPS)
	{
		setA(anotherWPS.a);
		setB(anotherWPS.b);
	}
	
	public double getB()
	{
		return b;
	}
	
	public void setB(double b)
	{
		this.b = b;
	}
	
	public double getA()
	{
		return a;
	}
	
	public void setA(double a)
	{
		this.a = a;
	}
	
	public boolean equals(WPS wps)
	{
		if (a == wps.a) if (b == wps.b) return true;
		return false;
	}
	
	public void addB(double b)
	{
		this.b += b;
	}
	
	public void addA(double a)
	{
		this.a += a;
	}
	
	public void subtractB(double b)
	{
		this.b -= b;
	}
	
	public void subtractA(double a)
	{
		this.a -= a;
	}
	
	public void subtracWPS(WPS wps)
	{
		subtractA(wps.a);
		subtractB(wps.b);
	}
	
	public void addWPS(WPS wps)
	{
		addA(wps.a);
		addB(wps.b);
	}
	
	@Override
	public String toString()
	{
		return "WPS( A:" + a + " B:" + b + " )";
	}
	
	/** Returns string that is used to save game data */
	public String toCommand()
	{
		return a + "," + b;
	}
	
	public WPS shiftB(double b)
	{
		return new WPS(a, this.b + b);
	}
	
	public WPS shiftA(double a)
	{
		return new WPS(this.a + a, b);
	}
	
	public WPS shiftAB(double a, double b)
	{
		return new WPS(this.a + a, this.b + b);
	}
	
	public static WPS parseCoordinates(String coords)
	{
		WPS p = new WPS();
		boolean nowA = true, wasDot = false;
		String number = "";
		for (char c : coords.toCharArray())
		{
			if (Character.isDigit(c) || c == '-')
			{
				number = number + c;
			}
			else if (c == '.' && !wasDot)
			{
				number = number + c;
				wasDot = true;
			}
			else if (nowA)
			{
				nowA = false;
				wasDot = false;
				if (number != null)
				{
					p.setA(Float.parseFloat(number));
				}
				number = "";
			}
			else
			{
				break;
			}
		}
		if (number != null)
		{
			p.setB(Float.parseFloat(number));
		}
		return p;
	}
	
	/** Checks if wps values a and b are higher than values of this WPS */
	public boolean isLonger(WPS wps)
	{
		if (wps.a > a)
		{
			if (wps.b > b)
			{
				return true;
			}
		}
		return false;
	}
	
	/** Checks if wps values a and b are lower than values of this WPS */
	public boolean isShorter(WPS wps)
	{
		return wps.doPythagoreanTheorem() < doPythagoreanTheorem();
	}
	
	/** returns the length of c */
	public double doPythagoreanTheorem()
	{
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}
	
	public WPS getDifference(WPS wps)
	{
		return new WPS(Math.abs(a - wps.a), Math.abs(b - wps.b));
	}
}
