package madrsty.demo.madrsty.EventBus;


import madrsty.demo.madrsty.Model.StudentModel;

public class ArrangementStudentItemClick {

    private boolean success;
    private StudentModel studentModel;

    public ArrangementStudentItemClick(boolean success, StudentModel studentModel) {
        this.success = success;
        this.studentModel = studentModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public StudentModel getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }
}
