package com.parpiiev.time.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name="activities")
@Entity
public class Activity {

    @Id
    @Column(name = "activity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="activity_category_id", nullable=false)
    Category category;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.MERGE)
    private List<UsersActivity> userActivityList;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.MERGE)
    private List<ActivityRequest> requestList;

    public Activity() {
    }

    /**
     * Activity constructor used for testing with param:
     * @param name Activity name
     * @param category category id
     */
    public Activity(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public List<ActivityRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<ActivityRequest> requestList) {
        this.requestList = requestList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer activity_id) {
        this.id = activity_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<UsersActivity> getUserActivityList() {
        return userActivityList;
    }

    public void setUserActivityList(List<UsersActivity> userActivityList) {
        this.userActivityList = userActivityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(name, activity.name) && Objects.equals(category, activity.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category);
    }

    @Override
    public String toString() {
        return "\n Activity{" +
                "activity_id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
