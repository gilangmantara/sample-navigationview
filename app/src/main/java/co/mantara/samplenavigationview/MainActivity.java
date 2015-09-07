package co.mantara.samplenavigationview;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Gilang on 07/09/2015.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private LinearLayout mBaseDrawer;

    private ActionBarDrawerToggle mDrawerToggle;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tes

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mBaseDrawer = (LinearLayout) findViewById(R.id.base_drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationItemSelectedListener());
        mDrawerLayout.setDrawerListener(new DemoDrawerListener());

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,
                toolbar,R.string.drawer_open, R.string.drawer_close);

        // DEFAULT FRAGMENT
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            selectedFragment = new InboxFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, selectedFragment)
                    .commit();

            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * The action bar home/up action should open or close the drawer.
         * mDrawerToggle will take care of this.
         */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (mDrawerLayout.isDrawerOpen(mBaseDrawer)) {
            mDrawerLayout.closeDrawer(mBaseDrawer);
        }
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

    private class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //Checking if the item is in checked state or not, if not make it in checked state
            if(menuItem.isChecked()) menuItem.setChecked(false);
            else menuItem.setChecked(true);

            //Closing drawer on item click
            mDrawerLayout.closeDrawers();

            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()){
                //Replacing the main content with ContentFragment Which is our Inbox View;
                case R.id.inbox:
                    selectedFragment = new InboxFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    // Replace whatever is in the fragment_container view with this
                    // fragment,
                    // and add the transaction to the back stack so the user can navigate
                    // back
                    ft.replace(R.id.fragment_container, selectedFragment);

                    // Commit the transaction
                    ft.commit();
                    return true;
                case R.id.sent:
                    navigationView.getMenu().getItem(1).setChecked(true);
                    Toast.makeText(getApplicationContext(),"SENT",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.spam:
                    navigationView.getMenu().getItem(2).setChecked(true);
                    Toast.makeText(getApplicationContext(),"SPAM",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.logout:
                    finish();
                    return true;
                default:
                    Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                    return true;
            }
        }
    }
    //endregion CLASS NAVIGATION_LISTENER #

    /**
     * A drawer listener can be used to respond to drawer events such as becoming
     * fully opened or closed. You should always prefer to perform expensive operations
     * such as drastic relayout when no animation is currently in progress, either before
     * or after the drawer animates.
     * <p/>
     * When using ActionBarDrawerToggle, all DrawerLayout listener methods should be forwarded
     * if the ActionBarDrawerToggle is not used as the DrawerLayout listener directly.
     */
    private class DemoDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }
}
