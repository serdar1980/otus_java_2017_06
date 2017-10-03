package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet{
    private String street;

    public AddressDataSet() {

    }
    public AddressDataSet( String street) {
        this(null, street);
    }

    public AddressDataSet(Long id, String street) {
        super(id);
        this.street = street;
    }

    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
