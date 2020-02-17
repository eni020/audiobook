import java.util.Comparator;

public class Rating {

    private int userid;
    private int bookid;
    private double rate;

    public Rating(int userid, int bookid, double rate) {
        this.userid = userid;
        this.bookid = bookid;
        this.rate = rate;
    }

    public int getUserid() {
        return userid;
    }

    public int getBookid() {
        return bookid;
    }

    public double getRate() {
        return rate;
    }

    static class RateDescComparator implements Comparator<Rating> {

        @Override
        public int compare(Rating r1, Rating r2) {

            return (int)r2.rate - (int)r1.rate;
        }
    }

    static class BookComparator implements Comparator<Rating> {

        @Override
        public int compare(Rating r1, Rating r2) {

            return r1.bookid - r2.bookid;
        }
    }

}
