package madrsty.demo.madrsty.EventBus;

public class PassQuizStateClick {

    private String unitName,passStata,message;

    public PassQuizStateClick(String unitName, String passStata, String message) {
        this.unitName = unitName;
        this.passStata = passStata;
        this.message = message;
    }

    public String getPassStata() {
        return passStata;
    }

    public void setPassStata(String passStata) {
        this.passStata = passStata;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
