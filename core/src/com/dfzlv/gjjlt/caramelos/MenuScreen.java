package com.dfzlv.gjjlt.caramelos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Locale;

/**
 * Created by USUARIO on 12/09/2017.
 */
public class MenuScreen implements Screen{
    SpriteBatch batch;
    final CaramelosGame game;
    TextButton textButtonSuperBull;
    TextButton textButtonAbout;
    Image image;
    Stage stage;
    FitViewport viewport;

    public MenuScreen(final CaramelosGame game)
    {
        this.game = game;
        Gdx.input.setCatchBackKey(false);
        viewport = new FitViewport(Constants.DIFFICULTY_WORLD_SIZE_WIDTH, Constants.DIFFICULTY_WORLD_SIZE_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        Skin uiSkin = new Skin(Gdx.files.internal("UiSkin/uiskin.json"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Vollkorn/Vollkorn-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        FileHandle baseFileHandle = Gdx.files.internal("Messages/menus");
        String localeLanguage =java.util.Locale.getDefault().toString();
        Locale locale = new Locale(localeLanguage);
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale,"UTF-8");
        String namegame = myBundle.get("game");
        String menustart = myBundle.get("menustart");
        String menuabout = myBundle.get("menuabout");

        textButtonSuperBull = new TextButton(menustart,uiSkin);
        textButtonAbout =new TextButton(menuabout,uiSkin);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font12;
        textButtonStyle.up = uiSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = uiSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = uiSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = uiSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonSuperBull.setStyle(textButtonStyle);
        textButtonAbout.setStyle(textButtonStyle);

        Texture background  = new Texture(Gdx.files.internal("android_libgdx.png"));
        TextureRegion textureRegion = new TextureRegion(background);
        image = new Image(textureRegion);

        Table tableParent = new Table();
        tableParent.setFillParent(true);

        Table table = new Table();
        stage.addActor(tableParent);

        Label label = new Label(namegame,uiSkin);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font12;
        label.setStyle(labelStyle);

        table.add(label);
        table.row();
        table.add(textButtonSuperBull).width(400).height(300).pad(10);
        table.row();
        table.add(textButtonAbout).width(400).height(75).pad(10);

        tableParent.add(image).width(400).height(225).align(Align.center).pad(10);
        tableParent.add(table).width(400).pad(10);


        textButtonSuperBull.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.ShowSuperBullScreen(1);
            }
        });


        textButtonAbout.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.ShowAboutScreen();
            }
        });




    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)95/255,(float)10/255,(float)255/255,(float)1.0);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
        //stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
