package commands;

import Server.ScriptCommandManager;
import interfaces.Described;

/**
 * {@code CommandObjects} enum keeps {@code Command} objects
 * corresponding their data from {@link AvailableCommands} enum.
 * Every constant has the identical name as in relevant {@link AvailableCommands} object.
 * {@code CommandObjects} uses {@link AvailableCommands} to create
 * {@code Command} objects. These {@code Command} objects are used
 * in {@link ScriptCommandManager#execution(AvailableCommands)}.
 * It is necessary to include here a new constant, linked to relevant
 * {@link AvailableCommands} object, when adding a new instruction to execute to this program.
 */
public enum CommandObjects implements Described {
    HELP(new Help(AvailableCommands.HELP)),
    INFO(new Info(AvailableCommands.INFO)),
    ADD(new Add(AvailableCommands.ADD)),
    SHOW(new Show(AvailableCommands.SHOW)),
    CLEAR(new Clear(AvailableCommands.CLEAR)),
    PRINT_UNIQUE_NUMBER_OF_PARTICIPANTS(new
            PrintUniqueNumberOfParticipants(AvailableCommands.PRINT_UNIQUE_NUMBER_OF_PARTICIPANTS)),
    GROUP_COUNTING_BY_FRONT_MAN(new GroupCountingByFrontMan(AvailableCommands.GROUP_COUNTING_BY_FRONT_MAN)),
    ADD_IF_MAX(new AddIfMax(AvailableCommands.ADD_IF_MAX)),
    ADD_IF_MIN(new AddIfMin(AvailableCommands.ADD_IF_MIN)),
    REMOVE_GREATER(new RemoveGreater(AvailableCommands.REMOVE_GREATER)),
    REMOVE_BY_ID(new RemoveByID(AvailableCommands.REMOVE_BY_ID)),
    REMOVE_ALL_BY_FRONT_MAN(new RemoveAllByFrontMan(AvailableCommands.REMOVE_ALL_BY_FRONT_MAN)),
    UPDATE(new Update(AvailableCommands.UPDATE)),
    SAVE(new Save(AvailableCommands.SAVE)),
    EXECUTE_SCRIPT(new ExecuteScript(AvailableCommands.EXECUTE_SCRIPT)),
    EXIT(new Exit(AvailableCommands.EXIT));

    private final Command command;

    /**
     * Creates {@code Command} object.
     *
     * @param command {@code Command} object.
     */
    CommandObjects(Command command) {
        this.command = command;
    }

    /**
     * @return {@code Command} object.
     */
    public Command getCommand() {
        return this.command;
    }

    public String getDescription() {
        return "Хранит объект типа " + command.getClass().getName();
    }

}
