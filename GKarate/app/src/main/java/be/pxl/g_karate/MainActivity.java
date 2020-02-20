package be.pxl.g_karate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Map<Integer, Integer> circlesOnHumanBody;

    List<Integer> handMomventsLeft;
    List<Integer> handMomventsRight;

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

        handMomventsLeft = new ArrayList<>();
        handMomventsRight = new ArrayList<>();

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

        currentPlaceInList = 0;

        handleHandMovements();
    }

    public void handleHandMovements() {
        for (int i = 0; i < handMomventsLeft.size(); i++) {
            int currentPlaceLeft = handMomventsLeft.get(i);
            int currentPlaceRight = handMomventsRight.get(i);

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

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }


}
