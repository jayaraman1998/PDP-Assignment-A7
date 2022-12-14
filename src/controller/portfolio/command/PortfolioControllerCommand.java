package controller.portfolio.command;

import java.io.IOException;
import java.util.Scanner;

/**
 * Represents an interface which defines each of the commands executed by the controller.
 */
public interface PortfolioControllerCommand {
  /**
   * Method that contains the controller command implementation.
   */
  void goCommand(Scanner scan) throws IOException;
}
