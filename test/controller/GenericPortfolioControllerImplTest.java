package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import model.GenericPortfolioListImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class is a test class for GenericPortfolioControllerImpl class.
 * This class contains methods that test the GenericPortfolioControllerImpl methods.
 */
public class GenericPortfolioControllerImplTest {

  private String menuString;

  @Before
  public void setUp() {
    menuString = "\n\n-------------------------------"
            + "\nWelcome!\nWhat kind of portfolios do you want to work with? Press option key:\n\n"
            + "1. Flexible Portfolio\n"
            + "2. Inflexible Portfolio\n"
            + "3. Exit\n"
            + "-------------------------------\n\n";
  }

  @Test
  public void testMenuExit() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3");
    GenericPortfolioControllerImpl controller = new GenericPortfolioControllerImpl(in, out);
    controller.goGenericController(new GenericPortfolioListImpl());
    assertEquals(menuString, out.toString());
  }
}