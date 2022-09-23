package com.mt.minilauncher;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Debug {
	public static Shell parent;
	
	/**
	 * Debug class using SWT dialogs
	 * @param parent The main window to parent to
	 */
	public Debug(Shell parent) {
		Debug.parent = parent;
	}
	
	/**
	 * Information icon
	 */
	public static final int INF = SWT.ICON_INFORMATION;
	
	/**
	 * Error icon
	 */
	public static final int ERR = SWT.ICON_ERROR;
	
	/**
	 * Test icon
	 */
	public static final int TST = SWT.ICON_WORKING;
	
	/**
	 * Warning icon
	 */
	public static final int WARN = SWT.ICON_WARNING;
	
	/**
	 * Prompt/Question icon
	 */
	public static final int PROMPT = SWT.ICON_QUESTION;
	
	/**
	 * Integer constant for adding a pair of OK and Cancel buttons
	 */
	public static final int OK_CANCEL = (SWT.OK | SWT.CANCEL);
	
	/**
	 * Integer constant for OK Button
	 */
	public static final int OK = SWT.OK;
	
	/**
	 * Integer constant for Cancel button
	 */
	public static final int CANCEL = SWT.CANCEL;
	
	/**
	 * Standard popup dialog
	 * @param title title of the window
	 * @param message text body within the window
	 * @param messagetype One of the integer constants {INF, ERR, TST, WARN, PROMPT, OK_CANCEL, OK, CANCEL}
	 * @return ID of the dialog button clicked (OK, CANCEL, etc)
	 */
	public static int callCrashDialog(String title, String message, int messagetype) {
		MessageBox dialog = new MessageBox(parent, messagetype);
		dialog.setText(title);
		dialog.setMessage(message);
		
		return dialog.open();
	}
	
	/**
	 * This dialog will always have OK and CANCEL buttons
	 * @param title the title of the window
	 * @param message text body within the window
	 * @return result of the dialog as a button ID (OK, CANCEL)
	 */
	public static int callConfirmDialog(String title, String message) {
		MessageBox dialog = new MessageBox(parent, PROMPT | OK_CANCEL);
		dialog.setText(title);
		dialog.setMessage(message);
		
		return dialog.open();
	}
}
