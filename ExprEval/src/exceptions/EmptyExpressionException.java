// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyExpressionException.java

package exceptions;

// Referenced classes of package exceptions:
//            SyntacticException

public class EmptyExpressionException extends SyntacticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8594606805313141917L;

	public EmptyExpressionException() {
		this("The expression is empty.");
	}

	public EmptyExpressionException(String s) {
		super(s);
	}
}
