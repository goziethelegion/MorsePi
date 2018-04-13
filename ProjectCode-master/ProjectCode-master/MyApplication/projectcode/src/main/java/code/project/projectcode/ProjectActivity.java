package code.project.projectcode;
/*
Createed By Andrew McGuire
TEAM PANADA
PROJECT CODE
 */
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static code.project.projectcode.FragmentHandler.ARG_PAGE;

public class ProjectActivity extends AppCompatActivity {

    private static Context context;
    FragmentPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        ProjectActivity.context = getApplicationContext();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                ProjectActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    // Populating Menu
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.About:
                Toast.makeText(ProjectActivity.getAppContext(), "Created by Team Panda 2017-2018", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tutorial:
                Intent tutorialintent = new Intent(ProjectActivity.this,Tutorial.class);
                startActivity(tutorialintent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Returning the Context so that it is accessed by other classes
    public static Context getAppContext()
    {
        return ProjectActivity.context;
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
        @Override
        public CharSequence getPageTitle(int position) {
           if(position == 0)
               return "Morse";
           else if(position == 2)
               return "Settings";
            else return "Text";
        }
        @Override
        public Fragment getItem(int position) {

            return FragmentHandler.create(position +1 );
        }



    }
}
