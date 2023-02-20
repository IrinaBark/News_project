package by.htp.ex.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import by.htp.ex.controller.impl.ChangeLocale;
import by.htp.ex.controller.impl.DeleteNews;
import by.htp.ex.controller.impl.DoRegistration;
import by.htp.ex.controller.impl.DoSIgnIn;
import by.htp.ex.controller.impl.DoSignOut;
import by.htp.ex.controller.impl.GoToAddNews;
import by.htp.ex.controller.impl.GoToBasePage;
import by.htp.ex.controller.impl.GoToConfirmationPage;
import by.htp.ex.controller.impl.GoToEditNews;
import by.htp.ex.controller.impl.GoToErrorPage;
import by.htp.ex.controller.impl.GoToNewsList;
import by.htp.ex.controller.impl.GoToRegistrationPageCommand;
import by.htp.ex.controller.impl.GoToViewNews;
import by.htp.ex.controller.impl.NoSuchFunction;
import by.htp.ex.controller.impl.SaveNews;
import by.htp.ex.controller.impl.UpdateNews;

public class CommandProvider {
	private final Map<CommandName, Command> commands = new HashMap<>();
	private static final String ERROR_COMMAND = "NO_SUCH_fUNCTION";
	
	public CommandProvider() {
		commands.put(CommandName.GO_TO_BASE_PAGE, new GoToBasePage());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.DO_SIGN_IN, new DoSIgnIn());
		commands.put(CommandName.DO_SIGN_OUT, new DoSignOut());
		commands.put(CommandName.GO_TO_NEWS_LIST, new GoToNewsList());
		commands.put(CommandName.GO_TO_VIEW_NEWS, new GoToViewNews());
		commands.put(CommandName.SAVE_NEWS, new SaveNews());
		commands.put(CommandName.GO_TO_ADD_NEWS, new GoToAddNews());
		commands.put(CommandName.GO_TO_EDIT_NEWS, new GoToEditNews());
		commands.put(CommandName.DELETE_NEWS, new DeleteNews());
		commands.put(CommandName.GO_TO_CONFIRMATION_PAGE, new GoToConfirmationPage());
		commands.put(CommandName.NO_SUCH_FUNCTION, new NoSuchFunction());
		commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
		commands.put(CommandName.UPDATE_NEWS, new UpdateNews());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPage());
	    commands.put(CommandName.CHANGE_LOCALE, new ChangeLocale());
	}
	public Command getCommand(String name) {
		try {
			CommandName commandName = CommandName.valueOf(name.toUpperCase());
			Command command = commands.get(commandName);
			return command;
		} catch (NoSuchElementException e) {
			return commands.get(CommandName.valueOf(ERROR_COMMAND));
		}
	}
}
