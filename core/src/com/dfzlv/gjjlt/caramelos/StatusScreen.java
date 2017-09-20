package com.dfzlv.gjjlt.caramelos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Locale;

/**
 * Created by USUARIO on 12/09/2017.
 */
public class StatusScreen extends InputAdapter implements Screen {
    CaramelosGame game;
    SpriteBatch batch;
    FitViewport viewport;
    ShapeRenderer renderer;
    boolean victory;
    int level;
    Stage stage;
    TextButton button;
    BitmapFont font12;
    Image imageLogo;
    String menulevel;
    String menuvictory;
    String menuchampion;
    String menucontinue;
    String menugameover;
    int maxNumLevels = 10;


    public StatusScreen(CaramelosGame game, boolean victory,int level)
    {
        this.game = game;
        this.victory = victory;
        this.level = level;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Vollkorn/Vollkorn-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        FileHandle baseFileHandle = Gdx.files.internal("Messages/menus");
        String localeLanguage =java.util.Locale.getDefault().toString();
        Locale locale = new Locale(localeLanguage);
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale,"UTF-8");
        menulevel = myBundle.get("menulevel");
        menuvictory = myBundle.get("menuvictory");
        menuchampion = myBundle.get("menuchampion");
        menucontinue = myBundle.get("menucontinue");
        menugameover = myBundle.get("menugameover");
    }

    @Override
    public void show() {


        batch = new SpriteBatch();

        viewport = new FitViewport(Constants.DIFFICULTY_WORLD_SIZE_WIDTH, Constants.DIFFICULTY_WORLD_SIZE_HEIGHT);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);


        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Skin uiSkin = new Skin(Gdx.files.internal("UiSkin/uiskin.json"));
        button = new TextButton(menucontinue, uiSkin);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font12;
        textButtonStyle.up = uiSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = uiSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = uiSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = uiSkin.newDrawable("white", Color.DARK_GRAY);
        button.setStyle(textButtonStyle);

        imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Star.png")))));


        Table table = new Table();
        table.setFillParent(true);
        table.add(imageLogo);
        table.row();
        table.add(button).width(300).height(200).pad(10);
        table.row();

        stage.addActor(table);

        button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                if(!victory || (victory && level == maxNumLevels))
                    game.ShowMenuScreen();
                else
                    game.ShowSuperBullScreen(level +1);

            }
        });





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)95/255,(float)10/255,(float)255/255,(float)1.0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.end();
        if(victory){
            if(level == maxNumLevels)
            {
                imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Star.png")))));
                button.setText(menuvictory+" "+ menulevel + " "+level+ "\n"+menuchampion+"\n"+menucontinue);
            }
            else{
                imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Star.png")))));
                button.setText(menuvictory+" "+ menulevel + " "+level+ "\n"+menucontinue);
            }
        }
        else
        {
            imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Cross.png")))));
            button.setText(menugameover+" "+ menulevel + " "+level+ "\n"+menucontinue);}
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        //stage.getViewport().update(width, height, true);
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
        return true;
    }
}
