package com.example.Final.service.Property.CommandPattern;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {
    private List<ICommand> commandQueue = new ArrayList<>();

    public void addCommand(ICommand command){
        commandQueue.add(command);
    }

    public void executeCommand(){
        for(ICommand command : commandQueue){
            command.execute();
        }
        commandQueue.clear();
    }
}
