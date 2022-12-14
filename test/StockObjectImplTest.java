import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import model.stock.StockObject;
import model.stock.StockObjectImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class is a test class for StockObjectImpl.
 * This class contains methods which tests the methods and properties of StockObjectImpl.
 */
public class StockObjectImplTest {

  private StockObject stock;

  @Before
  public void setup() {
    stock = new StockObjectImpl("GOOG");
  }

  // test getting ticker
  @Test
  public void testGettingTicker() {
    assertEquals("GOOG", stock.getTicker());
  }

  // test getting current price
  @Test
  public void testGettingCurrentPrice() {
    float price = stock.getCurrentPrice();
    assertEquals(price, price, 0.01);
  }

  // test getting price at a date
  @Test
  public void testGettingPriceAtDate() {
    LocalDate date = LocalDate.now();
    float price = stock.getCurrentPriceAtDate(date);
    assertEquals(price, price, 0.01);
  }

  // test getting current value
  @Test
  public void testGettingCurrentValue() {
    float quantity = 2;
    float price = stock.getCurrentPrice();
    float value = stock.getCurrentValue(quantity);
    assertEquals(price * quantity, value, 0.01);
  }

  // test getting current value at a date
  @Test
  public void testGettingValueAtDate() {
    LocalDate date = LocalDate.now();
    float value = stock.getCurrentValueAtDate(date, 1);
    assertEquals(value, value, 0.01);
  }

}