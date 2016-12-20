package com.pennypop.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class GameScreen implements Screen {

	private final Stage stage;
	private final SpriteBatch spriteBatch;
	private Game game;
	public int x = 7;
	public int y = 6;
	// row and col
	private ButtonData[][] board;

	private Table tableboard;
	private ImageButton button;

	private ButtonData t;
	private ImageButton redbutton;
	private ImageButton yellowbutton;
	protected int RorY = 0;
	// RorY==0:red
	// RorY==1:yellow

	public GameScreen(Game game) {
		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
		this.game = game;
		board = new ButtonData[x][y];
		tableboard = new Table();
		redbutton = CreateButton("red.png");
		yellowbutton = CreateButton("yellow.png");

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		stage.dispose();
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		Table.drawDebug(stage);

		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {

				button = CreateButton("background.png");
				// draw the button
				t = new ButtonData(i, y - j - 1, button, 3);
				// pass data to listenerinsider

				button.addListener(new ChangeListener() {
					ButtonData mydata = t;
					private int sety;
					// for the location

					@Override
					public void changed(ChangeEvent event, Actor actor) {

						System.out.println(mydata.getX() + "  " + mydata.getY());
						boolean flag = false;
						for (int k = 0; k < y; k++) {
							if (board[mydata.getX()][k].getType() == 3) {
								sety = k;
								flag = true;

								break;
							}

						}
						// check is it aviable to move, such as not beyond range
						if (flag) {

							if (RorY == 0) {
								board[mydata.getX()][sety].getButton().setStyle(redbutton.getStyle());
							} else if (RorY == 1) {
								board[mydata.getX()][sety].getButton().setStyle(yellowbutton.getStyle());

							}
							board[mydata.getX()][sety].setType(RorY);
							RorY = (RorY + 1) % 2;

						}

					}
				});

				board[i][y - j - 1] = t;
				tableboard.add(button);

			}
			tableboard.row();

		}
		tableboard.debug();

		tableboard.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		stage.addActor(tableboard);

	}

	private ImageButton CreateButton(String image) {
		// TODO Auto-generated method stub
		Texture myTexture3 = new Texture(Gdx.files.internal(image));
		TextureRegion myTextureRegion3 = new TextureRegion(myTexture3);
		TextureRegionDrawable myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);
		ImageButton tembutton = new ImageButton(myTexRegionDrawable3);
		return tembutton;

	}

	@Override
	public void pause() {
		// Irrelevant on desktop, ignore this
	}

	@Override
	public void resume() {
		// Irrelevant on desktop, ignore this
	}

}