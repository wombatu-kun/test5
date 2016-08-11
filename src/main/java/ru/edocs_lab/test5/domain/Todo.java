package ru.edocs_lab.test5.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="TODO_LIST")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private long id;

    @Basic(optional = false)
    @Column(name="CREATED", updatable=false, insertable=false, columnDefinition="DATE DEFAULT CURRENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date created;

    @Column(name="WHAT", length=128)
    private String what;

    @Column(name="WHEN")
    @Temporal(TemporalType.DATE)
    private Date when;

    public Todo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }
}
