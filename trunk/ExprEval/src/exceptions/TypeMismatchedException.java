// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TypeMismatchedException.java

package exceptions;

// Referenced classes of package exceptions:
//            SemanticException

public class TypeMismatchedException extends SemanticException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 75818522773086227L;

	public TypeMismatchedException() {
		this("Type mismatched.");
	}

	public TypeMismatchedException(String s) {
		super(s);
	}
}
