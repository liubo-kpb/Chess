
package MainClasses;

public class KingUnderThreatException extends Exception{
    @Override
    public String getMessage() {
        return "Unable to do that.\nKing is under threat.";
    }
}
