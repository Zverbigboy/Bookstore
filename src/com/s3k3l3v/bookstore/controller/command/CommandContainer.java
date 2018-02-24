package com.s3k3l3v.bookstore.controller.command;


import org.apache.log4j.Logger;


import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger logger = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<>();

    static{
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("listBook", new ListBookCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("addBook", new AddBookCommand());
        commands.put("updateBook", new UpdateBookCommand());
        commands.put("adminMenu", new AdminMenuCommand());

        commands.put("addUser", new AddUserCommand());
        commands.put("updateUser", new UpdateUserCommand());

        commands.put("orderBook", new OrderBookCommand());
        commands.put("listOrderBook", new ListOrderBookCommand());

        commands.put("listOrderBookForLibrarian", new ListOrderBookForLibrarianCommand());

        commands.put("search", new SearchCommand());

        commands.put("sortByParameter", new SortByParameterCommand());

        commands.put("listUnregistered", new ListUnregistered());

        commands.put("listBookClient", new ListBookClientCommand());

        commands.put("giveAnOrder", new GiveAnOrderCommand());

        commands.put("personalCommand", new PersonalCommand());

        logger.debug("command container was successfully initialized");
        logger.trace("NUmber of commands ==> " + commands.size());
    }

    public static Command get(String commName){
        if (commName == null || !commands.containsKey(commName)){
            logger.trace("Command not found, name ==> " + commName);
            return commands.get("noCommand");
        }
        return commands.get(commName);
    }

}
