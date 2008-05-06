// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TrinaryOperationException.java

package exceptions;

// Referenced classes of package exceptions:
//            SyntacticException

public class TrinaryOperationException extends SyntacticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1729880091179494588L;

	public TrinaryOperationException() {
		this("Syntactic error in trinary operation.");
	}

	public TrinaryOperationException(String s) {
		super(s);
	}
}
