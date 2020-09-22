package danieldiv.pseudogames.hulajwro.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.text.DecimalFormat;

import danieldiv.pseudogames.hulajwro.Screens.FahrenScreen;
import danieldiv.pseudogames.hulajwro.SpielFahre;

//New camera and new viewport to keep Hud locked at given position on the screen
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private float runTimer;
    private float timeCount;
    private float recordTime = 999;
    private boolean timeUp; // true when the world timer reaches 0
    String StringOfTimeNow = "GO!";

    Label nowtimeLabel;
    Label recordtimeLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label recordLabel;
    BitmapFont bitmapfont;

    public Hud(SpriteBatch sb, FahrenScreen screen, SpielFahre spiel) {
        // recordTime = 1000;
        runTimer = 0;
        timeCount = 0;

        //fits to height, adds black bars to sides
        viewport = new FitViewport(SpielFahre.VIRTUAL_WIDTH, SpielFahre.VIRTUAL_HEIGHT, new OrthographicCamera());
        //stage is like a empty box waiting for Table to lay out Labels
        stage = new Stage(viewport, sb);

        // Gdx.input.setInputProcessor(stage);

        Table table = new Table();

        //top position of stage
        table.top();
        //to make it the size of the stage
        table.setFillParent(true);

        bitmapfont = new BitmapFont();
        bitmapfont.getData().setScale(2);
        //%03d - 3 digits long
        //%06d - 6 digits long
        // countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        nowtimeLabel = new Label(StringOfTimeNow, new Label.LabelStyle(bitmapfont, Color.WHITE));
        Gdx.app.log("APPlog", "RecordTimeLabel " + this.recordTime);
        if (recordTime == 999)
            recordtimeLabel = new Label("", new Label.LabelStyle(bitmapfont, Color.WHITE));
        else
            recordtimeLabel = new Label(String.format("%.2f", this.recordTime), new Label.LabelStyle(bitmapfont, Color.WHITE));
        timeLabel = new Label("TIME:", new Label.LabelStyle(bitmapfont, Color.WHITE));
        levelLabel = new Label("level1", new Label.LabelStyle(bitmapfont, Color.WHITE));
        worldLabel = new Label("World 1", new Label.LabelStyle(bitmapfont, Color.WHITE));
        if (recordTime == 999)
            recordLabel = new Label("", new Label.LabelStyle(bitmapfont, Color.WHITE));
        else recordLabel = new Label("YOUR BEST:", new Label.LabelStyle(bitmapfont, Color.WHITE));


        //expand for entire top row - for multimpe inside one row distribute them equally
        table.add(timeLabel).expandX().padTop(1);
        table.add(worldLabel).expandX().padTop(1);
        //if (recordTime != 999)
        table.add(recordLabel).expandX().padTop(1);
        //go to next row
        table.row();
        table.add(nowtimeLabel).expandX();
        table.add(levelLabel).expandX();
        //only display record  and its labelafter first run
        // if (recordTime != 999)
        table.add(recordtimeLabel).expandX();

        if (recordTime == 999) {
            recordLabel.clear();
            recordtimeLabel.clear();
        }

        stage.addActor(table);


    }

    public void update(float dt) {
        timeCount += dt;
        //Gdx.app.log("GdxTag", "time count " + timeCount + "worldTimer " + worldTimer);
        if (timeCount >= 0.1) {
            if (runTimer >= 0) {
                //worldTimer++;
                runTimer = runTimer + timeCount;
            }


            //new DecimalFormat("#:##").format(runTimer); //"1.2"

            //%01$tM %01$tS,%01$tL
            StringOfTimeNow = (String.format("%.2f", (runTimer)));
            String StringOfTimeNowFormatted = StringOfTimeNow.replace(",", ":");
            nowtimeLabel.setText(StringOfTimeNowFormatted);
            //nowtimeLabel.setText(  new DecimalFormat("#.##").format(runTimer));
            timeCount = 0;
        }
    }


    // public boolean isTimeUp() { return timeUp; }

    public void draw() {
        stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
    }


    public float getRunTimer() {
        return runTimer;
    }

    public void setRecordTime(float recordTime) {
        this.recordTime = recordTime;
        Gdx.app.log("APPlog", "setRecordTime() " + this.recordTime);
        //recordtimeLabel.setText((int) this.recordTime);
        if (recordTime != 0) {
            recordtimeLabel.setText(String.format("%.2f", (this.recordTime)).replace(",", ":"));
            recordLabel.setText( "YOUR BEST:");
        }
    }

    public float getRecordTime() {
        return recordTime;
    }
}
