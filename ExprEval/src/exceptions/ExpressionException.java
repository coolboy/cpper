// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExpressionException.java

package exceptions;

public class ExpressionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3441333482954368138L;

	public ExpressionException() {
		this("Error found in the expression.");
	}

	public ExpressionException(String s) {
		super(s);
	}
}
