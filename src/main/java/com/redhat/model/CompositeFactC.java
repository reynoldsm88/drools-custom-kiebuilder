package com.redhat.model;

public class CompositeFactC {
    private int id;
    private BasicFactA factA;
    private BasicFactB factB;

    public CompositeFactC( int id, BasicFactA factA, BasicFactB factB ) {
        super();
        this.id = id;
        this.factA = factA;
        this.factB = factB;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public BasicFactA getFactA() {
        return factA;
    }

    public void setFactA( BasicFactA factA ) {
        this.factA = factA;
    }

    public BasicFactB getFactB() {
        return factB;
    }

    public void setFactB( BasicFactB factB ) {
        this.factB = factB;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( factA == null ) ? 0 : factA.hashCode() );
        result = prime * result + ( ( factB == null ) ? 0 : factB.hashCode() );
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        CompositeFactC other = (CompositeFactC) obj;
        if ( factA == null ) {
            if ( other.factA != null )
                return false;
        } else if ( !factA.equals( other.factA ) )
            return false;
        if ( factB == null ) {
            if ( other.factB != null )
                return false;
        } else if ( !factB.equals( other.factB ) )
            return false;
        if ( id != other.id )
            return false;
        return true;
    }

}
