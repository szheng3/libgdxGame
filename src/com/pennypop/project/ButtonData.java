package com.pennypop.project;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class ButtonData {
	private int x;
	// x: colum
	private int y;
	// y: row
	private ImageButton button;
	private int type;
	// type: 1 red
	// type: 2 yellow
	// type: 3 no botton

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ImageButton getButton() {
		return button;
	}

	public void setButton(ImageButton button) {
		this.button = button;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ButtonData(int x, int y, ImageButton button, int type) {
		super();
		this.x = x;
		this.y = y;
		this.button = button;
		this.type = type;
	}

}
