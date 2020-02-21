package be.pxl.g_karate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import be.pxl.g_karate.api.ApiProxy;
import be.pxl.g_karate.api.ExerciseRepo;
import be.pxl.g_karate.api.models.Exercise;

public class MainActivity extends AppCompatActivity {

    Map<Integer, Integer> circlesOnHumanBody;
    Map<Integer, Integer> numbersMap;

    //List<Integer> handMomventsLeft;
    //List<Integer> handMomventsRight;

    ImageView previousGestureLeft;
    ImageView previousGestureRight;

    int currentPlaceInList;

    Exercise exercise1 = new Exercise();
    Exercise test;
    ExerciseRepo repo = new ExerciseRepo(new ApiProxy());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //handMomventsLeft = new ArrayList<>();
        //handMomventsRight = new ArrayList<>();

        exercise1.addHandMovementLeft(5);
        exercise1.addHandMovementLeft(6);
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
            uiThread.start();
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public void handleHandMovements() {
        int currentPlaceLeft = test.getHandMovementsLeft().get(currentPlaceInList);
        int currentPlaceRight = test.getHandMovementsRight().get(currentPlaceInList);

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

        if (currentPlaceLeft != -1) {
            ImageView currentGestureLeft = findViewById(circlesOnHumanBody.get(currentPlaceLeft));
            currentGestureLeft.setImageIcon(Icon.createWithResource(this, numbersMap.get(currentPlaceLeft)));
            currentGestureLeft.setBackgroundResource(R.drawable.circle_red);
            currentGestureLeft.setVisibility(View.VISIBLE);

            previousGestureLeft = currentGestureLeft;
        } else {
            previousGestureLeft = null;
        }

        if (currentPlaceRight != -1) {
            ImageView currentGestureRight = findViewById(circlesOnHumanBody.get(currentPlaceRight));
            currentGestureRight.setImageIcon(Icon.createWithResource(this, numbersMap.get(currentPlaceRight)));
            currentGestureRight.setBackgroundResource(R.drawable.circle_blue);
            currentGestureRight.setVisibility(View.VISIBLE);

            previousGestureRight = currentGestureRight;
        } else {
            previousGestureRight = null;
        }
    }


}
