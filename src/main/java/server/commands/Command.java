package server.commands;

import common.AvailableCommands;
import common.InstructionPattern;
import common.ResultPattern;
import server.interfaces.Described;
import server.interfaces.Executable;

/**
 * Class {@code Command} is the root of all commands
 * in current program. Every command has {@code Command}
 * as a superclass.
 * All commands can be executed and described.
 */
public abstract class Command implements Described, Executable, Cloneable {
    private long clientId;
    private String title;
    private String description;
    private AvailableCommands command;
    protected ResultPattern report;
    protected InstructionPattern dataBase;
    private boolean readingTheScript;
    protected ExecuteScript.ExecutionStringScanner scriptScanner;


    public Command() {
    }

    /**
     * Constructs a new command.
     *
     * @param command relevant {@link AvailableCommands} command.
     */
    public Command(AvailableCommands command) {
        this.command = command;
        this.title = command.getTitle();
        this.description = command.getDescription();
        this.report = new ResultPattern();
        this.readingTheScript = false;
    }

    public void setScriptScanner(ExecuteScript.ExecutionStringScanner scriptScanner) {
        this.scriptScanner = scriptScanner;
    }

    public void turnOnScriptMode() {
        this.readingTheScript = true;
    }

    public boolean isReadingTheScript() {
        return readingTheScript;
    }

    public void setDataBase(InstructionPattern pattern) {
        this.dataBase = pattern;
    }

    public ResultPattern getReport() {
        return this.report;
    }

    public InstructionPattern getDataBase() {
        return this.dataBase;
    }

    public String getTitle() {
        return this.title;
    }

    public long getClientId() {
        return this.clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Compares this object to the specified object.
     *
     * @param obj the object to compare with.
     * @return {@code true} if objects' {@link Command#title}s are the same;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass())
            return false;
        Command otherObj = (Command) obj;
        return title.equals(otherObj.title);
    }

    /**
     * Returns a hash code using all values of {@code Command} fields.
     *
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return this.title.hashCode()
                + this.description.hashCode()
                + this.command.hashCode();
    }

    /**
     * Returns a {@code String} object representing this {@code Command} value.
     *
     * @return a string representation of the value of this object.
     */
    public String toString() {
        return getClass().getName()
                + "[title=" + title
                + ";description=" + description
                + "]";
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public Command clone() {
        try {
            return (Command) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
