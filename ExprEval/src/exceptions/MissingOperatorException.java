// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MissingOperatorException.java

package exceptions;

// Referenced classes of package exceptions:
//            SyntacticException

public class MissingOperatorException extends SyntacticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5057908909161441404L;

	public MissingOperatorException() {
		this("An operator is expected.");
	}

	public MissingOperatorException(String s) {
		super(s);
	}
}
