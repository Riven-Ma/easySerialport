package com.liubike.view;

import javax.swing.JFrame;

public abstract class AbstractView implements View {

	public AbstractView() {
		super();
	}
	
	protected JFrame frame;
	
	public void show() {
		this.frame.setVisible(true);
	}


	
}
