package common;

import java.io.Serializable;

public class CompleteMessage implements Serializable {

    private final TransportedData transportedData;
    private ResultPattern resultPattern;
    private InstructionPattern instructionPattern;

    public CompleteMessage(TransportedData data, ResultPattern result) {
        this.transportedData = data;
        this.resultPattern = result;
    }

    public CompleteMessage(TransportedData data, InstructionPattern pattern) {
        this.transportedData = data;
        this.instructionPattern = pattern;
    }

    public InstructionPattern getInstructionPattern() {
        return this.instructionPattern;
    }

    public ResultPattern getResultPattern() {
        return this.resultPattern;
    }

    public TransportedData getTransportedData() {
        return this.transportedData;
    }

}
