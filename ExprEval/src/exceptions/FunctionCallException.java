// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionCallException.java

package exceptions;

// Referenced classes of package exceptions:
//            SyntacticException

public class FunctionCallException extends SyntacticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2183590576548419742L;

	public FunctionCallException() {
		this("Syntactic error in function call.");
	}

	public FunctionCallException(String s) {
		super(s);
	}
}
