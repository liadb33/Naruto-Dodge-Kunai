package com.example.assignment_two.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.assignment_two.Interfaces.LocationUpdateCallback;
import com.example.assignment_two.Interfaces.MoveCallback;
import com.example.assignment_two.Logic.GameManager;
import com.example.assignment_two.R;
import com.example.assignment_two.Utilities.LocationManager;
import com.example.assignment_two.Utilities.MoveDetector;
import com.example.assignment_two.Utilities.ScoreManager;
import com.example.assignment_two.Utilities.SignalManager;
import com.example.assignment_two.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int LIFE = 3;
    public static final String KEY_MODE = "KEY_MODE";
    private long DELAY = 550L;
    private ExtendedFloatingActionButton main_FAB_right;
    private ExtendedFloatingActionButton main_FAB_left;
    private AppCompatImageView[] main_IMG_hearts;
    private AppCompatImageView[][] grid_obstacles;
    private AppCompatImageView[] grid_dodge;
    private MaterialTextView main_LBL_score;

    private SoundPlayer soundPlayer;
    private GameManager gameManager;
    private LocationManager locationManager;
    private int mode;
    private MoveDetector moveDetector;
    private Gson gson;
    private double latitude;
    private double longitude;
    private Timer timer;
    private boolean timerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();
        gameManager = new GameManager(LIFE);
        timerOn = false;

        findViews();
        initViews();
        startTimer();
    }


    private void updateGameUi() {
        gameManager.updateGameLogic();

        main_LBL_score.setText(String.valueOf(gameManager.getScore()));

        for (int i = 0; i < gameManager.getObstacleBoard().length; i++) {
            for (int j = 0; j < gameManager.getObstacleBoard()[0].length; j++) {
                switch (gameManager.getObstacleBoard()[i][j]) {
                    case 0:
                        grid_obstacles[i][j].setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        grid_obstacles[i][j].setImageResource(R.drawable.kunai);
                        grid_obstacles[i][j].setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        grid_obstacles[i][j].setImageResource(R.drawable.naruto_coin);
                        grid_obstacles[i][j].setVisibility(View.VISIBLE);
                        break;
                }
            }

        }

        for (int col = 0; col < gameManager.getDodgeBoard().length; col++) {
            switch (gameManager.collisionDetection(col)) {
                case 1:
                    collisionDetected();
                    break;
                case 2:
                    collisionCoin();
                    break;
                default:
            }
        }
        gameManager.updateScoreRegular();
    }

    private void collisionCoin(){
        gameManager.coinHitUpdate();
        SignalManager.getInstance().toast("Arigato Gozaimasu😁😁\n +100 POINTS!");
        soundPlayer.playCoinSound(R.raw.naruto_arigato);
    }

    private void collisionDetected() {
        gameManager.collisionScore();
        soundPlayer.playCrashSound(R.raw.hit_sound);
        SignalManager.getInstance().toast("KUSO!😵‍💫😵‍💫 \n -50 POINTS!");
        SignalManager.getInstance().vibrate(800);

        gameManager.updateHits();
        main_IMG_hearts[gameManager.getHitNum() - 1].setVisibility(View.INVISIBLE);
        if (gameManager.isGameLost()) {
            gameEnded();
        }
    }

    private void gameEnded(){
        SignalManager.getInstance().toast("You've Lost!");
        String address = locationManager.getAddress(this, latitude, longitude);
        ScoreManager.getInstance().saveNewScore(gameManager.getScore(),latitude,longitude,address);
        changeActivityRecord();
    }

    private void changeActivityRecord() {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateTimerDelay() {
        if (timer != null) {
            timer.cancel();
            timerOn = false;
        }
        startTimer();
    }


    private void startTimer() {
        if (!timerOn) {
            timer = new Timer();
            timerOn = true;
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            Log.d("DELAY", "run: " + DELAY);
                            runOnUiThread(() -> updateGameUi());
                        }
                    }, 0L, DELAY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager = LocationManager.getInstance();
        locationManager.setLocationUpdateCallback(new LocationUpdateCallback() {
            @Override
            public void onLocationUpdated(double latitude, double longitude) {
                MainActivity.this.latitude = latitude;
                MainActivity.this.longitude = longitude;
            }
        });
        locationManager.getDeviceLocation();
        soundPlayer = new SoundPlayer(this);
        soundPlayer.playBackground(R.raw.background_fight); //plays background
        startTimer();
        if (mode == 1)
            moveDetector.startSensor();
    }


    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopSound();
        stopTimer();
        if (mode == 1)
            moveDetector.stopSensor();
        if (locationManager != null) {
            locationManager.stopLocationUpdates();
            locationManager = null;
        }

    }

    private void stopTimer() {
        if (timer != null) {
            timerOn = false;
            timer.cancel();
        }
    }


    public void changeMovement(int direction) {
        gameManager.dodgeMovement(direction);
        for (int i = 0; i < grid_dodge.length; i++) {
            if (gameManager.getDodgeBoard()[i] == 1)
                grid_dodge[i].setVisibility(View.VISIBLE);
            else
                grid_dodge[i].setVisibility(View.INVISIBLE);
        }
    }

    private void initViews() {
        Intent previousIntent = getIntent();
        mode = previousIntent.getIntExtra(KEY_MODE, 0);

        if (mode == 0) { //buttons mode
            main_FAB_right.setOnClickListener(v -> changeMovement(1));
            main_FAB_left.setOnClickListener(v -> changeMovement(-1));

        } else if (mode == 1) { // sensors mode
            main_FAB_right.setVisibility(View.INVISIBLE);
            main_FAB_left.setVisibility(View.INVISIBLE);

            moveDetector = new MoveDetector(this,
                    new MoveCallback() {
                        @Override
                        public void changeMovementX() {
                            changeMovement(moveDetector.getMoveX());
                        }

                        @Override
                        public void changeMovementY() {
                            DELAY = Math.max(200L, Math.min(2500L, DELAY + moveDetector.getMoveY()));
                            updateTimerDelay();
                        }
                    });
        }

        main_LBL_score.setText(String.valueOf(gameManager.getScore()));

        for (int i = 0; i < gameManager.getDodgeBoard().length; i++) {
            if (gameManager.getDodgeBoard()[i] == 1)
                grid_dodge[i].setVisibility(View.VISIBLE);
            else
                grid_dodge[i].setVisibility(View.INVISIBLE);
        }

        for (AppCompatImageView[] rows : grid_obstacles)
            for (AppCompatImageView element : rows)
                element.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("DiscouragedApi")
    private void findViews() {
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_FAB_right = findViewById(R.id.main_FAB_right);
        main_FAB_left = findViewById(R.id.main_FAB_left);

        main_IMG_hearts = new AppCompatImageView[LIFE];
        for (int i = 0; i < main_IMG_hearts.length; i++) {
            String heartID = "main_IMG_heart" + (i + 1);
            main_IMG_hearts[i] = findViewById(getResources().getIdentifier(heartID, "id", getPackageName()));
        }
        grid_obstacles = new AppCompatImageView[gameManager.getRows()][gameManager.getCols()];
        for (int row = 0; row < grid_obstacles.length; row++) {
            for (int col = 0; col < grid_obstacles[row].length; col++) {
                String obstacleID = "grid_fall" + (row + 1) + (col + 1);
                grid_obstacles[row][col] = findViewById(getResources().getIdentifier(obstacleID, "id", getPackageName()));
            }
        }

        grid_dodge = new AppCompatImageView[gameManager.getCols()];
        for (int i = 0; i < grid_dodge.length; i++) {
            String dodgeID = "grid_dodge" + (i + 1);
            grid_dodge[i] = findViewById(getResources().getIdentifier(dodgeID, "id", getPackageName()));
        }
    }
}