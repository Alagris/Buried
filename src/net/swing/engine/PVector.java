package net.swing.engine;

public class PVector
{
	
	protected WPS	vector	= new WPS();
	
	public PVector(WPS vector)
	{
		this.set(vector);
	}
	
	public PVector()
	{
	}
	
	/** Moves a WPS point (body) to the place pointed by this vector */
	public void apply(WPS body)
	{
		body.addB(vector.getB());
		body.addA(vector.getA());
	}
	
	/**
	 * Moves a WPS point (body) to the place pointed by this vector (but only A
	 * axis)
	 */
	public void applyA(WPS body)
	{
		body.addA(vector.getA());
	}
	
	/**
	 * Moves a WPS point (body) to the place pointed by this vector (but only B
	 * axis)
	 */
	public void applyB(WPS body)
	{
		body.addB(vector.getB());
	}
	
	/** Moves a WPS point (body) to the place pointed by this vector */
	public void apply(WPS body, double delta)
	{
		body.addB(vector.getB() * delta);
		body.addA(vector.getA() * delta);
	}
	
	/**
	 * Moves a WPS point (body) to the place pointed by this vector (but only A
	 * axis)
	 */
	public void applyA(WPS body, double delta)
	{
		body.addA(vector.getA() * delta);
	}
	
	/**
	 * Moves a WPS point (body) to the place pointed by this vector (but only B
	 * axis)
	 */
	public void applyB(WPS body, double delta)
	{
		body.addB(vector.getB() * delta);
	}
	
	/**
	 * Returns a new WPS point moved with this vector (but not applies vector to
	 * already existing point)
	 */
	public WPS abstractApply(WPS body)
	{
		WPS w = new WPS(body);
		w.addB(vector.getB());
		w.addA(vector.getA());
		return w;
	}
	
	/**
	 * Returns a new WPS point moved with this vector (but not applies vector to
	 * already existing point)
	 */
	public WPS abstractApply(WPS body, double delta)
	{
		WPS w = new WPS(body);
		w.addB(vector.getB() * delta);
		w.addA(vector.getA() * delta);
		return w;
	}
	
	public WPS toWPS()
	{
		return vector;
	}
	
	public void addVector(double a, double b)
	{
		vector.addA(a);
		vector.addB(b);
	}
	
	public void addVector(WPS anotherVector)
	{
		vector.addA(anotherVector.getA());
		vector.addB(anotherVector.getB());
	}
	
	public void addVectors(WPS[] anotherVectors)
	{
		for (WPS anotherVector : anotherVectors)
		{
			addVector(anotherVector);
		}
	}
	
	public void addVector(PVector anotherVector)
	{
		vector.addA(anotherVector.toWPS().getA());
		vector.addB(anotherVector.toWPS().getB());
	}
	
	public void addVectors(PVector[] anotherVectors)
	{
		for (PVector anotherVector : anotherVectors)
		{
			addVector(anotherVector);
		}
	}
	
	@Override
	public String toString()
	{
		return "Vector( A:" + vector.a + " B:" + vector.b + " )";
	}
	
	/** Subtracts values of a and b from this vector */
	public void subtractVector(double a, double b)
	{
		vector.subtractA(a);
		vector.subtractB(b);
	}
	
	/** Subtracts value of another vector from this vector */
	public void subtractVector(WPS anotherVector)
	{
		vector.subtractA(anotherVector.getA());
		vector.subtractB(anotherVector.getB());
	}
	
	/** Subtracts values of another vectors from this vector */
	public void subtractVectors(WPS[] anotherVectors)
	{
		for (WPS anotherVector : anotherVectors)
		{
			subtractVector(anotherVector);
		}
	}
	
	public void subtractVector(PVector anotherVector)
	{
		vector.subtractA(anotherVector.toWPS().getA());
		vector.subtractB(anotherVector.toWPS().getB());
	}
	
	public void subtractVectors(PVector[] anotherVectors)
	{
		for (PVector anotherVector : anotherVectors)
		{
			subtractVector(anotherVector);
		}
	}
	
	public void reset()
	{
		vector = new WPS();
	}
	
	public WPS abstractApplyAxisA(WPS body, double delta)
	{
		WPS w = new WPS(body);
		w.addA(vector.getA() * delta);
		return w;
	}
	
	public WPS abstractApplyAxisB(WPS body, double delta)
	{
		WPS w = new WPS(body);
		w.addB(vector.getB() * delta);
		return w;
	}
	
	public WPS abstractApplyAxisA(WPS body)
	{
		WPS w = new WPS(body);
		w.addA(vector.getA());
		return w;
	}
	
	public WPS abstractApplyAxisB(WPS body)
	{
		WPS w = new WPS(body);
		w.addB(vector.getB());
		return w;
	}
	
	public void resetA()
	{
		vector.a = 0;
	}
	
	public void resetB()
	{
		vector.b = 0;
	}
	
	public void subtractVectorAxisA(WPS vector)
	{
		this.vector.subtractA(vector.getA());
	}
	
	public void subtractVectorAxisB(WPS vector)
	{
		this.vector.subtractB(vector.getB());
	}
	
	public void addVectorAxisA(WPS vector)
	{
		this.vector.addA(vector.getA());
	}
	
	public void addVectorAxisB(WPS vector)
	{
		this.vector.addB(vector.getB());
	}
	
	public void subtractVectorAxisA(double a)
	{
		this.vector.subtractA(a);
	}
	
	public void subtractVectorAxisB(double b)
	{
		this.vector.subtractB(b);
	}
	
	public void addVectorAxisA(double a)
	{
		this.vector.addA(a);
	}
	
	public void addVectorAxisB(double b)
	{
		this.vector.addB(b);
	}
	
	public void setA(double a)
	{
		this.vector.a = a;
	}
	
	public void setB(double b)
	{
		this.vector.b = b;
	}
	
	public void set(double a, double b)
	{
		this.vector.a = a;
		this.vector.b = b;
	}
	
	public void set(WPS vector)
	{
		this.vector.a = vector.a;
		this.vector.b = vector.b;
	}
	
}
