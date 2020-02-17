import java.util.*;

public class Main {

    static List<Rating> ratings = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static int ratingNum = 0;
    static int userNum = 0;
    static int bookNum = 0;
    static double[][] R, P, Q, E;
    static int K;

    public static void input() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        String[] input = line.split("\\s+");
        ratingNum = Integer.parseInt(input[0]);
        userNum = Integer.parseInt(input[1]);
        bookNum = Integer.parseInt(input[2]);

        for(int i = 0; i < ratingNum; i++) {
            line = in.nextLine();
            input = line.split("\\s+");
            int u = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);
            double r = Integer.parseInt(input[2]);
            Rating rating = new Rating(u, b, r);
            ratings.add(rating);
        }

        for(int i = 0; i < userNum; i++) {
            User user = new User(i, ratings);
            users.add(user);
        }
    }
    
    public static void matrixinit() {
        R = new double[userNum][bookNum];

        for(User u: users) {
            List<Rating> ur = u.getRatings();
            Collections.sort(ur, new Rating.BookComparator());
            for(Rating r: ur) {
                R[u.getId()][r.getBookid()] = r.getRate();
            }
        }

//        K = userNum < bookNum ? userNum : bookNum;
//        K /= 10;
//        if(K < 2) {
//            K = 2;
//        }

        K=30;
        P = new double[userNum][K];
        Q = new double[K][bookNum];

        for (int i = 0; i < userNum; i++) {
            for (int j = 0; j < K; j++) {
                P[i][j] = new Random().nextDouble();
            }
        }

        for (int i = 0; i < K; i++) {
            for (int j = 0; j < bookNum; j++) {
                Q[i][j] = new Random().nextDouble();
            }
        }

    }

    public static void calcE() {
        E = new double[userNum][bookNum];
        for (int i = 0; i < userNum; i++) {
            for (int j = 0; j < bookNum; j++) {
                if (R[i][j] != 0) {
                    for (int k = 0; k < K; k++) {
                        E[i][j] += P[i][k] * Q[k][j];
                    }
                    E[i][j] = R[i][j] - E[i][j];
                }
            }
        }
    }

    public static void modPQ() {
        for (int i = 0; i < userNum; i++) {
            for (int j = 0; j < bookNum; j++) {
                if (R[i][j] != 0) {
                    for (int k = 0; k < K; k++) {
                        double p = P[i][k];
                        double e = E[i][j];
                        double q = Q[k][j];

                        P[i][k] = p + 0.0002 * e * q;
                        Q[k][j] = q + 0.0002 * e * p;
                    }
                }
            }
        }
        calcE();
    }
    public static void main(String[] args) {

        input();
        matrixinit();

        double sum = 10001;

        int q = 10;
        while (sum > 10000) {
            sum =0;
            calcE();
            modPQ();
            for (int i = 0; i < userNum; i++) {
                for (int j = 0; j < bookNum; j++) {
                    if (R[i][j] != 0){
                        double temp = E[i][j];
                        temp = Math.abs(temp);
                        sum += temp;
                    }
                }
            }
        }

        List<Rating> predrates = new ArrayList<>();
        List<User> predusers = new ArrayList<>();

        double[][] RA = new double[userNum][bookNum];
        for (int i = 0; i < userNum; i++) {
            for (int j = 0; j < bookNum; j++) {
                if (R[i][j] == 0) {
                    for (int k = 0; k < K; k++) {
                        RA[i][j] += P[i][k] * Q[k][j];
                    }
                    RA[i][j] = Math.round(RA[i][j]);
                    Rating rating = new Rating(i, j, RA[i][j]);
                    predrates.add(rating);
                }
            }
        }

        for(int i = 0; i < userNum; i++) {
            User user = new User(i, predrates);
            predusers.add(user);
        }
        
        double[][] recomm = new double[userNum][10];

        for(User u: predusers) {
            int i = 0;
            List<Rating> ur = u.getRatings();
            Collections.sort(ur, new Rating.RateDescComparator());
            for(Rating r: ur) {
                recomm[u.getId()][i++] = r.getBookid();
                if (i == 10) {
                    break;
                }
            }
        }


        for (int i = 0; i < userNum; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(recomm[i][j]);
                if (j < 10-1)
                    System.out.print("\t");
                else if (i < userNum-1)
                    System.out.println();
            }
        }

    }
}
