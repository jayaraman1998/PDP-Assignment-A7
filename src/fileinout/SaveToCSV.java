package fileinout;

/**
 * This class has methods that will save the given data into CSV file.
 * The class extends AbstractFileIo, an abstract class that implements the file-inout.
 * FileIO interface.
 */
public class SaveToCSV extends AbstractFileIO {

  /**
   * The Method constructs a CSV file type FileIO.
   */
  public SaveToCSV() {
    super("csv");
  }

  @Override
  protected String formatData(Object data) {
    return data.toString();
  }
}
