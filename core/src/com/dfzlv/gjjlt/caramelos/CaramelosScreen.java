package com.dfzlv.gjjlt.caramelos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by USUARIO on 19/09/2017.
 */
public class CaramelosScreen extends InputAdapter implements Screen {
    CaramelosGame game;
    int level;
    int score =0;
    int maxscore = 30;
    SpriteBatch batch;
    FitViewport viewport;
    ShapeRenderer renderer;
    Stage stage;
    Skin uiSkin;
    BitmapFont font50;
    BitmapFont font40;
    int size;
    ArrayList<Label> labelArrayList;
    String strscore;
    Label labelScore;

    public CaramelosScreen(CaramelosGame game,int nlevel)
    {
        this.game = game;
        this.level = nlevel;
        this.score =0;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        uiSkin = new Skin(Gdx.files.internal("UiSkin/uiskin.json"));

        viewport = new FitViewport(Constants.DIFFICULTY_WORLD_SIZE_WIDTH, Constants.DIFFICULTY_WORLD_SIZE_HEIGHT);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Vollkorn/Vollkorn-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 55;
        font50 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("Vollkorn/Vollkorn-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 30;
        font40 = generator2.generateFont(parameter2); // font size 12 pixels
        generator2.dispose(); // don't forget to dispose to avoid memory leaks!

        FileHandle baseFileHandle = Gdx.files.internal("Messages/menus");
        String localeLanguage =java.util.Locale.getDefault().toString();
        Locale locale = new Locale(localeLanguage);
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale,"UTF-8");

        stage = new Stage(viewport);

        Table rightTable = new Table();
        Random random = new Random();
        size = 7;
        labelArrayList = new ArrayList<Label>();
        for(int i =0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                int rand = random.nextInt(5);
                CalculateColorText calculateColorText = new CalculateColorText(rand).invoke();
                Color color = calculateColorText.getColor();
                String text = calculateColorText.getText();
                Label.LabelStyle labelStyle = new Label.LabelStyle();
                labelStyle.font = font50;
                labelStyle.background = uiSkin.newDrawable("white", color);
                final Label label = new Label(text, uiSkin);
                label.setStyle(labelStyle);
                label.setAlignment(Align.center);
                rightTable.add(label).width(65).height(65);

                labelArrayList.add(label);


            }
            rightTable.row();
        }

        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = font40;
        String strgame = myBundle.get("game");
        strscore = myBundle.get("menuscore");
        String strlevel = myBundle.get("menulevel");
        Label labelGame = new Label(strgame,uiSkin);
        Label labelLevel = new Label(strlevel+": "+level,uiSkin);
        labelScore = new Label(strscore+": "+score,uiSkin);
        labelGame.setStyle(labelStyle2);
        labelLevel.setStyle(labelStyle2);
        labelScore.setStyle(labelStyle2);

        Table leftTable = new Table();
        leftTable.add(labelGame).align(Align.left);
        leftTable.row();
        leftTable.add(labelLevel).align(Align.left);
        leftTable.row();
        leftTable.add(labelScore).align(Align.left);

        Table table = new Table();
        table.setFillParent(true);
        table.add(leftTable).width(300).align(Align.top);
        table.add(rightTable).width(500);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        DragAndDrop dragAndDrop = new DragAndDrop();
        for(int i =0; i<labelArrayList.size();i++) {
            final Label label = labelArrayList.get(i);
            dragAndDrop.addSource(new DragAndDrop.Source(label) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(label.getText());

                    Label.LabelStyle labelStyle3 = new Label.LabelStyle();
                    labelStyle3.font = font50;
                    //labelStyle3.background = label.getStyle().background;
                    Label label1 = new Label(label.getText(), uiSkin);
                    label1.setStyle(labelStyle3);
                    payload.setDragActor(label1);

                    /*Label validLabel = new Label("Some payload!", uiSkin);
                    validLabel.setColor(0, 1, 0, 1);
                    payload.setValidDragActor(validLabel);

                    Label invalidLabel = new Label("Some payload!", uiSkin);
                    invalidLabel.setColor(1, 0, 0, 1);
                    payload.setInvalidDragActor(invalidLabel);*/

                    return payload;
                }
            });
            dragAndDrop.addTarget(new DragAndDrop.Target(label) {
                public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    Label labelSource = (Label) source.getActor();
                    int indexSource = labelArrayList.indexOf(labelSource);
                    int indexTarget = labelArrayList.indexOf(label);

                    int rowSource = indexSource/size;
                    int colSource = indexSource - (indexSource/size)*size;
                    int rowTarget = indexTarget/size;
                    int colTarget = indexTarget - (indexTarget/size)*size;

                    if((Math.abs(rowSource-rowTarget)==1 && Math.abs(colSource-colTarget)==0)||
                            (Math.abs(rowSource-rowTarget)==0 && Math.abs(colSource-colTarget)==1)){
                        getActor().setColor(Color.LIGHT_GRAY);
                    }
                    return true;
                }

                public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                    getActor().setColor(Color.WHITE);
                }

                public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {

                    Label labelSource = (Label) source.getActor();
                    int indexSource = labelArrayList.indexOf(labelSource);
                    int indexTarget = labelArrayList.indexOf(label);

                    int rowSource = indexSource/size;
                    int colSource = indexSource - (indexSource/size)*size;
                    int rowTarget = indexTarget/size;
                    int colTarget = indexTarget - (indexTarget/size)*size;

                    if((Math.abs(rowSource-rowTarget)==1 && Math.abs(colSource-colTarget)==0)||
                    (Math.abs(rowSource-rowTarget)==0 && Math.abs(colSource-colTarget)==1)){

                        System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
                        String aux = labelSource.getText().toString();
                        Label.LabelStyle labelStyle = new Label.LabelStyle();
                        labelStyle.font = font50;
                        labelStyle.background = labelSource.getStyle().background;
                        labelSource.setText(label.getText());
                        labelSource.setStyle(label.getStyle());
                        label.setText(aux);
                        label.setStyle(labelStyle);

                        CheckRowsAndColumns();
                    }
                }
            });
        }
    }

    public void CheckRowsAndColumns()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0; j<size;j++)
            {
                int index1 = i*size +j;
                int index2 = i*size +j+1;
                int index3 = i*size +j+2;
                int index4 = i*size +j+3;
                if(j+3<size)
                {
                    String text = labelArrayList.get(index1).getText().toString();
                    if(text.equals(labelArrayList.get(index2).getText().toString())
                            && text.equals(labelArrayList.get(index3).getText().toString())
                            && text.equals(labelArrayList.get(index4).getText().toString()))
                    {
                        score+=4;
                        labelScore.setText(strscore+": "+score);
                        System.out.println("Score");
                        labelArrayList.get(index1).setVisible(false);
                        labelArrayList.get(index2).setVisible(false);
                        labelArrayList.get(index3).setVisible(false);
                        labelArrayList.get(index4).setVisible(false);
                    }
                }

                int ind1 = i*size +j;
                int ind2 = (i+1)*size +j;
                int ind3 = (i+2)*size +j;
                int ind4 = (i+3)*size +j;
                if(i+3<size)
                {
                    String text = labelArrayList.get(ind1).getText().toString();
                    if(text.equals(labelArrayList.get(ind2).getText().toString())
                            && text.equals(labelArrayList.get(ind3).getText().toString())
                            && text.equals(labelArrayList.get(ind4).getText().toString()))
                    {
                        score+=4;
                        labelScore.setText(strscore+": "+score);
                        System.out.println("Score");
                        labelArrayList.get(ind1).setVisible(false);
                        labelArrayList.get(ind2).setVisible(false);
                        labelArrayList.get(ind3).setVisible(false);
                        labelArrayList.get(ind4).setVisible(false);
                    }
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        if(score >= maxscore)
            game.ShowStatusScreen(true,level);
        Gdx.gl.glClearColor((float)95/255,(float)10/255,(float)255/255,(float)1.0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        UpdateInvisibleCells();
    }

    public void UpdateInvisibleCells()
    {
        for(int i =0; i< labelArrayList.size();i++){
            Label label = labelArrayList.get(i);
            if(!label.isVisible()){
                Random random = new Random();
                int rand = random.nextInt(5);
                CalculateColorText calculateColorText = new CalculateColorText(rand).invoke();
                Color color = calculateColorText.getColor();
                String text = calculateColorText.getText();
                Label.LabelStyle labelStyle = new Label.LabelStyle();
                labelStyle.font = font50;
                labelStyle.background = uiSkin.newDrawable("white", color);
                label.setText(text);
                label.setStyle(labelStyle);
                label.setVisible(true);
            }
        }
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
        return false;
    }

    private class CalculateColorText {
        private int rand;
        private Color color;
        private String text;

        public CalculateColorText(int rand) {
            this.rand = rand;
        }

        public Color getColor() {
            return color;
        }

        public String getText() {
            return text;
        }

        public CalculateColorText invoke() {
            color = Color.CHARTREUSE;
            text = "A";
            switch (rand) {
                case 0:
                    color = Color.CHARTREUSE;
                    text = "A";
                    break;
                case 1:
                    color = Color.CORAL;
                    text = "E";
                    break;
                case 2:
                    color = Color.ORANGE;
                    text = "I";
                    break;
                case 3:
                    color = Color.SKY;
                    text = "O";
                    break;
                case 4:
                    color = Color.LIME;
                    text = "U";
                    break;
            }
            return this;
        }
    }
}
