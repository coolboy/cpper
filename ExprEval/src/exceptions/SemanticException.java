// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SemanticException.java

package exceptions;

// Referenced classes of package exceptions:
//            ExpressionException

public class SemanticException extends ExpressionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026669804388327135L;

	public SemanticException() {
		this("Semantic error.");
	}

	public SemanticException(String s) {
		super(s);
	}
}
