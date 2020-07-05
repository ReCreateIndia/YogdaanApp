package recreate.india.yogdaan;

public class NgoModel {
    String City;
    String Name;
    String Number;
    String State;
    String  Userid;
    String Work;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getWork() {
        return Work;
    }

    public void setWork(String work) {
        Work = work;
    }

    public NgoModel(String city, String name, String number, String state, String userid, String work) {
        City = city;
        Name = name;
        Number = number;
        State = state;
        Userid = userid;
        Work = work;
    }

    public NgoModel(){
    }
}
