package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {
    @Column(name = "id")
    private String name;

    private Date lastDateUse;
    private int age;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PhoneDataSet> phones = new ArrayList<>();


    public UserDataSet() {
    }

    public UserDataSet(long id, String name, Integer age) {
        super(id);
        this.name = name;
        this.lastDateUse = new Date();
        this.age = age;
    }

    @Column(name = "user_name")
    public String getName() {
        this.lastDateUse = new Date();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Transient
    public Date getLastDateUse() {
        return lastDateUse;
    }

    public void setLastDateUse(Date lastDateUse) {
        this.lastDateUse = lastDateUse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserDataSet ");
        sb.append("Id :").append(this.getId())
                .append("Name :").append(this.getName())
                .append("Age :").append(this.getAge());
        return sb.toString();
    }
}
