package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.ParseIndexException;

/**
 * Parses input arguments and creates a new UnassignCommand object.
 */
public class UnassignCommandParser implements Parser<UnassignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnassignCommand
     * and returns an UnassignCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnassignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON_INDEX, PREFIX_TASK_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON_INDEX, PREFIX_TASK_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
        }

        Index personIndex;
        Index taskIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON_INDEX).get());
        } catch (ParseIndexException pie) {
            throw new ParseIndexException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, pie);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE), pe);
        }

        try {
            taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());
        } catch (ParseIndexException pie) {
            throw new ParseIndexException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, pie);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE), pe);
        }

        return new UnassignCommand(personIndex, taskIndex);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
