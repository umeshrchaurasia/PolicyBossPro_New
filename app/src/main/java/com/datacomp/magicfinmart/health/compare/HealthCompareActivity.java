package com.datacomp.magicfinmart.health.compare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.datacomp.magicfinmart.BaseActivity;
import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.health.fragment.HealthQuoteFragment;
import com.datacomp.magicfinmart.home.HomeActivity;
import com.datacomp.magicfinmart.utility.SortbyInsurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.BenefitsEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.HealthQuoteEntity;

public class HealthCompareActivity extends BaseActivity {

    RecyclerView rvBenefits, rvBenefitsOptions;
    List<HealthQuoteEntity> listHealthQuote;
    HealthCompareViewAdapter mAdapter;
    Spinner spBenefits;
    Button btnBack;
    ArrayList<String> listBenefits;
    ArrayList<BenefitsEntity> list9Benefits;
    ArrayAdapter<String> benefitsAdapter;
    HealthNineBenefitsViewAdapter mBenefitsAdapter;
    TextView txtSelectedBenefits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_compare);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);
        this.setTitle("COMPARE");

        init();
        listBenefits = new ArrayList<>();
        list9Benefits = new ArrayList<BenefitsEntity>();
        //listHealthQuote = new ArrayList<>();
        if (getIntent().getParcelableArrayListExtra(HealthQuoteFragment.HEALTH_COMPARE) != null) {
            List<HealthQuoteEntity> list = getIntent().getParcelableArrayListExtra(HealthQuoteFragment.HEALTH_COMPARE);

            listHealthQuote = new ArrayList<>(removeDuplicate(list));

            fillBenefits();
            bindBenefits();

        }

        // spBenefits.setSelection(0);
        updateBenefits("Room Rent Limit");
        mBenefitsAdapter.updateList(0);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<HealthQuoteEntity> removeDuplicate(List<HealthQuoteEntity> list) {
        List<HealthQuoteEntity> quoteList = new ArrayList<>();

        boolean isAdd = true;

        for (int i = 0; i < list.size(); i++) {
            HealthQuoteEntity entity = list.get(i);
            for (int j = 0; j < quoteList.size(); j++) {
                HealthQuoteEntity en = quoteList.get(j);
                if (en.getInsurerId() == entity.getInsurerId()) {
                    isAdd = false;
                } else {
                    isAdd = true;
                }
            }
            if (isAdd) {
                quoteList.add(entity);
            }
        }


        return quoteList;
    }


    private void bindBenefits() {

        Collections.sort(listHealthQuote, new SortbyInsurer());
        mAdapter = new HealthCompareViewAdapter(this, listHealthQuote);
        rvBenefits.setAdapter(mAdapter);


        benefitsAdapter = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, listBenefits) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
        spBenefits.setAdapter(benefitsAdapter);

        spBenefits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    updateBenefits(spBenefits.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void updateBenefits(String benefits) {
        txtSelectedBenefits.setText(benefits);
        resetBenefits();
        for (int i = 0; i < listHealthQuote.size(); i++) {
            HealthQuoteEntity entity = listHealthQuote.get(i);

            for (int j = 0; j < entity.getLstbenfitsFive().size(); j++) {
                BenefitsEntity benefitsEntity = entity.getLstbenfitsFive().get(j);
                if (benefitsEntity.getBeneDesc().equals(benefits)) {
                    benefitsEntity.setSelected(true);
                    break;
                }
            }
        }

        mAdapter.refreshSelection(listHealthQuote);

    }

    private void resetBenefits() {
        for (int i = 0; i < listHealthQuote.size(); i++) {
            HealthQuoteEntity entity = listHealthQuote.get(i);

            for (int j = 0; j < entity.getLstbenfitsFive().size(); j++) {
                BenefitsEntity benefitsEntity = entity.getLstbenfitsFive().get(j);
                benefitsEntity.setSelected(false);
            }
        }
    }

    private void init() {
        btnBack = (Button) findViewById(R.id.btnBack);
        txtSelectedBenefits = (TextView) findViewById(R.id.txtSelectedBenefits);
        spBenefits = (Spinner) findViewById(R.id.spBenefits);
        rvBenefits = (RecyclerView) findViewById(R.id.rvBenefits);
        rvBenefits.setLayoutManager(new LinearLayoutManager(this));

        rvBenefitsOptions = (RecyclerView) findViewById(R.id.rvBenefitsOptions);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(HealthCompareActivity.this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(HealthCompareActivity.this) {
                    private static final float SPEED = 4000f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvBenefitsOptions.setLayoutManager(layoutManager);
        autoScroll();
    }

    private void fillBenefits() {


        if (listHealthQuote != null) {
            if (listHealthQuote.get(0) != null) {
                for (int i = 0; i < listHealthQuote.get(0).getLstbenfitsFive().size(); i++) {
                    BenefitsEntity entity = listHealthQuote.get(0).getLstbenfitsFive().get(i);
                    if (entity.getBeneID() == 1
                            || entity.getBeneID() == 2
                            || entity.getBeneID() == 3
                            || entity.getBeneID() == 4
                            || entity.getBeneID() == 6
                            || entity.getBeneID() == 8
                            || entity.getBeneID() == 13
                            || entity.getBeneID() == 14
                            || entity.getBeneID() == 22) {
                        if (entity.getBeneID() == 1) {
                            entity.setSelected(true);
                        }
                        list9Benefits.add(entity);

                    } else {
                        listBenefits.add(entity.getBeneDesc());
                    }
                }

                listBenefits.add(0, "Select Other Benefits");

                mBenefitsAdapter = new HealthNineBenefitsViewAdapter(this, list9Benefits);
                rvBenefitsOptions.setAdapter(mBenefitsAdapter);
            }
        }
    }

    public void autoScroll() {
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == mBenefitsAdapter.getItemCount())
                    count = 0;
                if (count < mBenefitsAdapter.getItemCount()) {
                    rvBenefitsOptions.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_home:

                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
