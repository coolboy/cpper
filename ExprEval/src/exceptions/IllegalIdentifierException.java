// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IllegalIdentifierException.java

package exceptions;

// Referenced classes of package exceptions:
//            LexicalException

public class IllegalIdentifierException extends LexicalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5585964570182254086L;

	public IllegalIdentifierException() {
		this("Not a predefined identifier.");
	}

	public IllegalIdentifierException(String s) {
		super(s);
	}
}
