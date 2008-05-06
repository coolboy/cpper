// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DividedByZeroException.java

package exceptions;

// Referenced classes of package exceptions:
//            SemanticException

public class DividedByZeroException extends SemanticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4003665148463305388L;

	public DividedByZeroException() {
		this("Divided by 0.");
	}

	public DividedByZeroException(String s) {
		super(s);
	}
}
