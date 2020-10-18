package madrsty.demo.madrsty.Callback;



import java.util.List;

import madrsty.demo.madrsty.Model.StudentModel;

public interface IArrangementStudentCallback {

    void onArrangementStudentLoadSuccess(List<StudentModel> studentModels);

    void onArrangementStudentLoadFailed(String massege);
}
