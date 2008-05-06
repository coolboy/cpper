// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SyntacticException.java

package exceptions;

// Referenced classes of package exceptions:
//            ExpressionException

public class SyntacticException extends ExpressionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7205385625743253580L;

	public SyntacticException() {
		this("Syntactic error.");
	}

	public SyntacticException(String s) {
		super(s);
	}
}
