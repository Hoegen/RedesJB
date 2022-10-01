package main;

import java.awt.Toolkit;

public class UserProperties {
	
	public static int getScrWid() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}

	public static int getScrHei() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}
}
