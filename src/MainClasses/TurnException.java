package MainClasses;

public class TurnException extends Exception{
	@Override
	public String getMessage(){
		return "Not your turn!";
	}
}
