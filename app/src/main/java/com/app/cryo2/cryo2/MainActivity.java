package com.app.cryo2.cryo2;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.cryo2.cryo2.fragments.ContactFragment;
import com.app.cryo2.cryo2.fragments.FirstFragment;
import com.app.cryo2.cryo2.fragments.HistoryFragment;
import com.app.cryo2.cryo2.fragments.ProfileFragment;
import com.app.cryo2.cryo2.fragments.SecondFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar toolbar;
    private Fragment ff,sf,profilef,contactf,hystoryf;
    private boolean emptyContainer=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mtoggle= new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.Open,R.string.Close);
        nvDrawer=findViewById(R.id.nvView);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //menu para abrir el navigation drawer
        mDrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ff = new FirstFragment();
        sf = new SecondFragment();
        profilef = new ProfileFragment();
        hystoryf=new HistoryFragment();
        contactf=new ContactFragment();

        //cargamos fragment inicio
        FragmentManager fm= getSupportFragmentManager();
        fm.beginTransaction().replace(
                R.id.flContent,
                ff,
                ff.getTag()
        ).commit();

        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment miFragment=null;
                switch (item.getItemId())
                {
                    case R.id.nav_inicio:
                        miFragment=ff;
                        break;
                    case R.id.historial:
                        miFragment=hystoryf;
                        break;
                    case R.id.perfil:
                        miFragment=profilef;
                        break;
                    case R.id.Contacta:
                        miFragment=contactf;
                        break;
                }

                if(miFragment!=null) //si hace click en uno de los 3 botones, si pincha fuera, es nulo
                {
                    FragmentManager miFragManager = getSupportFragmentManager();
                    FragmentTransaction miFragTransa = miFragManager.beginTransaction();
                    if (emptyContainer == true)    //si esta vacio mete un fragment, si esta lleno, saca el que está, y mete el nuevo
                    {
                        miFragTransa.add(R.id.flContent,miFragment);
                        emptyContainer=false;   //Porque ya no esta vacio
                    } else {
                        miFragTransa.replace(R.id.flContent,miFragment);

                    }
                    miFragTransa.commit();
                    miFragManager.executePendingTransactions();
                    mDrawer.closeDrawers(); //decimos que cierre todos los menus desplegables
                    return true;
                }

                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        else {
            switch(item.getItemId()){
                case R.id.home:
                    mDrawer.openDrawer(GravityCompat.START);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
