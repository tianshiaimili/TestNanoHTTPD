package com.example.mynanohttpd;

import android.util.Log;


public class LogUtils {
	
	private static final boolean DEBUG_E = true;
	private static final boolean DEBUG_D = true;
	private static final boolean DEBUG_W = true;
	private static final boolean DEBUG_V = true;
	private static final boolean DEBUG_I = true;
	private static final String TAG = "LogUtils";

	public static void e(String message) {
		if (DEBUG_E) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 4) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				
				int lineNumber = elements[3].getLineNumber();
//				Log.e(className + "." + methodName + "():" + lineNumber,
//						message);
				
				Log.e(TAG,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}
	
	public static void e(String tag,String message) {
		if (DEBUG_E) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 4) {
				Log.e(tag, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
//				Log.e(className + "." + methodName + "():" + lineNumber,
//						message);
				Log.e(tag,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}

	public static void d(String message) {
		if (DEBUG_D) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
//				Log.d("TAG",className + "." + methodName + "():" + lineNumber+"�� "+
//						message);
				
				Log.d(TAG,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}
	
	
	public static void d(String tag,String message) {
		if (DEBUG_D) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
//				Log.d("TAG",className + "." + methodName + "():" + lineNumber+"�� "+
//						message);
				
				Log.d(tag,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}

	public static void i(String message) {
		if (DEBUG_I) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
//				Log.i(className + "." + methodName + "():" + lineNumber,
//						message);
				Log.i(TAG,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}
	
	///
	public static void i(String tag,String message) {
		if (DEBUG_I) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
//				Log.i(className + "." + methodName + "():" + lineNumber,
//						message);
				Log.i(tag,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}
	

	public static void w(String message) {
		if (DEBUG_W) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
//				Log.w(className + "." + methodName + "():" + lineNumber,
//						message);
				Log.w(TAG,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}
	
	////
	public static void w(String tag,String message) {
		if (DEBUG_W) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				
				Log.w(tag,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
			}
		}
	}
	

	public static void v(String message) {
		if (DEBUG_V) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				
				Log.v(TAG,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
				
			}
		}
	}

	///
	public static void v(String tag,String message) {
		if (DEBUG_V) {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName
						.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();

				Log.v(tag,"["+className + ":"+methodName +" _ "+ lineNumber+"�� ]:"+
						message);
				
			}
		}
	}
	
}
