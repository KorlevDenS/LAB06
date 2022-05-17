package exceptions;

public class InstructionFetchException extends RuntimeException{
    public InstructionFetchException(String message) {
        super(message);
    }
}
