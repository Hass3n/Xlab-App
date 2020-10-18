package madrsty.demo.madrsty.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ActivityKt;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;
import madrsty.demo.madrsty.Common.common;
import madrsty.demo.madrsty.EventBus.PassMassageClick;
import madrsty.demo.madrsty.LoginActivity;
import madrsty.demo.madrsty.R;
import me.ibrahimsn.lib.SmoothBottomBar;

public class Student2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private SmoothBottomBar smoothBottomBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student3);

        smoothBottomBar = findViewById(R.id.bottomBar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_student,
                R.id.nav_quiz,
                R.id.nav_arrangement,
                R.id.nav_profile_student,
                R.id.nav_result)
                .build();

        this.navController = Navigation.findNavController(this, R.id.main_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        getSupportActionBar().hide();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bottom, menu);

        smoothBottomBar.setupWithNavController(menu, navController);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);
        //  drawer.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.nav_home_student:

                navController.navigate(R.id.nav_home_student);

                break;
            case R.id.nav_profile_student:

                navController.navigate(R.id.nav_profile_student);

                break;

            case R.id.nav_arrangement:

                navController.navigate(R.id.nav_arrangement);
                break;

        }
        return true;
    }

    @Override
    protected void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);


    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onCreateGroupSelected(PassMassageClick event) {

        if (event.getMassage().equals("StartQuiz")) {

            navController.navigate(R.id.nav_quiz);

        } else if (event.getMassage().equals("ShowResult")) {

            navController.navigate(R.id.nav_result);
            // EventBus.getDefault().postSticky(new PassQuizStateClick("UnitOne"));


            //  EventBus.clearCaches();


        } else if (event.getMassage().equals("QuitQuiz")) {

            navController.navigate(R.id.nav_home_student);

        }else if (event.getMassage().equals("SignOut")) {

            common.currentStudent = null;
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(Student2.this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            EventBus.clearCaches();

        }
    }

    private String getuID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }

}
