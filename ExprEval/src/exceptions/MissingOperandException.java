// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MissingOperandException.java

package exceptions;

// Referenced classes of package exceptions:
//            SyntacticException

public class MissingOperandException extends SyntacticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5179954944816716839L;

	public MissingOperandException() {
		this("An operand is expected.");
	}

	public MissingOperandException(String s) {
		super(s);
	}
}
