package business_logic;

/*
 * Carsten Andersen
 */

public class OperationNotAllowedException extends Exception {
	private static final long serialVersionUID = 1L;

	public OperationNotAllowedException(String Message) {
		super(Message);
	}

}
