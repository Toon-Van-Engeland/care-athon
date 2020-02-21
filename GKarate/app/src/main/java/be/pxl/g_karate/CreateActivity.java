package be.pxl.g_karate;

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
import java.util.List;

public class CreateActivity  extends AppCompatActivity {

    private ViewPager mViewPagerLeftFood;
    private ViewPager mViewerRightFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initPagerLeftFood();
        initPagerRightFood();
    }

    private void initPagerRightFood() {
        mViewerRightFood = findViewById(R.id.right_food_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ThreeRightFoot(), "Three_Right");
        adapter.addFragment(new OneRightFoot(), "One_Right");
        adapter.addFragment(new FiveRightFoot(), "Five_Right");
        adapter.addFragment(new GrayRightFragment(), "Gray_Right");
        mViewerRightFood.setAdapter(adapter);
    }

    private void initPagerLeftFood() {
        mViewPagerLeftFood = findViewById(R.id.left_food_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ForLeftFood(), "Four_Left");
        adapter.addFragment(new TwoLeftFood(), "Two_Left");
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