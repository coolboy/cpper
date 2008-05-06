// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MissingRightParenthesisException.java

package exceptions;

// Referenced classes of package exceptions:
//            SyntacticException

public class MissingRightParenthesisException extends SyntacticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2171716540627902293L;

	public MissingRightParenthesisException() {
		this("Right parenthesis ')' is expected.");
	}

	public MissingRightParenthesisException(String s) {
		super(s);
	}
}
