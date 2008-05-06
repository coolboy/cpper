// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IllegalSymbolException.java

package exceptions;

// Referenced classes of package exceptions:
//            LexicalException

public class IllegalSymbolException extends LexicalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8875880530031766749L;

	public IllegalSymbolException() {
		this("Unknown character.");
	}

	public IllegalSymbolException(String s) {
		super(s);
	}
}
