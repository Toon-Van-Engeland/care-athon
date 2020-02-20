package be.pxl.g_karate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Map<Integer, Integer> circlesOnHumanBody;

    List<Integer> handMovementsLeft;
    List<Integer> handMovementsRight;

    ImageView previousGestureLeft;
    ImageView previousGestureRight;

    int currentPlaceInList;

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

        handMovementsLeft = new ArrayList<>();
        handMovementsRight = new ArrayList<>();

        handMovementsLeft.add(1);
        handMovementsLeft.add(2);
        handMovementsLeft.add(5);
        handMovementsLeft.add(6);
        handMovementsLeft.add(8);
        handMovementsLeft.add(3);
        handMovementsLeft.add(4);
        handMovementsLeft.add(7);

        handMovementsRight.add(6);
        handMovementsRight.add(7);
        handMovementsRight.add(6);
        handMovementsRight.add(-1);
        handMovementsRight.add(2);
        handMovementsRight.add(7);
        handMovementsRight.add(5);
        handMovementsRight.add(8);

        currentPlaceInList = 0;

        new Thread(() -> {
            try {
                while(currentPlaceInList < handMovementsLeft.size()) {
                    runOnUiThread(this::handleHandMovements);
                    Thread.sleep(5_000);
                    currentPlaceInList++;
                }
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public void handleHandMovements() {
        int currentPlaceLeft = handMovementsLeft.get(currentPlaceInList);
        int currentPlaceRight = handMovementsRight.get(currentPlaceInList);

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
            currentGestureLeft.setImageIcon(Icon.createWithResource(this, R.drawable.ic_raincloud));
            currentGestureLeft.setBackgroundResource(R.drawable.circle_red);
            currentGestureLeft.setVisibility(View.VISIBLE);

            previousGestureLeft = currentGestureLeft;
        } else {
            previousGestureLeft = null;
        }

        if (currentPlaceRight != -1) {
            ImageView currentGestureRight = findViewById(circlesOnHumanBody.get(currentPlaceRight));
            currentGestureRight.setImageIcon(Icon.createWithResource(this, R.drawable.ic_sun));
            currentGestureRight.setBackgroundResource(R.drawable.circle_blue);
            currentGestureRight.setVisibility(View.VISIBLE);

            previousGestureRight = currentGestureRight;
        } else {
            previousGestureRight = null;
        }
    }


}
