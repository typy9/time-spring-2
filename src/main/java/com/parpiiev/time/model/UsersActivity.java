package com.parpiiev.time.model;

import javax.persistence.*;

@Table(name = "users_activities")
@Entity
public class UsersActivity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable=false) // insertable = false, updatable = false
    private User user;

    @ManyToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "activity_id", nullable=false) // insertable = false, updatable = false
    private Activity activity;

    @Column(name = "time")
    private int time;

    public UsersActivity() {
    }

    /**
     * UsersActivity constructor used for testing with param:
     * @param user User associated with UserActivity
     * @param activity Activity associated with UserActivity
     * @param time time for UserActivity
     */
    public UsersActivity(int id, User user, Activity activity, int time) {
        this.id = id;
        this.user = user;
        this.activity = activity;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "\n UsersActivity{" +
                "id=" + id +
                ", user=" + user +
                ", activity=" + activity +
                ", time=" + time +
                '}';
    }
}
