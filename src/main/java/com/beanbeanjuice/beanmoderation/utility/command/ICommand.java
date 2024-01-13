package com.beanbeanjuice.beanmoderation.utility.command;

import org.bukkit.command.CommandExecutor;

import java.util.HashMap;

public interface ICommand extends CommandExecutor {

    String getName();
    HashMap<String, ISubCommand> getSubCommands();

}
