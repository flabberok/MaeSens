package by.maesens.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import by.maesens.android.R;
import by.maesens.android.account.AppAccount;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.interfaces.IControlsTrigger;
import by.maesens.android.network.InternetConnection;
import by.maesens.android.services.GcmRegistrationService;
import by.maesens.android.ui.fragments.BalanceFragment;
import by.maesens.android.ui.fragments.CharityFragment;
import by.maesens.android.ui.fragments.FragmentAllAuctions;
import by.maesens.android.ui.fragments.FragmentAuctionsCreated;
import by.maesens.android.ui.fragments.FragmentAuctionsFavourite;
import by.maesens.android.ui.fragments.FragmentAuctionsWon;
import by.maesens.android.ui.fragments.FragmentNews;
import by.maesens.android.ui.fragments.FragmentSettings;
import by.maesens.android.ui.fragments.MyBidsFragment;
import by.maesens.android.ui.fragments.UserFragment;
import by.maesens.android.utils.GcmTokenManager;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IComponentSetter, IControlsTrigger {

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setUpDrawer();
        setFragment(FragmentAllAuctions.newInstance(true), true);

        //TODO перенести операции с токеном для нотификаций в SplashActivity
        String gcmToken = GcmTokenManager.getToken(this);
        if (gcmToken == null) {
            Intent intent = new Intent(this, GcmRegistrationService.class);
            startService(intent);
        } else {
            Log.d("BaseActivity", "gcmToken = " + gcmToken);
        }
    }

    private void setUpDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.drawer_open, R.string.drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().setGroupVisible(R.id.meetings_group, false);
        setMenuCounter(5);
    }

    private void setMenuCounter(int count) {

        LinearLayout circularTextView = (LinearLayout) mNavigationView.getMenu().findItem(R.id.menu_messages).getActionView();
        TextView view = (TextView) circularTextView.findViewById(R.id.menu_messages_counter_tv);
        if (count > 0) {
            view.setText(String.valueOf(count));
        } else {
            view.setText(null);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return drawerItemSelected(item);
    }

    protected Boolean drawerItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_my_page: {
                setFragment(UserFragment.newInstance(AppAccount.getId()), true);
                break;
            }
            case R.id.menu_news: {
                setFragment(new FragmentNews(), true);
                break;
            }
            case R.id.menu_messages: {
                break;
            }
            case R.id.menu_meetings: {
                setFragment(FragmentAllAuctions.newInstance(true), true);
                break;
            }
            case R.id.menu_my_meetings: {
                if (mNavigationView.getMenu().findItem(R.id.menu_pushed).isVisible()) {
                    mNavigationView.getMenu().setGroupVisible(R.id.meetings_group, false);
                    return true;
                } else {
                    mNavigationView.getMenu().setGroupVisible(R.id.meetings_group, true);
                    return true;
                }
            }
            case R.id.menu_pushed: {
                mNavigationView.getMenu().setGroupVisible(R.id.meetings_group, false);
                setFragment(new FragmentAuctionsCreated());
                break;
            }
            case R.id.menu_won: {
                mNavigationView.getMenu().setGroupVisible(R.id.meetings_group, false);
                setFragment(new FragmentAuctionsWon());
                break;
            }
            case R.id.menu_favourites: {
                mNavigationView.getMenu().setGroupVisible(R.id.meetings_group, false);
                setFragment(new FragmentAuctionsFavourite());
                break;
            }
            case R.id.menu_my_binds: {
                setFragment(new MyBidsFragment(), true);
                break;
            }
            case R.id.menu_projects: {
                setFragment(CharityFragment.newInstance(), true);
                break;
            }
            case R.id.menu_billing:
                setFragment(new BalanceFragment(), true);
                break;
            case R.id.menu_information: {
                break;
            }
            case R.id.menu_settings: {
                setFragment(new FragmentSettings(), true);
                break;
            }
            case R.id.menu_logout: {
//                setFragment(new LoginForm());
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                setNavigationIcon(getSupportFragmentManager().getBackStackEntryCount() == 2);
                if (!getSupportFragmentManager().popBackStackImmediate()) {
                    supportFinishAfterTransition();
                }
            } else
                finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("BaseActivity", "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mToggle.isDrawerIndicatorEnabled()){
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    else
                        drawer.openDrawer(GravityCompat.START);
                } else {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFragment(Fragment f) {
        setFragment(f, false);
    }

    private void setFragment(Fragment f, boolean fromNavigationDrawer) {
        if (InternetConnection.isAvailable(this)) {
            try {
                // Очищаем бэкстек фрагментов (самый первый фрагмент все равно остается)
                if (fromNavigationDrawer)
                    clearBackStack();

                setNavigationIcon(fromNavigationDrawer);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.base_container, f);
                fragmentTransaction.commit();
            } catch (Exception ex) {
                Log.e("setFragment", ex.getMessage());
            }
        }
    }

    private void setNavigationIcon(boolean isEnable){
        mToggle.setDrawerIndicatorEnabled(isEnable);
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void enableControls() {
        Log.d("BaseActivity", "enableControls BaseActivity");
        mDrawer.setEnabled(true);
    }

    @Override
    public void disableControls() {
        Log.d("BaseActivity", "disableControls BaseActivity");
        mDrawer.setEnabled(false);
    }
}
