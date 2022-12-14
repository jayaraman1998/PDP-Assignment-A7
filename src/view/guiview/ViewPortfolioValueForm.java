package view.guiview;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import controller.guicontroller.Features;

/**
 * Class that contains the form to view a specific portfolio's value on a date.
 */
public class ViewPortfolioValueForm extends AbstractFrame {

  private final JSpinner dateSpinner;

  /**
   * Constructs the portfolio value frame with the following parameters.
   *
   * @param portfolioNames list of existing portfolios
   * @param portfolioName  given portfolio's name
   * @param dateString     given date as string
   * @param valueOnDate    value on given date
   */
  public ViewPortfolioValueForm(String[] portfolioNames, String portfolioName,
                                String dateString, String valueOnDate) {
    super("Get Portfolio Value On A Date");

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    this.add(createPortfoliosListRadio(portfolioNames));

    // enter value date
    JLabel valueDatePrompt = new JLabel("Enter date:");
    formPanel.add(valueDatePrompt);

    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    formPanel.add(dateSpinner);

    // display result value if passed
    if (dateString.length() > 0 && portfolioName.length() > 0 && valueOnDate.length() > 0) {
      JLabel valueResultStatement = new JLabel("Value of Portfolio: "
              + portfolioName
              + " on Date: "
              + dateString
              + ", is: $"
              + valueOnDate
      );
      formPanel.add(valueResultStatement);
    }

    // submit button
    submitButton = new JButton("Submit");
    submitButton.setActionCommand("Submit");
    formPanel.add(submitButton);

    // exit button
    backButton = new JButton("Back");
    backButton.setActionCommand("Back");
    formPanel.add(backButton);

    this.add(formPanel);

    pack();
    setVisible(true);

  }

  @Override
  public void addFeatures(Features features) {
    submitButton.addActionListener(evt -> {
      if (rGroup.getSelection() == null) {
        displayErrorMessage("Please select a portfolio first!");
      } else {
        this.setVisible(false);
        features.viewPortfolioValueAtDate(
                getRadioButtonSelection(),
                getDateSpinnerValue(dateSpinner)
        );
      }
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }
}
