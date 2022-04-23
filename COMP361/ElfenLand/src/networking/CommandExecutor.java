package networking;

import java.io.ObjectInputStream;
import java.lang.Runnable;
// Start of user code for imports
// End of user code

import commands.AllPlayersAddedCommand;
import commands.PlaceCounterCommand;
import commands.RemoteCommand;
import commands.SetupRoundCommand;
import commands.StartDrawFaceUpCounterPhaseCommand;
import commands.TestCommand;

/**
 * CommandExecutor class definition.
 * Generated by the TouchCORE code generator.
 */

public class CommandExecutor implements Runnable {
    
    protected ObjectInputStream myInputStream;
    
    public CommandExecutor(ObjectInputStream in) {
        this.myInputStream = in;
    }

    public void run() {
        try {
            while (true) {
            	System.out.println("here @ command executor");
                Object readObject = myInputStream.readObject();
                RemoteCommand command = (RemoteCommand) readObject;
                
                System.out.println("i got a "+ command.getClass().toString());
                
                if (command instanceof TestCommand)
                {
                	System.out.println("Just got a TestCommand command");
                	
                	SingleCommandExecutor plswork = new SingleCommandExecutor(command);
                    Thread thread = new Thread(plswork);
                    
                    System.out.println("created the new singlecommand executor and starting it up now");
                    thread.start();
                    
                    System.out.println("THIS PRINTS AFTER THE SINGLE COMMAND EXECUTOR THREAD.START SO IT MUST HAVE FINISHED");
                }
                else if (command.getClass() == AllPlayersAddedCommand.class) {
                	
                	System.out.println("Just got a allplayersadded command");
                	
                	SingleCommandExecutor plswork = new SingleCommandExecutor(command);
                    Thread thread = new Thread(plswork);
                    
                    System.out.println("created the new singlecommand executor and starting it up now");
                    thread.start();
                    
                    System.out.println("THIS PRINTS AFTER THE SINGLE COMMAND EXECUTOR THREAD.START SO IT MUST HAVE FINISHED");
                }
                else if (command.getClass() == PlaceCounterCommand.class) {
                	
                	System.out.println("Just got a place counter command command");
                	
                	SingleCommandExecutor plswork = new SingleCommandExecutor(command);
                    Thread thread = new Thread(plswork);
                    
                    System.out.println("created the new singlecommand executor and starting it up now");
                    thread.start();
                    
                    System.out.println("THIS PRINTS AFTER THE SINGLE COMMAND EXECUTOR THREAD.START SO IT MUST HAVE FINISHED");
                }
                
                else if (command.getClass() == StartDrawFaceUpCounterPhaseCommand.class) {
                	
                	System.out.println("Just got a start draw face up counter phase command");
                	
                	SingleCommandExecutor plswork = new SingleCommandExecutor(command);
                    Thread thread = new Thread(plswork);
                    
                    System.out.println("created the new singlecommand executor and starting it up now");
                    thread.start();
                    
                    System.out.println("THIS PRINTS AFTER THE SINGLE COMMAND EXECUTOR THREAD.START SO IT MUST HAVE FINISHED");
                                        
                    System.out.println("THIS PRINTS AFTER THE SINGLE COMMAND EXECUTOR THREAD.INTERRUPT SO IT MUST HAVE FINISHED");
                }
                
                else {
                	System.out.println("executing command now");
                    command.execute();
                    System.out.println("command executed!");
                }
                
                
            }
        } catch (ClassNotFoundException e) {
            
        } catch (java.io.IOException e) {
            
        }
    }

    public ObjectInputStream getMyInputStream() {
        return this.myInputStream;
    }

    public boolean setMyInputStream(ObjectInputStream newObject) {
        this.myInputStream = newObject;
        return true;
    }
}
