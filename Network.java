/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) return users[i];
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (userCount == users.length) return false;
        if (getUser(name) != null) return false;
        System.out.println("\n...Adding User " + name + "...");
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (getUser(name1) == null) return false;
        if (getUser(name2) == null) return false;
        // User temp = new User("temp");
        // temp = getUser(name1);
        return getUser(name1).addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        User mostRecommendedUserToFollow = null;
        int mostMutualNum = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i] == user) continue;
            int currMutual = user.countMutual(users[i]);
            if (currMutual > mostMutualNum) {
                mostRecommendedUserToFollow = users[i];
                mostMutualNum = currMutual;
            }
        }
        return mostRecommendedUserToFollow.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0) return null;
        String mostPop = users[0].getName();
        int mostFollowers = followeeCount(mostPop);
        for (int i = 0; i < userCount; i++) {
            String currName = users[i].getName();
            int currFollowers = followeeCount(currName);
            if (currFollowers > mostFollowers) {
                mostPop = currName;
                mostFollowers = currFollowers;
            }
        }
        return mostPop;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       String resString = "Network:";
       for (int i = 0; i < userCount; i++) {
            resString += "\n" + users[i].toString();
       }
       return resString;
    }
}
