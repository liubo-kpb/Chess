package MainClasses;

public class KingNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Error! King not found.";
    }
}
