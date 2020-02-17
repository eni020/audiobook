import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    int id;
    List<Rating> ratings;
    double avgrate;

    public User(int id, List<Rating> ratings) {
        this.id = id;
        this.ratings = new ArrayList<>();
        for (Rating r: ratings) {
            if (r.getUserid() == this.id) {
                this.ratings.add(r);
            }
        }
    }

    public int getId() {
        return id;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

}
