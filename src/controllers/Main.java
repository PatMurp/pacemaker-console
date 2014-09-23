package controllers;

import java.io.IOException;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

public class Main
{
  @Command
  public void createUser (String firstName,  String lastName, 
                          String email,      String password)
  {
  }

  @Command
  public void getUser (String email)
  {

  }

  @Command
  public void getUsers ()
  {

  }

  @Command
  public void deleteUser (String email)
  {
  }

  public static void main(String[] args) throws IOException
  {
    Shell shell = ShellFactory.createConsoleShell("pc", "Welcome to pcemaker-console - ?help for instructions", new Main());
    shell.commandLoop(); 
  }
}
