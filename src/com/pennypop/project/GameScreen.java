/**
 * @author: Shuai Zheng
 */

package com.pennypop.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	public int countforwin = 4;
	// number for win
	private ButtonData[][] board;

	private Table tableboard;
	private ImageButton button;

	private ButtonData t;
	private ImageButton redbutton;
	private ImageButton yellowbutton;
	protected int RorY = 0;
	private boolean resultwin;
	private BitmapFont font;
	private Label WhoWin;
	private Table wintable;
	private Table rootable;
	private MainScreen screen;

	public GameScreen(Game game) {
		font = new BitmapFont(Gdx.files.internal("font.fnt"), false);

		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
		this.game = game;
		board = new ButtonData[x][y];
		tableboard = new Table();
		redbutton = CreateButton("red.png");
		yellowbutton = CreateButton("yellow.png");
		wintable = new Table();
		rootable = new Table();

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
		WhoWin = new Label("", new Label.LabelStyle(font, Color.RED));

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
						CheckForWin();

					}
				});

				board[i][y - j - 1] = t;
				tableboard.add(button);

			}
			tableboard.row();

		}
		tableboard.debug();
		wintable.add(WhoWin).colspan(3).center().pad(10);
		rootable.add(wintable);
		rootable.row();
		rootable.add(tableboard);

		rootable.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		stage.addActor(rootable);

	}

	private ImageButton CreateButton(String image) {
		// TODO Auto-generated method stub
		Texture myTexture3 = new Texture(Gdx.files.internal(image));
		TextureRegion myTextureRegion3 = new TextureRegion(myTexture3);
		TextureRegionDrawable myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);
		ImageButton tembutton = new ImageButton(myTexRegionDrawable3);
		return tembutton;

	}

	private void CheckForWin() {

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				for (int rory = 0; rory < 2; rory++) {

					if (board[i][j].getType() == rory) {
						int tempx = i;
						int tempy = j;
						int countforcol = 0;
						while (true) {

							if (tempx < x && board[tempx][j].getType() == rory) {
								tempx++;
								countforcol = countforcol + 1;
								System.out.println("countforcol:" + countforcol);
								if (countforcol == countforwin) {
									ReadytoShowWin(rory);

								}

							} else {
								break;
							}
							// check for win col

						}
						int countforrow = 0;
						while (true) {

							if (tempy < y && board[i][tempy].getType() == rory) {
								tempy++;
								countforrow = countforrow + 1;
								System.out.println("countforrow:" + countforrow);
								if (countforrow == countforwin) {
									ReadytoShowWin(rory);

								}

							} else {
								break;
							}
							// check for win row

						}

						int countforright = 0;
						int tempyright = j;
						int tempxright = i;

						while (true) {

							if (tempxright < x && tempyright < y && board[tempxright][tempyright].getType() == rory) {
								tempyright++;
								tempxright++;
								countforright = countforright + 1;
								System.out.println("countforright:" + countforright);
								if (countforright == countforwin) {
									ReadytoShowWin(rory);
								}

							} else {
								break;
							}
							// check for win right

						}

						int counterforleft = 0;
						int tempyleft = j;
						int tempxleft = i;

						while (true) {

							if (tempxleft >= 0 && tempyleft < y && board[tempxleft][tempyleft].getType() == rory) {
								tempyleft++;
								tempxleft--;
								counterforleft = counterforleft + 1;
								System.out.println("counterforleft:" + counterforleft);
								if (counterforleft == countforwin) {
									ReadytoShowWin(rory);

								}
							} else {
								break;
							}
							// check for win left
						}

					}

				}

			}

		}

	}

	void ReadytoShowWin(int rory) {
		if (rory == 0) {
			resultwin = true;

			WhoWin.setText("Red Win");

		} else if (rory == 1) {
			resultwin = true;

			WhoWin.setText("Yellow Win");

		}

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