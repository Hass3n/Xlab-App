package madrsty.demo.madrsty.Model;

public class QuizModel {
    String student_id,unit_name,state;
    int score;

    public QuizModel() {
    }

    public QuizModel(String student_id, String unit_name, String state, int score) {
        this.student_id = student_id;
        this.unit_name = unit_name;
        this.state = state;
        this.score = score;
    }


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
