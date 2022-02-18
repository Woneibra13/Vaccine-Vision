package application;
public class User implements Comparable<User>{
    private String name = null, region = null, email = null;
    private int age = 0, conditions=0;

    /*Empty constructor defaults all fields to null
    This should not be used*/
    public User() {
        age = 0;
        name = region = null;
    }

    public User(String name, int age, String region, String email) {
        this.name = name;
        this.age = age;
        this.region = region;
        this.email = email;
    }

    public int getReqs() {
        return age + conditions;
    }

    public void setReqs(int age, int conditions) {
        this.age = age;
        this.conditions = conditions;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail () {
        return email;
    }

    public int compareTo(User that){
        int priority = that.getReqs() - this.getReqs();
        if (priority<0)
            return 1;
        if (priority>0)
            return -1;
        else
            return 0;
    }

    public boolean equals(Object o){
        User that = (User) o;
        return this.compareTo(that) == 0;
    }
}