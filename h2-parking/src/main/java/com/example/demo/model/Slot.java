package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Slot
{
    public static final class Type
    {
        public static final int SMALL   = 1;
        public static final int COMPACT = 2;
        public static final int LARGE   = 3;
    }

    @Id
    @GeneratedValue
    private Long    id;
    private Integer type;
    private Boolean occupied;

    public Slot()
    {
    }

    public Slot(Integer type, Boolean occupied)
    {
        this.type = type;
        this.occupied = occupied;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Boolean getOccupied()
    {
        return occupied;
    }

    public void setOccupied(Boolean occupied)
    {
        this.occupied = occupied;
    }
}
