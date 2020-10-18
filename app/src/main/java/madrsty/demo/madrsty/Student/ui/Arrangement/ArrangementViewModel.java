package madrsty.demo.madrsty.Student.ui.Arrangement;

import android.renderscript.Element;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import madrsty.demo.madrsty.Callback.IArrangementStudentCallback;
import madrsty.demo.madrsty.Common.common;
import madrsty.demo.madrsty.Model.StudentModel;

public class ArrangementViewModel extends ViewModel implements IArrangementStudentCallback {

    private MutableLiveData<List<StudentModel>> listMutableLiveData;

    private MutableLiveData<String> massageErorr = new MutableLiveData<>();

    private IArrangementStudentCallback iArrangementStudentCallback;


    public ArrangementViewModel() {
        iArrangementStudentCallback = this;
    }

    public MutableLiveData<List<StudentModel>> getListMutableLiveData() {

        if (listMutableLiveData == null) {

            listMutableLiveData = new MutableLiveData<>();
            massageErorr = new MutableLiveData<>();


            loadRequests();
        }
        return listMutableLiveData;
    }

    private void loadRequests() {

        List<StudentModel> tmpList = new ArrayList<>();
        Query OutStandingRequestRef = FirebaseDatabase.getInstance().getReference().child(common.STUDENT_KEY);
        OutStandingRequestRef.orderByChild("s_point").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                    StudentModel studentModel = itemSnapShot.getValue(StudentModel.class);

                    // studentModel.setS_point(itemSnapShot.getValue(StudentModel.class).getS_point());
                    // studentModel.setR_id(itemSnapShot.getKey());
                    tmpList.add(studentModel);


                   // Collections.reverse(tmpList);


                }

                iArrangementStudentCallback.onArrangementStudentLoadSuccess(tmpList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iArrangementStudentCallback.onArrangementStudentLoadFailed(databaseError.getMessage());
            }
        });


    }

    public MutableLiveData<String> getMassageErorr() {
        return massageErorr;
    }

    @Override
    public void onArrangementStudentLoadSuccess(List<StudentModel> studentModels) {
        listMutableLiveData.setValue(studentModels);

    }

    @Override
    public void onArrangementStudentLoadFailed(String massege) {
        massageErorr.setValue(massege);

    }
}