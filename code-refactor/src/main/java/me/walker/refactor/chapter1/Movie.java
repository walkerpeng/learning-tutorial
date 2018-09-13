package me.walker.refactor.chapter1;

public class Movie {
    private Price price;

    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String title;
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        setPriceCode(priceCode);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriceCode() {
        return price.getPriceCode();
    }

    public void setPriceCode(int priceCode) {
        switch (priceCode) {
            case REGULAR:
                price = new RegularPrice();
                break;
            case CHILDRENS:
                price = new ChildrenPrice();
                break;
            case NEW_RELEASE:
                price = new NewReleasePrice();
                break;
                default:
                    throw new IllegalArgumentException("Incorrect Price Code");
        }
    }

    public double getCharge(int daysRented){
        return price.getCharge(daysRented);
    }

    int getFrequentRenterPoints(int daysRented){
        return price.getFrequentRenterPoints(daysRented);
    }
}
