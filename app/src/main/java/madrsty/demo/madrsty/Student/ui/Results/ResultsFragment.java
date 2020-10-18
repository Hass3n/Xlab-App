package madrsty.demo.madrsty.Student.ui.Results;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import madrsty.demo.madrsty.Common.common;
import madrsty.demo.madrsty.EventBus.PassMassageClick;
import madrsty.demo.madrsty.Model.QuizModel;
import madrsty.demo.madrsty.R;
import madrsty.demo.madrsty.Student.ui.Quiz.QuizFragment;

public class ResultsFragment extends Fragment {

    private ResultsViewModel notificationsViewModel;
    TextView tv, tv2, tv3;
    Button btnRestart;
    QuizModel quizModel;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference request;
    public static String state;

    @SuppressLint("SourceLockedOrientationActivity")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ResultsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_result, container, false);

        tv = (TextView) root.findViewById(R.id.tvres);
        tv2 = (TextView) root.findViewById(R.id.tvres2);
        tv3 = (TextView) root.findViewById(R.id.tvres3);
        btnRestart = (Button) root.findViewById(R.id.btnRestart);

        StringBuffer sb = new StringBuffer();
        sb.append(QuizFragment.correct + "\n");
        StringBuffer sb2 = new StringBuffer();
        sb2.append(QuizFragment.wrong + "\n");
        StringBuffer sb3 = new StringBuffer();
        sb3.append(QuizFragment.correct + "\n");
        tv.setText(sb);
        tv2.setText(sb2);
        tv3.setText(sb3);

        initFirebase();
        checkState();
        setResult();

        QuizFragment.correct = 0;
        QuizFragment.wrong = 0;

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new PassMassageClick("QuitQuiz"));


            }
        });


        return root;
    }


    private void initFirebase() {

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        request = database.getReference();

    }

    private void checkState() {
        if (QuizFragment.correct < QuizFragment.wrong || QuizFragment.correct == 0) {
            state = "fail";
        } else {
            state = "pass";
        }
    }

    private void setResult() {
        QuizModel quizModel = new QuizModel();

        quizModel.setScore(QuizFragment.correct);
        quizModel.setState(state);
        quizModel.setUnit_name(QuizFragment.unit_name);
        quizModel.setStudent_id(getuID());

        if (QuizFragment.unit_name.equals("UnitOne")) {

            request.child(common.RESULTS_KEY).child("1").child(getuID()).setValue(quizModel).addOnCompleteListener(task -> {
                if (state.equals("pass")) {
                    Toast.makeText(getContext(), "تهانينا...! لقد اجتزت الاختبار  ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "للاسف ...! يجيب عليك اعاده الاختبار", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else if (QuizFragment.unit_name.equals("UnitTwo")) {

            request.child(common.RESULTS_KEY).child("2").child(getuID()).setValue(quizModel).addOnCompleteListener(task -> {
                if (state.equals("pass")) {
                    Toast.makeText(getContext(), "تهانينا...! لقد اجتزت الاختبار  ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "للاسف ...! يجيب عليك اعاده الاختبار", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else if (QuizFragment.unit_name.equals("UnitThree")) {

            request.child(common.RESULTS_KEY).child("3").child(getuID()).setValue(quizModel).addOnCompleteListener(task -> {
                if (state.equals("pass")) {
                    Toast.makeText(getContext(), "تهانينا...! لقد اجتزت الاختبار  ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "للاسف ...! يجيب عليك اعاده الاختبار", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            request.child(common.RESULTS_KEY).child("4").child(getuID()).setValue(quizModel).addOnCompleteListener(task -> {
                if (state.equals("pass")) {
                    Toast.makeText(getContext(), "تهانينا...! لقد اجتزت الاختبار  ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "للاسف ...! يجيب عليك اعاده الاختبار", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());

        }


    }

    private String getuID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }


}