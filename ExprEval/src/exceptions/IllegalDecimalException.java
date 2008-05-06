// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IllegalDecimalException.java

package exceptions;

// Referenced classes of package exceptions:
//            LexicalException

public class IllegalDecimalException extends LexicalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6382501059776911465L;

	public IllegalDecimalException() {
		this("Malformed decimal constant.");
	}

	public IllegalDecimalException(String s) {
		super(s);
	}
}
