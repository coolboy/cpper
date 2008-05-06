// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MissingLeftParenthesisException.java

package exceptions;

// Referenced classes of package exceptions:
//            SyntacticException

public class MissingLeftParenthesisException extends SyntacticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2935546388792638093L;

	public MissingLeftParenthesisException() {
		this("Left parenthesis '(' is expected.");
	}

	public MissingLeftParenthesisException(String s) {
		super(s);
	}
}
