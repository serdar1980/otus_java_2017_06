package model;

import javax.persistence.Column;

public class DataSet {
    private long id;

    public DataSet() {
    }

    public DataSet(Long id) {
        this.id = id;
    }

    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
