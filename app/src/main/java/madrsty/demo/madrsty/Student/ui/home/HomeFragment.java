package madrsty.demo.madrsty.Student.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import madrsty.demo.madrsty.Common.common;
import madrsty.demo.madrsty.EventBus.PassMassageClick;
import madrsty.demo.madrsty.Model.QuizModel;
import madrsty.demo.madrsty.Model.StudentModel;
import madrsty.demo.madrsty.R;

import static madrsty.demo.madrsty.Common.common.getuID;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Unbinder unbinder;

    private View root;
    private int finalScore = 0;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference request;
    SweetAlertDialog  sweetAlertDialog;
    public static String unit_name_home;

    QuizModel quizModel;

    @BindView(R.id.imageView11)
    ImageView imageView11;

    @BindView(R.id.imageView13)
    ImageView imageView13;

    @BindView(R.id.imageView21)
    ImageView imageView21;

    @BindView(R.id.imageView22)
    ImageView imageView22;

    @BindView(R.id.imageView23)
    ImageView imageView23;

    @BindView(R.id.imageView31)
    ImageView imageView31;

    @BindView(R.id.imageView32)
    ImageView imageView32;

    @BindView(R.id.imageView33)
    ImageView imageView33;

    @BindView(R.id.imageView34)
    ImageView imageView34;

    @BindView(R.id.imageView41)
    ImageView imageView41;

    @BindView(R.id.imageView42)
    ImageView imageView42;

    @BindView(R.id.imageView43)
    ImageView imageView43;

    @BindView(R.id.rocket1)
    ImageView rocket1;

    @BindView(R.id.rocket2)
    ImageView rocket2;

    @BindView(R.id.rocket3)
    ImageView rocket3;

    @BindView(R.id.rocket4)
    ImageView rocket4;

    @OnClick(R.id.imageView13)
    void onQuestionUnitOneSelected() {

        unit_name_home = "الوحده الاولي";
        EventBus.getDefault().postSticky(new PassMassageClick("StartQuiz"));


    }

    @OnClick(R.id.imageView23)
    void onQuestionUnitTwoSelected() {

        unit_name_home = "الوحده الثانيه";

        EventBus.getDefault().postSticky(new PassMassageClick("StartQuiz"));

    }

    @OnClick(R.id.imageView34)
    void onQuestionUnitThreeSelected() {

        unit_name_home = "الوحده الثالثه";

        EventBus.getDefault().postSticky(new PassMassageClick("StartQuiz"));

    }

    @OnClick(R.id.imageView43)
    void onQuestionUnitFourSelected() {

        unit_name_home = "الوحده الرابعه";

        EventBus.getDefault().postSticky(new PassMassageClick("StartQuiz"));

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);


        initViews();

        initFirebase();

        checkInternet();


        return root;
    }

    private void checkInternet() {
        if (common.isConnectedToInternet(getContext())) {
            check_rock_place();
        } else {
            Toast.makeText(getContext(), "من فضلك تفقد الاتصال بالانترنت !", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void initViews() {

        unbinder = ButterKnife.bind(this, root);


    }

    private void initFirebase() {

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        request = database.getReference();

    }

    private void check_rock_place() {


        request.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(common.RESULTS_KEY).child("1").child(getuID()).exists()) {

                    quizModel = dataSnapshot.child(common.RESULTS_KEY).child("1").child(getuID()).getValue(QuizModel.class);

                    if (quizModel.getState().equals("pass")) {
                        finalScore += quizModel.getScore();
                        if (dataSnapshot.child(common.RESULTS_KEY).child("2").child(getuID()).exists()) {

                            quizModel = dataSnapshot.child(common.RESULTS_KEY).child("2").child(getuID()).getValue(QuizModel.class);

                            if (quizModel.getState().equals("pass")) {
                                finalScore += quizModel.getScore();

                                if (dataSnapshot.child(common.RESULTS_KEY).child("3").child(getuID()).exists()) {

                                    quizModel = dataSnapshot.child(common.RESULTS_KEY).child("3").child(getuID()).getValue(QuizModel.class);

                                    if (quizModel.getState().equals("pass")) {
                                        finalScore += quizModel.getScore();

                                        if (dataSnapshot.child(common.RESULTS_KEY).child("4").child(getuID()).exists()) {

                                            quizModel = dataSnapshot.child(common.RESULTS_KEY).child("4").child(getuID()).getValue(QuizModel.class);

                                            if (quizModel.getState().equals("pass")) {
                                                finalScore += quizModel.getScore();


                                                  sweetAlertDialog =     new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);

                                                sweetAlertDialog.setTitleText("انتهاء المرحله")
                                                        .setContentText("لقد انهيت المرحله بنجاح \n والنقاط الخاصه بك هي :" + finalScore)
                                                        .setConfirmText("اغلاق")
                                                        .setConfirmClickListener(sDialog -> {
                                                            setFinalScore();
                                                            sDialog.dismiss();
                                                        })
                                                        .show();


                                            } else {
                                                rocket4.setVisibility(View.VISIBLE);
                                                imageView43.setVisibility(View.VISIBLE);
                                                imageView34.setVisibility(View.VISIBLE);
                                                imageView23.setVisibility(View.VISIBLE);
                                                imageView13.setVisibility(View.VISIBLE);

                                            }

                                        } else {
                                            rocket4.setVisibility(View.VISIBLE);
                                            imageView43.setVisibility(View.VISIBLE);
                                            imageView34.setVisibility(View.VISIBLE);
                                            imageView23.setVisibility(View.VISIBLE);
                                            imageView13.setVisibility(View.VISIBLE);

                                        }

                                    } else {
                                        rocket3.setVisibility(View.VISIBLE);
                                        imageView34.setVisibility(View.VISIBLE);
                                        imageView23.setVisibility(View.VISIBLE);
                                        imageView13.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    rocket3.setVisibility(View.VISIBLE);
                                    imageView34.setVisibility(View.VISIBLE);
                                    imageView23.setVisibility(View.VISIBLE);
                                    imageView13.setVisibility(View.VISIBLE);

                                }
                            } else {

                                rocket2.setVisibility(View.VISIBLE);
                                imageView23.setVisibility(View.VISIBLE);
                                imageView13.setVisibility(View.VISIBLE);


                            }
                        } else {
                            rocket2.setVisibility(View.VISIBLE);
                            imageView23.setVisibility(View.VISIBLE);
                            imageView13.setVisibility(View.VISIBLE);


                        }
                    } else {

                        rocket1.setVisibility(View.VISIBLE);
                        imageView13.setVisibility(View.VISIBLE);

                    }
                } else {
                    rocket1.setVisibility(View.VISIBLE);
                    imageView13.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFinalScore() {

        request.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(common.STUDENT_KEY).child(getuID()).exists()) {

                    StudentModel studentModel = dataSnapshot.child(common.STUDENT_KEY).child(getuID()).getValue(StudentModel.class);

                    studentModel.setS_point(finalScore);
                    request.child(common.STUDENT_KEY).child(getuID()).setValue(studentModel).addOnCompleteListener(task -> {
                        Toast.makeText(getContext(), "Final Score Set Successfully", Toast.LENGTH_SHORT).show();
                        finalScore = 0;
                        sweetAlertDialog.dismiss();



                    }).addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
