package com.policyboss.policybosspro.introslider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;


public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext;
    TextView btnSkip;
    ImageView dot1, dot2, dot3, dot4, dot5;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init_widgets();
        setListener();
    }

    private void setListener() {
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        btnNext.setOnClickListener(this);
        btnSkip.setOnClickListener(this);

    }

    private void init_widgets() {
        dot1 = (ImageView) findViewById(R.id.dot1);
        dot2 = (ImageView) findViewById(R.id.dot2);
        dot3 = (ImageView) findViewById(R.id.dot3);
        dot4 = (ImageView) findViewById(R.id.dot4);
        dot5 = (ImageView) findViewById(R.id.dot5);
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5};

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (TextView) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                current = current + 1;
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    startActivity(new Intent(this, EulaActivity.class));
                }
                break;
            case R.id.btn_skip:
                startActivity(new Intent(this, EulaActivity.class));
                break;
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);
            current = position;
            setSelectedDot(position + 1);
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText("GET STARTED");
                btnSkip.setVisibility(View.VISIBLE);
            } else {
                // still pages are left
                btnNext.setText("NEXT");
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void setSelectedDot(int current) {
        dot1.setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));
        dot2.setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));
        dot3.setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));
        dot4.setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));
        dot5.setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));
        switch (current) {
            case 1:
                dot1.setImageDrawable(getResources().getDrawable(R.drawable.indicator_active));
                break;
            case 2:
                dot2.setImageDrawable(getResources().getDrawable(R.drawable.indicator_active));
                break;
            case 3:
                dot3.setImageDrawable(getResources().getDrawable(R.drawable.indicator_active));
                break;
            case 4:
                dot4.setImageDrawable(getResources().getDrawable(R.drawable.indicator_active));
                break;
            case 5:
                dot5.setImageDrawable(getResources().getDrawable(R.drawable.indicator_active));
                break;
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
