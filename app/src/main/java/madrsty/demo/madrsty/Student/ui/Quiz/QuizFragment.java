package madrsty.demo.madrsty.Student.ui.Quiz;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import madrsty.demo.madrsty.Common.common;
import madrsty.demo.madrsty.EventBus.PassMassageClick;
import madrsty.demo.madrsty.Model.QuizModel;
import madrsty.demo.madrsty.R;
import madrsty.demo.madrsty.Student.ui.home.HomeFragment;

public class QuizFragment extends Fragment {

    private QuizViewModel galleryViewModel;
    private Unbinder unbinder;
    private View root;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference request;

    private QuizFragment context = this;

    @BindView(R.id.include_qustion)
    View include_qustion;


    @BindView(R.id.include_quiz_state)
    View include_quiz_state;

    @BindView(R.id.quiz_state_pass_txt)
    TextView quiz_state_pass_txt;

    @BindView(R.id.quiz_state_fail_txt)
    TextView quiz_state_fail_txt;

    @BindView(R.id.quiz_state_pass_img)
    ImageView quiz_state_pass_img;

    @BindView(R.id.quiz_state_fail_img)
    ImageView quiz_state_fail_img;

    @BindView(R.id.quiz_state_btnRestart)
    Button quiz_state_btnRestart;

    @BindView(R.id.quiz_state_btnQuit)
    Button quiz_state_btnQuit;

    @BindView(R.id.tvque)
    TextView tv;

    @BindView(R.id.button3)
    Button submitbutton;

    @BindView(R.id.buttonquit)
    Button quitbutton;

    @BindView(R.id.answersgrp)
    RadioGroup radio_g;

    @BindView(R.id.radioButton)
    RadioButton rb1;

    @BindView(R.id.radioButton2)
    RadioButton rb2;

    @BindView(R.id.radioButton3)
    RadioButton rb3;

    @BindView(R.id.radioButton4)
    RadioButton rb4;


    private Timer timer;


    private String questions_unit_one[];
    String answers_unit_one[];
    String co_answers_unit_one[];

    private String questions_unit_two[];
    String answers_unit_two[];
    String co_answers_unit_two[];

    private String questions_unit_three[];
    String answers_unit_three[];
    String co_answers_unit_three[];

    private String questions_unit_four[];
    String answers_unit_four[];
    String co_answers_unit_four[];

    private int countdownValue = 60;
    int flag = 0;
    public static int marks = 0, correct = 0, wrong = 0, score = 0;
    public static String unit_name;


    private void check_previous_quiz_state() {


        request.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (HomeFragment.unit_name_home.equals("الوحده الاولي")) {
                    if (dataSnapshot.child(common.RESULTS_KEY).child("1").child(getuID()).exists()) {

                        QuizModel quizModel = dataSnapshot.child(common.RESULTS_KEY).child("1").child(getuID()).getValue(QuizModel.class);

                        if (quizModel.getState().equals("pass")) {

                            include_quiz_state.setVisibility(View.VISIBLE);
                            quiz_state_btnQuit.setOnClickListener(view -> EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz")));


                        } else {

                            include_quiz_state.setVisibility(View.VISIBLE);


                            quiz_state_fail_img.setVisibility(View.VISIBLE);
                            quiz_state_fail_txt.setVisibility(View.VISIBLE);
                            quiz_state_btnRestart.setVisibility(View.VISIBLE);

                            quiz_state_pass_img.setVisibility(View.INVISIBLE);
                            quiz_state_pass_txt.setVisibility(View.INVISIBLE);
                            quiz_state_btnQuit.setVisibility(View.INVISIBLE);

                            quiz_state_btnRestart.setOnClickListener(view -> {

                                include_quiz_state.setVisibility(View.INVISIBLE);
                                include_qustion.setVisibility(View.VISIBLE);

                                initQuestion_unit_one();

                            });


                        }

                    } else {

                        include_qustion.setVisibility(View.VISIBLE);

                        initQuestion_unit_one();

                    }

                } else if (HomeFragment.unit_name_home.equals("الوحده الثانيه")) {
                    if (dataSnapshot.child(common.RESULTS_KEY).child("2").child(getuID()).exists()) {

                        QuizModel quizModel = dataSnapshot.child(common.RESULTS_KEY).child("2").child(getuID()).getValue(QuizModel.class);

                        if (quizModel.getState().equals("pass")) {

                            include_quiz_state.setVisibility(View.VISIBLE);
                            quiz_state_btnQuit.setOnClickListener(view -> EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz")));


                        } else {

                            include_quiz_state.setVisibility(View.VISIBLE);


                            quiz_state_fail_img.setVisibility(View.VISIBLE);
                            quiz_state_fail_txt.setVisibility(View.VISIBLE);
                            quiz_state_btnRestart.setVisibility(View.VISIBLE);

                            quiz_state_pass_img.setVisibility(View.INVISIBLE);
                            quiz_state_pass_txt.setVisibility(View.INVISIBLE);
                            quiz_state_btnQuit.setVisibility(View.INVISIBLE);

                            quiz_state_btnRestart.setOnClickListener(view -> {


                                include_quiz_state.setVisibility(View.INVISIBLE);
                                include_qustion.setVisibility(View.VISIBLE);


                                initQuestion_unit_two();

                            });


                        }

                    } else {

                        include_qustion.setVisibility(View.VISIBLE);


                        initQuestion_unit_two();

                    }

                } else if (HomeFragment.unit_name_home.equals("الوحده الثالثه")) {
                    if (dataSnapshot.child(common.RESULTS_KEY).child("3").child(getuID()).exists()) {

                        QuizModel quizModel = dataSnapshot.child(common.RESULTS_KEY).child("3").child(getuID()).getValue(QuizModel.class);

                        if (quizModel.getState().equals("pass")) {

                            include_quiz_state.setVisibility(View.VISIBLE);
                            quiz_state_btnQuit.setOnClickListener(view -> EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz")));


                        } else {

                            include_quiz_state.setVisibility(View.VISIBLE);


                            quiz_state_fail_img.setVisibility(View.VISIBLE);
                            quiz_state_fail_txt.setVisibility(View.VISIBLE);
                            quiz_state_btnRestart.setVisibility(View.VISIBLE);

                            quiz_state_pass_img.setVisibility(View.INVISIBLE);
                            quiz_state_pass_txt.setVisibility(View.INVISIBLE);
                            quiz_state_btnQuit.setVisibility(View.INVISIBLE);

                            quiz_state_btnRestart.setOnClickListener(view -> {

                                include_quiz_state.setVisibility(View.INVISIBLE);
                                include_qustion.setVisibility(View.VISIBLE);


                                initQuestion_unit_three();

                            });


                        }

                    } else {

                        include_qustion.setVisibility(View.VISIBLE);


                        initQuestion_unit_three();

                    }

                } else if (HomeFragment.unit_name_home.equals("الوحده الرابعه")) {
                    if (dataSnapshot.child(common.RESULTS_KEY).child("4").child(getuID()).exists()) {

                        QuizModel quizModel = dataSnapshot.child(common.RESULTS_KEY).child("4").child(getuID()).getValue(QuizModel.class);

                        if (quizModel.getState().equals("pass")) {

                            include_quiz_state.setVisibility(View.VISIBLE);
                            quiz_state_btnQuit.setOnClickListener(view -> EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz")));


                        } else {

                            include_quiz_state.setVisibility(View.VISIBLE);


                            quiz_state_fail_img.setVisibility(View.VISIBLE);
                            quiz_state_fail_txt.setVisibility(View.VISIBLE);
                            quiz_state_btnRestart.setVisibility(View.VISIBLE);

                            quiz_state_pass_img.setVisibility(View.INVISIBLE);
                            quiz_state_pass_txt.setVisibility(View.INVISIBLE);
                            quiz_state_btnQuit.setVisibility(View.INVISIBLE);

                            quiz_state_btnRestart.setOnClickListener(view -> {

                                include_quiz_state.setVisibility(View.INVISIBLE);
                                include_qustion.setVisibility(View.VISIBLE);

                                initQuestion_unit_four();

                            });
                        }

                    } else {

                        include_qustion.setVisibility(View.VISIBLE);

                        initQuestion_unit_four();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @SuppressLint("SourceLockedOrientationActivity")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(QuizViewModel.class);
        root = inflater.inflate(R.layout.fragment_quiz, container, false);


        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        initArrays();

        initViews();

        initFirebase();

        check_previous_quiz_state();

        return root;
    }

    private void initArrays() {
        Resources res = getResources();

        questions_unit_one = res.getStringArray(R.array.qu_unit_one);
        co_answers_unit_one = res.getStringArray(R.array.co_ans_unit_one);
        answers_unit_one = res.getStringArray(R.array.ans_unit_one);

        questions_unit_two = res.getStringArray(R.array.qu_unit_two);
        co_answers_unit_two = res.getStringArray(R.array.co_ans_unit_two);
        answers_unit_two = res.getStringArray(R.array.ans_unit_two);

        questions_unit_three = res.getStringArray(R.array.qu_unit_three);
        co_answers_unit_three = res.getStringArray(R.array.co_ans_unit_three);
        answers_unit_three = res.getStringArray(R.array.ans_unit_three);

        questions_unit_four = res.getStringArray(R.array.qu_unit_four);
        co_answers_unit_four = res.getStringArray(R.array.co_ans_unit_four);
        answers_unit_four = res.getStringArray(R.array.ans_unit_four);

    }


    private void initViews() {

        unbinder = ButterKnife.bind(this, root);


    }

    private void initFirebase() {

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        request = database.getReference();

    }


    private void initQuestion_unit_one() {
        timer = new Timer();
        final TextView score = root.findViewById(R.id.textView4);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    countdownValue--;
                    score.setText("00:00:" + countdownValue + "");

                    //when the value of the countdown reaches 0, we start the result activity:
                    if (countdownValue == 0) {
                        //pass unit name to result
                        unit_name = "UnitOne";
                        Toast.makeText(getContext(), "لقد انتهي الوقت المحدد ..للاسف !", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));


                        timer.cancel();
                    }
                });
            }
        }, 0, 1000);

        tv.setText(questions_unit_one[flag]);     // questioin
        rb1.setText(answers_unit_one[0]);             // opt
        rb2.setText(answers_unit_one[1]);
        rb3.setText(answers_unit_one[2]);
        rb4.setText(answers_unit_one[3]);
        submitbutton.setOnClickListener(v -> {


            if (radio_g.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "يجب عليك ان تختار اختيار واحد علي الاقل ", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton uans = (RadioButton) root.findViewById(radio_g.getCheckedRadioButtonId());
            String ansText = uans.getText().toString();

            if (ansText.equals(co_answers_unit_one[flag])) {
                correct++;
                Toast.makeText(getContext(), "صح", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(getContext(), "خطا", Toast.LENGTH_SHORT).show();
            }


            flag++;

            if (score != null)
                score.setText("" + correct);

            if (flag < questions_unit_one.length)           // second question and second radio
            {
                tv.setText(questions_unit_one[flag]);
                rb1.setText(answers_unit_one[flag * 4]);
                rb2.setText(answers_unit_one[flag * 4 + 1]);
                rb3.setText(answers_unit_one[flag * 4 + 2]);
                rb4.setText(answers_unit_one[flag * 4 + 3]);
            } else {

                // send score to next result activity
                marks = correct;

                unit_name = "UnitOne";

                EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));

                timer.cancel();


            }
            radio_g.clearCheck();
        });

        quitbutton.setOnClickListener(v -> {

            EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz"));

            timer.cancel();


        });
    }


    private void initQuestion_unit_two() {
        timer = new Timer();
        final TextView score = root.findViewById(R.id.textView4);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    countdownValue--;
                    score.setText("00:00:" + countdownValue + "");

                    //when the value of the countdown reaches 0, we start the result activity:
                    if (countdownValue == 0) {

                        unit_name = "UnitTwo";
                        Toast.makeText(getContext(), "لقد انتهي الوقت المحدد ..للاسف !", Toast.LENGTH_SHORT).show();

                        EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));


                        timer.cancel();
                    }
                });
            }
        }, 0, 1000);

        tv.setText(questions_unit_two[flag]);     // questioin
        rb1.setText(answers_unit_two[0]);             // opt
        rb2.setText(answers_unit_two[1]);
        rb3.setText(answers_unit_two[2]);
        rb4.setText(answers_unit_two[3]);
        submitbutton.setOnClickListener(v -> {


            if (radio_g.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "يجب عليك ان تختار اختيار واحد علي الاقل ", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton uans = (RadioButton) root.findViewById(radio_g.getCheckedRadioButtonId());
            String ansText = uans.getText().toString();

            if (ansText.equals(co_answers_unit_two[flag])) {
                correct++;
                Toast.makeText(getContext(), "صح", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(getContext(), "خطا", Toast.LENGTH_SHORT).show();
            }


            flag++;

            if (score != null)
                score.setText("" + correct);

            if (flag < questions_unit_two.length)           // second question and second radio
            {
                tv.setText(questions_unit_two[flag]);
                rb1.setText(answers_unit_two[flag * 4]);
                rb2.setText(answers_unit_two[flag * 4 + 1]);
                rb3.setText(answers_unit_two[flag * 4 + 2]);
                rb4.setText(answers_unit_two[flag * 4 + 3]);
            } else {

                // send score to next result activity
                marks = correct;

                unit_name = "UnitTwo";

                EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));
                timer.cancel();


            }
            radio_g.clearCheck();
        });

        quitbutton.setOnClickListener(v -> {


            EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz"));
            timer.cancel();


        });
    }

    private void initQuestion_unit_three() {
        timer = new Timer();
        final TextView score = root.findViewById(R.id.textView4);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    countdownValue--;
                    score.setText("00:00:" + countdownValue + "");

                    //when the value of the countdown reaches 0, we start the result activity:
                    if (countdownValue == 0) {


                        unit_name = "UnitThree";

                        Toast.makeText(getContext(), "لقد انتهي الوقت المحدد ..للاسف !", Toast.LENGTH_SHORT).show();

                        EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));


                        timer.cancel();
                    }
                });
            }
        }, 0, 1000);

        tv.setText(questions_unit_three[flag]);     // questioin
        rb1.setText(answers_unit_three[0]);             // opt
        rb2.setText(answers_unit_three[1]);
        rb3.setText(answers_unit_three[2]);
        rb4.setText(answers_unit_three[3]);
        submitbutton.setOnClickListener(v -> {


            if (radio_g.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "يجب عليك ان تختار اختيار واحد علي الاقل ", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton uans = (RadioButton) root.findViewById(radio_g.getCheckedRadioButtonId());
            String ansText = uans.getText().toString();

            if (ansText.equals(co_answers_unit_three[flag])) {
                correct++;
                Toast.makeText(getContext(), "صح", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(getContext(), "خطا", Toast.LENGTH_SHORT).show();
            }


            flag++;

            if (score != null)
                score.setText("" + correct);

            if (flag < questions_unit_three.length)           // second question and second radio
            {
                tv.setText(questions_unit_three[flag]);
                rb1.setText(answers_unit_three[flag * 4]);
                rb2.setText(answers_unit_three[flag * 4 + 1]);
                rb3.setText(answers_unit_three[flag * 4 + 2]);
                rb4.setText(answers_unit_three[flag * 4 + 3]);
            } else {

                // send score to next result activity
                marks = correct;

                unit_name = "UnitThree";

                EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));
                timer.cancel();


            }
            radio_g.clearCheck();
        });

        quitbutton.setOnClickListener(v -> {
            EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz"));
            timer.cancel();


        });
    }

    private void initQuestion_unit_four() {
        timer = new Timer();
        final TextView score = root.findViewById(R.id.textView4);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    countdownValue--;
                    score.setText("00:00:" + countdownValue + "");

                    //when the value of the countdown reaches 0, we start the result activity:
                    if (countdownValue == 0) {

                        unit_name = "UnitFour";
                        Toast.makeText(getContext(), "لقد انتهي الوقت المحدد ..للاسف !", Toast.LENGTH_SHORT).show();

                        EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));


                        timer.cancel();
                    }
                });
            }
        }, 0, 1000);

        tv.setText(questions_unit_four[flag]);     // questioin
        rb1.setText(answers_unit_four[0]);             // opt
        rb2.setText(answers_unit_four[1]);
        rb3.setText(answers_unit_four[2]);
        rb4.setText(answers_unit_four[3]);
        submitbutton.setOnClickListener(v -> {


            if (radio_g.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "يجب عليك ان تختار اختيار واحد علي الاقل ", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton uans = (RadioButton) root.findViewById(radio_g.getCheckedRadioButtonId());
            String ansText = uans.getText().toString();

            if (ansText.equals(co_answers_unit_four[flag])) {
                correct++;
                Toast.makeText(getContext(), "صح", Toast.LENGTH_SHORT).show();
            } else {
                wrong++;
                Toast.makeText(getContext(), "خطا", Toast.LENGTH_SHORT).show();
            }


            flag++;

            if (score != null)
                score.setText("" + correct);

            if (flag < questions_unit_four.length)           // second question and second radio
            {
                tv.setText(questions_unit_four[flag]);
                rb1.setText(answers_unit_four[flag * 4]);
                rb2.setText(answers_unit_four[flag * 4 + 1]);
                rb3.setText(answers_unit_four[flag * 4 + 2]);
                rb4.setText(answers_unit_four[flag * 4 + 3]);
            } else {

                // send score to next result activity
                marks = correct;
                unit_name = "UnitFour";

                EventBus.getDefault().postSticky(new PassMassageClick("ShowResult"));
                timer.cancel();


            }
            radio_g.clearCheck();
        });

        quitbutton.setOnClickListener(v -> {
            EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz"));
            timer.cancel();


        });
    }

    private String getuID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }
}