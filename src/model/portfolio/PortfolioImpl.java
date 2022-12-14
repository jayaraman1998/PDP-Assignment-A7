package model.portfolio;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fileinout.FileIO;
import fileinout.SaveToCSV;
import model.stock.StockObject;

/**
 * This class implements the interface Portfolio.
 * This class has the list of stock objects.
 */
public class PortfolioImpl implements Portfolio {

  protected final String portfolioName;
  protected final Map<String, PortfolioItem> stocks;

  protected final String portfolioFileName;
  protected final String currentDirectory;

  protected boolean fileSaved;

  /**
   * This method constructs a builder object that can be used to build the PortfolioImpl.
   *
   * @return returns a PortfolioBuilder Object.
   */
  public static PortfolioBuilder getBuilder() {
    return new PortfolioBuilder();
  }

  protected PortfolioImpl(String portfolioName, String portfolioFileName, String currentDirectory) {
    this.currentDirectory = currentDirectory;

    this.portfolioName = portfolioName;

    this.stocks = new HashMap<>();

    if (portfolioFileName == null) {
      this.portfolioFileName = portfolioName + "_" + new Date().getTime() + ".csv";
    } else {
      this.portfolioFileName = portfolioFileName;
      fileSaved = true;
    }
  }

  private PortfolioImpl(String portfolioName, Map<String, PortfolioItem> stocks,
                        String portfolioFileName) {

    this.currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";

    this.portfolioName = portfolioName;

    this.stocks = new HashMap<>(stocks);

    if (portfolioFileName == null) {
      this.portfolioFileName = portfolioName + "_" + new Date().getTime() + ".csv";
    } else {
      this.portfolioFileName = portfolioFileName;
      fileSaved = true;
    }
  }

  /**
   * This class is builder class that is used to build the Portfolio.
   * To build the PortfolioImpl Object, one must use this PortfolioBuilder Class.
   */
  public static class PortfolioBuilder {

    private String portfolioName;
    private final Map<String, PortfolioItem> stocks;

    private String portfolioFileName;

    private PortfolioBuilder() {
      stocks = new HashMap<>();
      portfolioFileName = null;
    }

    /**
     * This method will set the Portfolio name that will be used to build the Portfolio class.
     *
     * @param portfolioName the name of the portfolio.
     * @return returns the current PortfolioBuilder object.
     */
    public PortfolioBuilder portfolioName(String portfolioName) {
      this.portfolioName = portfolioName;
      return this;
    }

    /**
     * This method will set the file path where the created portfolio will be saved.
     *
     * @param filepath the filepath where the portfolio needs to be saved.
     * @return returns the current PortfolioBuilder object.
     */
    public PortfolioBuilder setPortfolioFileName(Path filepath) {
      if (filepath != null) {
        this.portfolioFileName = filepath.getFileName().toString();
      }
      return this;
    }

    /**
     * This method will add a stock to the PortfolioBuilder stocks. And this stocks will be
     * used to build the PortfolioImpl Object.
     *
     * @param stock    the stockObject that needs to be added to the Portfolio.
     * @param quantity the quantity of the stock that was purchased/invested.
     * @return returns the current PortfolioBuilder Object.
     * @throws IllegalArgumentException if the given quantity value is not valid.
     */
    public PortfolioBuilder addStockToPortfolio(StockObject stock, float quantity)
            throws IllegalArgumentException {

      if (quantity <= 0) {
        throw new IllegalArgumentException("The quantity value must be greater than zero.");
      }

      stocks.put(stock.getTicker(), new PortfolioItem(stock, quantity, stock.getCurrentPrice()));

      return this;
    }

    /**
     * This method will create the PortfolioImpl Object with the updated PortfolioBuilder data
     * that were set using PortfolioBuilder methods. The method returns a PortfolioImpl object that
     * is implementing the Portfolio Interface.
     *
     * @return returns a PortfolioImpl Object that is implementing Portfolio interface.
     */
    public Portfolio build() {
      return new PortfolioImpl(this.portfolioName, stocks, this.portfolioFileName);
    }


  }

  @Override
  public String getPortfolioName() {
    return this.portfolioName;
  }

  @Override
  public String getPortfolioFilePath() {
    return this.fileSaved
            ? this.getSaveFilePath().toString()
            : "File Could not be Saved.";
  }

  @Override
  public PortfolioItem[] getPortfolioComposition() {
    PortfolioItem[] portfolioItems = new PortfolioItem[]{};
    return stocks.values().toArray(portfolioItems);
  }

  @Override
  public float getPortfolioValue() {
    return stocks
            .values()
            .stream()
            .map(PortfolioItem::getCurrentValue)
            .reduce((float) 0, Float::sum);
  }

  @Override
  public float getPortfolioValueAtDate(LocalDate date) throws IllegalArgumentException {
    if (date.compareTo(LocalDate.now()) > 0) {
      throw new IllegalArgumentException("Cannot get the Portfolio value for the future date.");
    }
    return stocks
            .values()
            .stream()
            .map(portfolioItem -> portfolioItem
                    .getStock()
                    .getCurrentValueAtDate(date, portfolioItem.getQuantity()))
            .reduce((float) 0, Float::sum);
  }

  protected Path getSaveFilePath() {
    FileIO fileIO = new SaveToCSV();
    return fileIO.getFilePathFromString(currentDirectory + portfolioFileName);
  }

  @Override
  public String savePortfolioToFile() throws FileNotFoundException {
    Path filepath = getSaveFilePath();

    PortfolioItem[] items = stocks.values().toArray(new PortfolioItem[0]);

    FileIO fileIO = new SaveToCSV();

    String path = fileIO.writeData(filepath.toString(), items);

    this.fileSaved = true;
    return path;
  }
}
