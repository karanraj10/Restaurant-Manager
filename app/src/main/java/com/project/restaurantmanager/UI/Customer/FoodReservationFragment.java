package com.project.restaurantmanager.UI.Customer;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.project.restaurantmanager.R;

import java.util.ArrayList;
import java.util.List;

public class FoodReservationFragment extends Fragment {
    public static int mSelectedRid;
    public static int mReservationOpt;
    public static int mStime,mEtime;
    TextView foodTextView,reservationTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodreservation,container,false);

        final ViewPager mViewPager = view.findViewById(R.id.foodreservationViewPager);

        foodTextView = view.findViewById(R.id.foodreservationFoodLabel);
        reservationTextView = view.findViewById(R.id.foodreservationReservationLabel);

        reservationTextView.setVisibility(View.INVISIBLE);


        foodTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        reservationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });

        final ColorStateList colorStateList = reservationTextView.getTextColors();

        CustomAdapter customAdapter = new CustomAdapter(getChildFragmentManager());
        customAdapter.addFragment(new FoodItemListFragment(),"food");
        if(mReservationOpt!=0) {
            customAdapter.addFragment(new ReservationFragment(), "reservation");
            reservationTextView.setVisibility(View.VISIBLE);
        }


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                {
                    foodTextView.setTextColor(Color.BLACK);
                    foodTextView.setTextSize(22);
                    reservationTextView.setTextColor(colorStateList);
                    reservationTextView.setTextSize(20);
                }
                else
                {
                    reservationTextView.setTextColor(Color.BLACK);
                    foodTextView.setTextColor(colorStateList);
                    foodTextView.setTextSize(20);
                    reservationTextView.setTextSize(22);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(customAdapter);

        return view;
    }
    protected class CustomAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> mFragments = new ArrayList();
        private List<CharSequence> mFragmentsTitle = new ArrayList();

        public CustomAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        public void addFragment(Fragment fragment,String title)
        {
            mFragments.add(fragment);
            mFragmentsTitle.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentsTitle.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
