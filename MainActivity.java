package sk.escapehouse.bratislava_ver2;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import sk.escapehouse.bratislava_ver2.R;
import sk.escapehouse.bratislava_ver2.Tab1;
import sk.escapehouse.bratislava_ver2.Tab2;


public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    private CodeChecker expert = new CodeChecker();




    //Called when the button gets clicked
    public void onClickCheckInput(View view) {
        //Get a reference to the TextView
        TextView answerOnDisplay = (TextView) findViewById(R.id.answer);
        //Get a reference to the Spinner
        Spinner numbers1 = (Spinner) findViewById(R.id.numbers1);
        //Get the selected item in the Spinner
        String selectedNumber1 = String.valueOf(numbers1.getSelectedItem());

        //Get a reference to the Spinner
        Spinner numbers2 = (Spinner) findViewById(R.id.numbers2);
        //Get the selected item in the Spinner
        String selectedNumber2 = String.valueOf(numbers2.getSelectedItem());

        //Get a reference to the Spinner
        Spinner numbers3 = (Spinner) findViewById(R.id.numbers3);
        //Get the selected item in the Spinner
        String selectedNumber3 = String.valueOf(numbers3.getSelectedItem());

        //Display the selected item
        //Get the answer from the CodeChecker class
        List<String> answersList = expert.getAnswer(selectedNumber1, selectedNumber2, selectedNumber3);
        StringBuilder answersFormatted = new StringBuilder();
        for (String brand : answersList) {
            answersFormatted.append(brand).append('\n');
        }
        //Display the answer
        answerOnDisplay.setText(answersFormatted);

    }
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
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.tabItem1);
        textView = (TextView) findViewById(R.id.text_view);
        textView.setVisibility(View.GONE);
        editText.setText("");



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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //this writes the text from edit field to the file
    public void saveProgress(View view){
        String Message = editText.getText().toString();
        System.getProperty("line.separator");
        String file_name = "save_file";
        try (FileOutputStream fileOutputStream = openFileOutput(file_name, Context.MODE_APPEND)) {
            fileOutputStream.write(Message.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){e.printStackTrace();} catch (IOException e) {
            e.printStackTrace();
        }
        // This turns off the Button after use
        final Button choice1 = (Button) findViewById(R.id.save_progress);
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice1.setEnabled(false);
            }

        });
    }
    //this shows the content of the saved file on the screen
    public void showProgress(View view) {
        try {
            String Message;
            FileInputStream fileInputStream = openFileInput("save_file");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((Message=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(Message + "\n");

            }
            textView.setText(stringBuffer.toString());
            textView.setVisibility(View.VISIBLE);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
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
            switch (position){
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                //  case 2:
                //    Tab2 tab2 = new Tab2();
                //      return tab2;
                //case 3:
                //  Tab3 tab3 = new Tab3();
                //return tab3;
            }
            return null;
        }



        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
