package com.dfzlv.gjjlt.caramelos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Locale;

/**
 * Created by USUARIO on 12/09/2017.
 */
public class AboutScreen extends InputAdapter implements Screen {
    CaramelosGame game;
    SpriteBatch batch;
    FitViewport viewport;
    ShapeRenderer renderer;
    Stage stage;

    public AboutScreen(CaramelosGame game)
    {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        Skin uiSkin = new Skin(Gdx.files.internal("UiSkin/uiskin.json"));

        viewport = new FitViewport(Constants.DIFFICULTY_WORLD_SIZE_WIDTH, Constants.DIFFICULTY_WORLD_SIZE_HEIGHT);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        Gdx.input.setInputProcessor(this);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Vollkorn/Vollkorn-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        FileHandle baseFileHandle = Gdx.files.internal("Messages/menus");
        String localeLanguage =java.util.Locale.getDefault().toString();
        Locale locale = new Locale(localeLanguage);
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale,"UTF-8");
        String menuabout = myBundle.get("menuabout");
        String menudevelopers = myBundle.get("menudevelopers");

        stage = new Stage(viewport);
        Label label = new Label(menuabout,uiSkin);
        Label label2 = new Label("\n"+menudevelopers+":\nluarca84" ,uiSkin);
        label2.setAlignment(Align.center);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font12;
        label2.setStyle(labelStyle);
        label.setStyle(labelStyle);

        Texture texture = new Texture("android_libgdx.png");
        TextureRegion textureRegion = new TextureRegion(texture);
        Image image = new Image(textureRegion);

        Table tableParent = new Table();
        tableParent.setFillParent(true);
        Table table = new Table();
        table.add(label);
        table.row();
        table.add(label2);
        table.row();

        tableParent.add(table);
        tableParent.row();
        tableParent.add(image).width(450);
        stage.addActor(tableParent);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)95/255,(float)10/255,(float)255/255,(float)1.0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.ShowMenuScreen();
        return false;
    }
}