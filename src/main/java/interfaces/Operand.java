package interfaces;

/**
 * Interface {@code Operand} requires implementing classes
 * to realise operand installation.
 */
public interface Operand {
    /**
     * The method is used to get operands from
     * {@code String} interpretation.
     * Created for commands that have simple operands.
     */
    void installOperand(String stringRepresentation);
}
