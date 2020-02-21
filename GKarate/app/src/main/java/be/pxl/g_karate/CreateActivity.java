package be.pxl.g_karate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import be.pxl.g_karate.api.models.Exercise;

public class CreateActivity  extends AppCompatActivity {

    Exercise exercise;

    private ViewPager mViewPagerLeftFood;
    private ViewPager mViewerRightFood;

    public int currentLeftSelection;
    public int currentRightSelection;

    Map<Integer, Integer> circlesOnHumanBody;
    Map<Integer, Integer> numbersMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initPagerLeftFood();
        initPagerRightFood();

        exercise = new Exercise();

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

        for (int i = 1; i <= 8; i++) {
            ((ImageView) findViewById(circlesOnHumanBody.get(i))).setImageIcon(Icon.createWithResource(this, numbersMap.get(i)));
        }
    }

    public void keyPointClickHandler(View view) {
        int selection = Integer.parseInt(view.getContentDescription().toString());
        System.out.println(selection);
        if (currentLeftSelection == selection) {
            System.out.println("leftSelection");
            circlesOnHumanBody.values().forEach((id) -> {
                if (id != currentLeftSelection) {
                    findViewById(id).setBackgroundResource(R.drawable.circle_blue);
                }
            });
            findViewById(circlesOnHumanBody.get(selection)).setBackgroundResource(R.drawable.circle_red_yellow_border);
            currentLeftSelection = 0;
            currentRightSelection = selection;
            return;
        }
        if (currentRightSelection == selection) {
            System.out.println("rightSelection");
            findViewById(circlesOnHumanBody.get(selection)).setBackgroundResource(R.drawable.circle_blue);
            currentRightSelection = 0;
            return;
        }
        view.setBackgroundResource(R.drawable.circle_blue_yellow_border);
        if (currentLeftSelection != 0) {
            findViewById(circlesOnHumanBody.get(currentLeftSelection)).setBackgroundResource(R.drawable.circle_blue);
        }
        currentLeftSelection = selection;
    }

    public void clear(View view) {
        circlesOnHumanBody.values().forEach((id) -> {
            findViewById(id).setBackgroundResource(R.drawable.circle_blue);
        });
        currentLeftSelection = 0;
        currentRightSelection = 0;
        mViewPagerLeftFood.setCurrentItem(3);
        mViewerRightFood.setCurrentItem(3);
    }

    public void next(View view) {
        int footnumberLeft = (mViewPagerLeftFood.getCurrentItem() + 1) * 2;
        int footnumberRight = ((mViewerRightFood.getCurrentItem() + 1) * 2) - 1;
        if (footnumberLeft > 6) {
            footnumberLeft = -1;
        }
        if (footnumberRight > 5) {
            footnumberRight = -1;
        }
        exercise.addFootMovementsRight(footnumberRight);
        exercise.addFootMovementsLeft(footnumberLeft);
        if (currentLeftSelection != 0) {
            exercise.addHandMovementRight(currentLeftSelection);
        } else {
            exercise.addHandMovementRight(-1);
        }
        if (currentRightSelection != 0) {
            exercise.addHandMovementLeft(currentRightSelection);
        } else {
            exercise.addHandMovementLeft(-1);
        }

        clear(view);
    }

    public void preview(View view) {
        int[] leftHand = new int[exercise.getHandMovementsLeft().size()];
        for (int i = 0; i < exercise.getHandMovementsLeft().size(); i++) {
            leftHand[i] = exercise.getHandMovementsLeft().get(i);
        }

        int[] leftFoot = new int[exercise.getFootMovementsLeft().size()];
        for (int i = 0; i < exercise.getFootMovementsLeft().size(); i++) {
            leftFoot[i] = exercise.getFootMovementsLeft().get(i);
        }

        int[] rightHand = new int[exercise.getHandMovementsRight().size()];
        for (int i = 0; i < exercise.getHandMovementsRight().size(); i++) {
            rightHand[i] = exercise.getHandMovementsRight().get(i);
        }

        int[] rightFoot = new int[exercise.getFootMovementsRight().size()];
        for (int i = 0; i < exercise.getFootMovementsRight().size(); i++) {
            rightFoot[i] = exercise.getFootMovementsRight().get(i);
        }

        Intent intent = new Intent(CreateActivity.this, MainActivity.class);
        intent.putExtra("exerciseLeftHand", leftHand);
        intent.putExtra("exerciseLeftFoot", leftFoot);
        intent.putExtra("exerciseRightHand", rightHand);
        intent.putExtra("exerciseRightFoot", rightFoot);
        startActivity(intent);
    }

    public void save(View view) {

    }

    private void initPagerRightFood() {
        mViewerRightFood = findViewById(R.id.right_food_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneRightFoot(), "One_Right");
        adapter.addFragment(new ThreeRightFoot(), "Three_Right");
        adapter.addFragment(new FiveRightFoot(), "Five_Right");
        adapter.addFragment(new GrayRightFragment(), "Gray_Right");
        mViewerRightFood.setAdapter(adapter);
    }

    private void initPagerLeftFood() {
        mViewPagerLeftFood = findViewById(R.id.left_food_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TwoLeftFood(), "One_Right");
        adapter.addFragment(new ForLeftFood(), "Four_Left");
        adapter.addFragment(new SixLeftFood(), "Six_Left");
        adapter.addFragment(new ForFragment(), "gray_left");

        mViewPagerLeftFood.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mList = new ArrayList<>();
        private final List<String> mTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            return mList.get(i);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mList.add(fragment);
            mTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }

    public static class ForLeftFood extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_left_footprint).setTint(Color.GREEN);
            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);


            Icon iconTwo = Icon.createWithResource(getContext(), R.drawable.ic_number_4);
            ((ImageView) v.findViewById(R.id.feet_left_index2)).setImageIcon(iconTwo);




            return v;
        }
    }

    public static class TwoLeftFood extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_left_footprint).setTint(Color.RED);

            Icon iconTwo = Icon.createWithResource(getContext(), R.drawable.ic_number_2);
            ((ImageView) v.findViewById(R.id.feet_left_index2)).setImageIcon(iconTwo);

            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);
            return v;
        }
    }

    public static class SixLeftFood extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_left_footprint).setTint(Color.YELLOW);

            Icon iconTwo = Icon.createWithResource(getContext(), R.drawable.ic_number_6);
            ((ImageView) v.findViewById(R.id.feet_left_index2)).setImageIcon(iconTwo);

            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);
            return v;
        }
    }

    public static class ForFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_left_footprint).setTint(Color.GRAY);
            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);
            return v;
        }
    }

    public static class ThreeRightFoot extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_right_footprint).setTint(Color.WHITE);

            Icon iconTwo = Icon.createWithResource(getContext(), R.drawable.ic_number_3);
            ((ImageView) v.findViewById(R.id.feet_left_index2)).setImageIcon(iconTwo);

            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);
            return v;
        }
    }

    public static class OneRightFoot extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_right_footprint).setTint(Color.BLUE);

            Icon iconTwo = Icon.createWithResource(getContext(), R.drawable.ic_number_1);
            ((ImageView) v.findViewById(R.id.feet_left_index2)).setImageIcon(iconTwo);

            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);
            return v;
        }
    }

    public static class FiveRightFoot extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_right_footprint).setTint(Color.MAGENTA);

            Icon iconTwo = Icon.createWithResource(getContext(), R.drawable.ic_number_5);
            ((ImageView) v.findViewById(R.id.feet_left_index2)).setImageIcon(iconTwo);

            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);
            return v;
        }
    }
    public static class GrayRightFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.left_food_box, container, false);
            Icon icon = Icon.createWithResource(getContext(), R.drawable.ic_right_footprint).setTint(Color.GRAY);
            ((ImageView) v.findViewById(R.id.feet_left2)).setImageIcon(icon);
            return v;
        }
    }
}