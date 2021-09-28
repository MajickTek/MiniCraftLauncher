package com.mt.minilauncher;

import javax.swing.JOptionPane;

public class Debug {
    public static int INF = JOptionPane.INFORMATION_MESSAGE;
    public static int ERR = JOptionPane.ERROR_MESSAGE;
    public static int TST = JOptionPane.PLAIN_MESSAGE;
    public static int WARN = JOptionPane.WARNING_MESSAGE;

    public static int OK_CANCEL = JOptionPane.OK_CANCEL_OPTION;
    public static int OK = JOptionPane.OK_OPTION;
    public static int CANCEL = JOptionPane.CANCEL_OPTION;
    
    public static void callCrashDialog(String title, String message, int msgtype) {
        JOptionPane.showMessageDialog(null, message, title, msgtype);
    }

    public static void callCrashDialog(String title, Object message, int msgtype) {
        JOptionPane.showMessageDialog(null, message, title, msgtype);
    }
    
    public static int callConfirmDialog(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, OK_CANCEL);
    }
    
}