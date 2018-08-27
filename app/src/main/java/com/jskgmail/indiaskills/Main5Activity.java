package com.jskgmail.indiaskills;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        MainActivity.online=isNetworkAvailable();

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main5, container, false);
            View rootView1 = inflater.inflate(R.layout.fragment_main55, container, false);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                //  MainActivity.online=true;
                ListView listView = (ListView) rootView1.findViewById(R.id.list);
                TextView no_net = rootView1.findViewById(R.id.noconnection);
                //  MainActivity.online=false;
                if (!MainActivity.online)
                    no_net.setVisibility(View.VISIBLE);


                ListViewAdapter adapter = new ListViewAdapter(getActivity(), MainActivity.arrayList_u_test_id, MainActivity.arrayList_test_id, MainActivity.arrayList_test_name);
                listView.setAdapter(adapter);


/*


                  RecyclerView recyclerView=rootView1.findViewById(R.id.list);;
                CustomAdaptertestlist adapter;


                //  MainActivity.online=true;
                adapter= new CustomAdaptertestlist(getActivity(),MainActivity.arrayList_u_test_id,MainActivity.arrayList_test_id,MainActivity.arrayList_test_name);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

*/


                return rootView1;

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                ListView listView = (ListView) rootView.findViewById(R.id.offlinetests);


                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                Type type1 = new TypeToken<ArrayList<ArrayList<String>>>() {
                }.getType();
                Type type2 = new TypeToken<ArrayList<ArrayList<ArrayList<String>>>>() {
                }.getType();
                Gson gson = new Gson();


                DatabaseHandleroff db = new DatabaseHandleroff(getContext());
                String test_det_str = null, test_q_str = null, test_ans_str = null, instr = null;

                ArrayList<String> testdet = new ArrayList<>();
                ArrayList<String> off_test_NAMES = new ArrayList<>();
                ArrayList<String> off_test_u_IDs = new ArrayList<>();

                List<TestDetailoff> contacts = db.getAllContacts();
                for (TestDetailoff cn : contacts) {

                    test_det_str = cn.getTestDetailss_array();
                    test_q_str = cn.getArrayList_3_all_questions();
                    test_ans_str = cn.getArrayList_3_all_options();
                    instr = cn.getInstructionList();


                    testdet = gson.fromJson(test_det_str, type);

                    off_test_NAMES.add(testdet.get(1));
                    off_test_u_IDs.add(testdet.get(0));


                }
                //              ArrayList<ArrayList<String>> testdet1 = gson.fromJson(test_q_str, type1);
//                ArrayList<ArrayList<ArrayList<String>>> testdet2 = gson.fromJson(test_ans_str, type1);

                //    Log.e("jjjjjjjjjj", String.valueOf(testdet));
                //   Log.e("jjjjjjjjjjaa", String.valueOf(testdet1));
                //      Log.e("jjjjjjjjjjbb", String.valueOf(testdet2));
                //       Log.e("jjjjjjjjjjccc", String.valueOf(instr));

                ListViewAdapterOffline adapter = new ListViewAdapterOffline(getActivity(), off_test_NAMES, off_test_u_IDs);
                listView.setAdapter(adapter);


                return rootView;

            } else

                return rootView;

        }
        }


        /**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
         * one of the sections/tabs/pages.
         */
        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 2;
            }
        }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
