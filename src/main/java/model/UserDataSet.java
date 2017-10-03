package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

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


    public UserDataSet(String name, Integer age) {
        this(null, name, age);
    }

    public UserDataSet(Long id, String name, Integer age) {
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

    @Column(name = "address_id")
    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }


    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        for(PhoneDataSet phone : phones){
            phone.setUser(this);
        }
        this.phones = phones;
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
