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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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

	public MainScreen() {
		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
		font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
		rootTable = new Table();
		tableleft = new Table();
		tableright = new Table();

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		stage.dispose();
	}

	@Override
	public void render(float delta) {

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
		Texture myTexture = new Texture(Gdx.files.internal("sfxButton.png"));
		TextureRegion myTextureRegion = new TextureRegion(myTexture);
		TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		sfxbutton = new ImageButton(myTexRegionDrawable);
		// draw the sfxbutton

		sfxbutton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sound.play();

			}
		});
		// play music sfxbutton

		Texture myTexture2 = new Texture(Gdx.files.internal("apiButton.png"));
		TextureRegion myTextureRegion2 = new TextureRegion(myTexture2);
		TextureRegionDrawable myTexRegionDrawable2 = new TextureRegionDrawable(myTextureRegion2);
		apibutton = new ImageButton(myTexRegionDrawable2);
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
				// try {
				// speed = result.getJSONObject("wind").getInt("speed");
				// System.out.println(speed);
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				// get json data

				flag = (flag + 1) % 2;
				System.out.println(flag);

			}
		});
		// add apibutton listener

		Texture myTexture3 = new Texture(Gdx.files.internal("gameButton.png"));
		TextureRegion myTextureRegion3 = new TextureRegion(myTexture3);
		TextureRegionDrawable myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);
		gamebutton = new ImageButton(myTexRegionDrawable3);
		// draw the gamebutton

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

}