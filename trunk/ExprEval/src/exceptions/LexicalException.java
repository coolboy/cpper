// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LexicalException.java

package exceptions;

// Referenced classes of package exceptions:
//            ExpressionException

public class LexicalException extends ExpressionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6592161451915210093L;

	public LexicalException() {
		this("Lexical error.");
	}

	public LexicalException(String s) {
		super(s);
	}
}
