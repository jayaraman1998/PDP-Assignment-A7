package controller.flexibleportfolio.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

import model.flexibleportfolio.FlexiblePortfolioList;
import view.flexibleportfolio.FlexiblePortfolioView;

/**
 * Contains command implementation to get flexible portfolio's value on a date.
 */
public class GetFlexiblePortfolioValueOnDate implements FlexiblePortfolioControllerCommand {

  private final FlexiblePortfolioList fpList;
  private final FlexiblePortfolioView view;

  /**
   * Constructs object to view flexible portfolio's value on a date.
   * @param fpList given model object
   * @param view given view object
   */
  public GetFlexiblePortfolioValueOnDate(FlexiblePortfolioList fpList, FlexiblePortfolioView view) {
    this.fpList = fpList;
    this.view = view;
  }

  private boolean validPortfolioName(String[] pNamesList, String pName) {
    return Arrays.asList(pNamesList).contains(pName);
  }

  private boolean toContinue(String flag) {
    if ((flag.equals("N") || flag.equals("n"))) {
      return true;
    } else {
      return !flag.equals("Y") && !flag.equals("y");
    }
  }

  @Override
  public void goCommand(Scanner scan) throws IOException {
    // get portfolio value on a date
    String[] pNames = fpList.getPortfolioListNames();
    if (pNames.length < 1) {
      view.noPortfoliosMessage();
      return;
    }
    view.displayListOfPortfolios(pNames);

    while (true) {
      view.portfolioNamePrompt();
      String pName = scan.next().toLowerCase();
      if (pName.equals("0")) {
        break;
      }

      if (!validPortfolioName(fpList.getPortfolioListNames(), pName)) {
        view.portfolioNameErrorMessage();
        continue;
      }

      view.datePrompt();
      String dateString = scan.next();
      if (dateString.equals("0")) {
        break;
      }

      LocalDate date;
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        date = LocalDate.parse(dateString, formatter);
      } catch (Exception e) {
        view.invalidDateStringMessage(dateString);
        continue;
      }

      float value;
      try {
        value = fpList.getPortfolio(pName).getPortfolioValueAtDate(date);
      } catch (IllegalArgumentException e) {
        view.displayErrorPrompt("Flexible Portfolio Value on Date Failed! Error: " + e);
        continue;
      }

      view.displayValueAtDate(pName, date, value);

      view.continuePrompt();
      if (toContinue(scan.next())) {
        break;
      }
    }
  }
}
