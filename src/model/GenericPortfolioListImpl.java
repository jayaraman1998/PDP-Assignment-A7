package model;

import model.flexibleportfolio.FlexiblePortfolioList;
import model.flexibleportfolio.FlexiblePortfolioListImpl;
import model.portfolio.PortfolioList;
import model.portfolio.PortfolioListImpl;

/**
 * This class implements the GenericPortfolioList interface.
 * This class acts as an implementation abstraction layer for PortfolioList and
 * FlexiblePortfolioList Implementations.
 */
public class GenericPortfolioListImpl implements GenericPortfolioList {

  private final FlexiblePortfolioList flexiblePortfolioList;

  public GenericPortfolioListImpl() {
    flexiblePortfolioList = new FlexiblePortfolioListImpl();
  }

  @Override
  public PortfolioList getPortfolioList() {
    return new PortfolioListImpl();
  }

  @Override
  public FlexiblePortfolioList getFlexiblePortfolioList() {
    return flexiblePortfolioList;
  }

}
