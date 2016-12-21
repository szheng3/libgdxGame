package com.pennypop.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * This is where you screen code will go, any UI should be in here
 * 
 * @author Richard Taylor @editor Shuai Zheng
 */
public class MainScreen implements Screen {

	private final Stage stage;
	private final SpriteBatch spriteBatch;
	private String Mytext;
	private BitmapFont font;
	private TextBounds Bound;
	private Sound sound;
	private ImageButton apibutton;
	private ImageButton sfxbutton;

	private int flag = 0;
	private Table rootTable;
	private Table tableleft;
	private Table tableright;
	private Label label;
	private ImageButton gamebutton;
	private Label weather;
	private Label place;
	private Label sky;
	private Label degree;
	protected String placename;
	protected String description;
	protected int deg;
	protected int speed;
	private Game game;
	private int flaggam = 0;
	private Screen screen;
	int[][] a = new int[4][4];

	public MainScreen(Game game) {
		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
		font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
		rootTable = new Table();
		tableleft = new Table();
		tableright = new Table();

		this.game = game;

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		stage.dispose();
	}

	@Override
	public void render(float delta) {
		// apibutton.setStyle(gamebutton.getStyle());

		if (flag == 0) {
			weather.setText("");
			place.setText("");
			sky.setText("");
			degree.setText("");
		} else {
			weather.setText("Current Weather");
			place.setText(placename);
			sky.setText(description);
			degree.setFontScale((float) 0.5);
			degree.setText(Integer.toString(deg) + " degrees, " + Integer.toString(speed) + "mph wind");

		}
		stage.act(delta);
		stage.draw();

		if (flaggam == 1) {
			clearWhite();
			screen.render(delta);
		}
	}

	private void clearWhite() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		System.out.println("hi i am hide");
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Mytext = "PennyPop";
		label = new Label(Mytext, new Label.LabelStyle(font, Color.RED));
		// create label for penntPop
		weather = new Label("Current", new Label.LabelStyle(font, Color.valueOf("8B4513")));
		// create label for weather
		place = new Label("", new Label.LabelStyle(font, Color.BLUE));
		// create label for place
		sky = new Label("", new Label.LabelStyle(font, Color.RED));
		// create label for sky
		degree = new Label("", new Label.LabelStyle(font, Color.RED));
		// create label for degree

		sound = Gdx.audio.newSound(Gdx.files.internal("button_click.wav"));
		// create sound

		Gdx.input.setInputProcessor(stage);

		sfxbutton = CreateButton("sfxButton.png");
		// draw the sfxbutton

		sfxbutton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sound.play();

			}
		});
		// play music sfxbutton

		apibutton = CreateButton("apiButton.png");
		// draw the apiButton

		apibutton.addListener(new ChangeListener() {

			private int speed;

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				JSONObject result = Json(
						"http://api.openweathermap.org/data/2.5/weather?q=San%20Francisco,US&appid=2e32d2b4b825464ec8c677a49531e9ae");
				try {
					System.out.println(result.get("name"));
					placename = (String) result.get("name");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JSONArray t = result.getJSONArray("weather");
					for (int n = 0; n < t.length(); n++) {
						JSONObject object = t.getJSONObject(n);
						description = (String) object.get("description");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JSONObject t = result.getJSONObject("wind");
					deg = t.getInt("deg");
					speed = t.getInt("speed");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				flag = (flag + 1) % 2;

			}
		});
		// add apibutton listener

		gamebutton = CreateButton("gameButton.png");
		// draw the gamebutton

		gamebutton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.input.setInputProcessor(null);

				screen = new GameScreen(game);
				game.setScreen(screen);

				flaggam = (flaggam + 1) % 2;

			}
		});
		// gamebutton listener
		tableleft.add(label).colspan(3).center().pad(10);
		tableleft.row();
		tableleft.add(sfxbutton).pad(3);
		tableleft.add(apibutton).pad(3);
		tableleft.add(gamebutton).pad(3);
		// add item to the lefttable
		tableright.add(weather);
		tableright.row();
		tableright.add(place).pad(3);
		tableright.row();
		tableright.add(sky);
		tableright.row();
		tableright.add(degree).pad(1);
		tableright.row();
		// add label to righttable
		rootTable.add(tableleft).pad(20);
		rootTable.add(tableright).pad(20);
		// add table to roottable
		rootTable.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		stage.addActor(rootTable);

	}

	@Override
	public void pause() {
		// Irrelevant on desktop, ignore this
	}

	@Override
	public void resume() {
		// Irrelevant on desktop, ignore this
	}

	public JSONObject Json(String Url) {
		JSONObject json = null;
		try {
			json = new JSONObject(IOUtils.toString(new URL(Url), Charset.forName("UTF-8")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;

	}
	// parse http json

	private ImageButton CreateButton(String image) {
		// TODO Auto-generated method stub
		Texture myTexture3 = new Texture(Gdx.files.internal(image));
		TextureRegion myTextureRegion3 = new TextureRegion(myTexture3);
		TextureRegionDrawable myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);
		ImageButton tembutton = new ImageButton(myTexRegionDrawable3);
		return tembutton;

	}
	// create button

}
