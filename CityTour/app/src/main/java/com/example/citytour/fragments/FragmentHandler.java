package com.example.citytour.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.citytour.R;


public class FragmentHandler extends FragmentActivity {

	public static final int FRAGMENT_ONE = 0;
	public static final int FRAGMENT_TWO = 1;
	public static final int FRAGMENTS = 2;

	private FragmentPagerAdapter _fragmentPagerAdapter;
	private ViewPager _viewPager;
	private List<Fragment> _fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_handler);
		String url = "";
		String checkpoint = "";
		Intent intent = getIntent();
		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
			     url = extras.getString("url");
			     checkpoint = extras.getString("checkpoint");
			}
		}

		Bundle urlBundle = new Bundle();
		urlBundle.putString("url", url);
		InfoFragment infoFragment = new InfoFragment();
		infoFragment.setArguments(urlBundle);
		
		Bundle checkBundle = new Bundle();
		checkBundle.putString("checkpoint", checkpoint);
		QuizzFragment quizzFragment = new QuizzFragment();
		quizzFragment.setArguments(checkBundle);
		
		_fragments.add(FRAGMENT_ONE, quizzFragment);
		_fragments.add(FRAGMENT_TWO, infoFragment);

		_fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){

			@Override
			public int getCount() {
				return FRAGMENTS;
			}

			@Override
			public Fragment getItem(final int position) {
				return _fragments.get(position);
			}

			@Override
			public CharSequence getPageTitle(final int position) {
				switch (position) {
				case FRAGMENT_ONE:
					return "QUIZZ";
				case FRAGMENT_TWO:
					return "INFO";
				default:
					return null;
				}
			}
		};

		// Set up view pager
		_viewPager.setAdapter(_fragmentPagerAdapter);
		_viewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						getActionBar().setSelectedNavigationItem(position);
					}
				});

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// Unused
			}
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				_viewPager.setCurrentItem(tab.getPosition());
			}
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// Unused
			}
		};

		// Add tabs to the action bar
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				actionBar.addTab(
						actionBar.newTab()
						.setText("QUIZZ")
						.setTabListener(tabListener));
			} else {
				actionBar.addTab(
						actionBar.newTab()
						.setText("INFO")
						.setTabListener(tabListener));
			}
		}
	}
}
