package be.pxl.g_karate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Hashtable;
import java.util.Map;

import be.pxl.g_karate.api.ApiProxy;
import be.pxl.g_karate.api.ExerciseRepo;
import be.pxl.g_karate.api.models.Exercise;

public class MainActivity extends AppCompatActivity {

    Map<Integer, Integer> circlesOnHumanBody;
    Map<Integer, Integer> numbersMap;
    Map<Integer, Integer> iconMap;
    Map<Integer, Integer> colorMap;

    ImageView previousGestureLeft;
    ImageView previousGestureRight;

    ImageView leftFootView;
    ImageView leftFootNumberView;
    ImageView leftFootIconView;

    ImageView rightFootView;
    ImageView rightFootNumberView;
    ImageView rightFootIconView;

    int currentPlaceInList;

    Exercise exercise1 = new Exercise();
    Exercise test;

    int previousHandLeft;
    int previousHandRight;

    boolean iconOnFeetEnabled = true;
    boolean colorOnFeetEnabled = true;

    ExerciseRepo repo = new ExerciseRepo(new ApiProxy());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftFootView = findViewById(R.id.feet_left);
        leftFootIconView = findViewById(R.id.feet_left_symbol);
        leftFootNumberView = findViewById(R.id.feet_left_index);

        rightFootView = findViewById(R.id.feet_right);
        rightFootIconView = findViewById(R.id.feet_right_symbol);
        rightFootNumberView = findViewById(R.id.feet_right_index);

        iconOnFeetEnabled = getIntent().getBooleanExtra("iconEnabled", true);
        colorOnFeetEnabled = getIntent().getBooleanExtra("colorEnabled", true);

        circlesOnHumanBody = new Hashtable<>();
        circlesOnHumanBody.put(1, R.id.ear_left);
        circlesOnHumanBody.put(2, R.id.ear_right);
        circlesOnHumanBody.put(3, R.id.shoulder_left);
        circlesOnHumanBody.put(4, R.id.shoulder_right);
        circlesOnHumanBody.put(5, R.id.heup_left);
        circlesOnHumanBody.put(6, R.id.heup_right);
        circlesOnHumanBody.put(7, R.id.knee_left);
        circlesOnHumanBody.put(8, R.id.knee_right);

        numbersMap = new Hashtable<>();
        numbersMap.put(1, R.drawable.ic_number_1);
        numbersMap.put(2, R.drawable.ic_number_2);
        numbersMap.put(3, R.drawable.ic_number_3);
        numbersMap.put(4, R.drawable.ic_number_4);
        numbersMap.put(5, R.drawable.ic_number_5);
        numbersMap.put(6, R.drawable.ic_number_6);
        numbersMap.put(7, R.drawable.ic_number_7);
        numbersMap.put(8, R.drawable.ic_number_8);

        iconMap = new Hashtable<>();
        iconMap.put(1, R.drawable.ic_sun);
        iconMap.put(2, R.drawable.ic_raincloud);
        iconMap.put(3, R.drawable.ic_glasses);
        iconMap.put(4, R.drawable.ic_umbrella);
        iconMap.put(5, R.drawable.ic_flower);
        iconMap.put(6, R.drawable.ic_hat);

        colorMap = new Hashtable<>();
        colorMap.put(1, Color.rgb(46,180,242));
        colorMap.put(2, Color.rgb(224,54,62));
        colorMap.put(3, Color.rgb(255,255,255));
        colorMap.put(4, Color.rgb(69,189,60));
        colorMap.put(5, Color.rgb(250,194,27));
        colorMap.put(6, Color.rgb(251,255,0));

        exercise1.addHandMovementLeft(5);
        exercise1.addHandMovementLeft(5);
        exercise1.addHandMovementLeft(7);
        exercise1.addHandMovementLeft(3);
        exercise1.addHandMovementLeft(-1);

        exercise1.addHandMovementRight(6);
        exercise1.addHandMovementRight(7);
        exercise1.addHandMovementRight(5);
        exercise1.addHandMovementRight(4);
        exercise1.addHandMovementRight(8);

        repo.addExercise(exercise1);
/*
        handMomventsLeft.add(1);
        handMomventsLeft.add(2);
        handMomventsLeft.add(5);
        handMomventsLeft.add(6);
        handMomventsLeft.add(8);
        handMomventsLeft.add(3);
        handMomventsLeft.add(4);
        handMomventsLeft.add(7);

        handMomventsRight.add(6);
        handMomventsRight.add(7);
        handMomventsRight.add(6);
        handMomventsRight.add(-1);
        handMomventsRight.add(2);
        handMomventsRight.add(7);
        handMomventsRight.add(5);
        handMomventsRight.add(8);
*/
        currentPlaceInList = 0;

        try {
            Thread dataThread = new Thread(() -> {
                try {
                    repo.getExercises();
                } catch (Exception e) {
                    System.err.println(e);
                }
            });
            dataThread.start();
            dataThread.join();
            System.out.println("EXERCISE LIST MAIN = " + ExerciseRepo.getExercisesList().size());
            test = ExerciseRepo.getExercisesList().get(2);
            Thread uiThread = new Thread(() -> {
                try {
                    while (currentPlaceInList < test.getHandMovementsLeft().size()) {
                        runOnUiThread(this::handleHandMovements);
                        Thread.sleep(5_000);
                        currentPlaceInList++;
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            });
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        exercise1.addFootMovementsLeft(2);
        exercise1.addFootMovementsLeft(-1);
        exercise1.addFootMovementsLeft(4);
        exercise1.addFootMovementsLeft(2);
        exercise1.addFootMovementsLeft(6);
        exercise1.addFootMovementsLeft(2);
        exercise1.addFootMovementsLeft(2);

        exercise1.addFootMovementsRight(1);
        exercise1.addFootMovementsRight(3);
        exercise1.addFootMovementsRight(-1);
        exercise1.addFootMovementsRight(1);
        exercise1.addFootMovementsRight(5);
        exercise1.addFootMovementsRight(1);
        exercise1.addFootMovementsRight(3);

//        repo.addExercise(exercise1);

        currentPlaceInList = 0;

        new Thread(() -> {
            try {
                int maximumOfLists = Math.max(exercise1.getFootMovementsLeft().size(), exercise1.getHandMovementsLeft().size());
                while(currentPlaceInList < maximumOfLists) {
                    runOnUiThread(this::handleHandMovements);
                    runOnUiThread(this::handleFootMovements);
                    Thread.sleep(5_000);
                    currentPlaceInList++;
                }
            } catch (Exception e) {

            }
        });
    }

    public void handleHandMovements() {
        if (currentPlaceInList >= exercise1.getHandMovementsLeft().size()) {
            //WIPE previous instructions
            if (previousGestureLeft != null) {
                previousGestureLeft.setBackground(null);
                previousGestureLeft.setImageIcon(null);
                previousGestureLeft.setVisibility(View.INVISIBLE);
            }

            if (previousGestureRight != null) {
                previousGestureRight.setBackground(null);
                previousGestureRight.setImageIcon(null);
                previousGestureRight.setVisibility(View.INVISIBLE);
            }
            return;
        }

        int currentPlaceLeft = exercise1.getHandMovementsLeft().get(currentPlaceInList);
        int currentPlaceRight = exercise1.getHandMovementsRight().get(currentPlaceInList);

        //WIPE previous instructions
        if (previousGestureLeft != null) {
            previousGestureLeft.setBackground(null);
            previousGestureLeft.setImageIcon(null);
            previousGestureLeft.setVisibility(View.INVISIBLE);
        }

        if (previousGestureRight != null) {
            previousGestureRight.setBackground(null);
            previousGestureRight.setImageIcon(null);
            previousGestureRight.setVisibility(View.INVISIBLE);
        }

        //Set left icon and cirkel on if gesture is needed
        if (currentPlaceLeft != -1) {
            ImageView currentGestureLeft = findViewById(circlesOnHumanBody.get(currentPlaceLeft));
            currentGestureLeft.setImageIcon(Icon.createWithResource(this, numbersMap.get(currentPlaceLeft)));
            if (previousHandLeft == currentPlaceLeft) {
                currentGestureLeft.setBackgroundResource(R.drawable.circle_red);
            } else {
                currentGestureLeft.setBackgroundResource(R.drawable.circle_red_yellow_border);
            }
            currentGestureLeft.setVisibility(View.VISIBLE);
            previousGestureLeft = currentGestureLeft;
        } else {
            previousGestureLeft = null;
        }

        previousHandLeft = currentPlaceLeft;

        //Set right icon and cirkel on if gesture is needed
        if (currentPlaceRight != -1) {
            ImageView currentGestureRight = findViewById(circlesOnHumanBody.get(currentPlaceRight));
            currentGestureRight.setImageIcon(Icon.createWithResource(this, numbersMap.get(currentPlaceRight)));
            if (previousHandRight == currentPlaceRight) {
                currentGestureRight.setBackgroundResource(R.drawable.circle_blue);
            } else {
                currentGestureRight.setBackgroundResource(R.drawable.circle_blue_yellow_border);
            }
            currentGestureRight.setVisibility(View.VISIBLE);

            previousGestureRight = currentGestureRight;
        } else {
            previousGestureRight = null;
        }

        previousHandRight = currentPlaceRight;
    }

    public void handleFootMovements() {
        if (currentPlaceInList >= exercise1.getFootMovementsLeft().size()) {
            return;
        }

        int currentPlaceLeftFoot = exercise1.getFootMovementsLeft().get(currentPlaceInList);
        int currentPlaceRightFoot = exercise1.getFootMovementsRight().get(currentPlaceInList);

        //Set all icons of the left foot
        if (currentPlaceLeftFoot != -1) {
            Icon icon = Icon.createWithResource(this, R.drawable.ic_left_footprint).setTint(colorMap.get(currentPlaceLeftFoot));
            if (!colorOnFeetEnabled) {
                icon.setTint(Color.rgb(0,0,0));
            }
            leftFootView.setImageIcon(icon);
            leftFootNumberView.setImageIcon(Icon.createWithResource(this, numbersMap.get(currentPlaceLeftFoot)));
            if (iconOnFeetEnabled) {
                leftFootIconView.setImageIcon(Icon.createWithResource(this, iconMap.get(currentPlaceLeftFoot)));
            }
        } else {
            leftFootView.setImageIcon(null);
            leftFootNumberView.setImageIcon(null);
            leftFootIconView.setImageIcon(null);
        }

        //Set all icons of the right foot
        if (currentPlaceRightFoot != -1) {
            Icon icon = Icon.createWithResource(this, R.drawable.ic_right_footprint).setTint(colorMap.get(currentPlaceRightFoot));
            if(!colorOnFeetEnabled) {
                icon.setTint(Color.rgb(0,0,0));
            }
            rightFootView.setImageIcon(icon);
            rightFootNumberView.setImageIcon(Icon.createWithResource(this, numbersMap.get(currentPlaceRightFoot)));
            if(iconOnFeetEnabled) {
                rightFootIconView.setImageIcon(Icon.createWithResource(this, iconMap.get(currentPlaceRightFoot)));
            }
        } else {
            rightFootView.setImageIcon(null);
            rightFootNumberView.setImageIcon(null);
            rightFootIconView.setImageIcon(null);
        }
    }


}
