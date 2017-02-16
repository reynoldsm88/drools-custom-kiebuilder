package com.redhat.model;

public class BasicFactB {
    private int id;
    private String str;
    private double dbl;

    public BasicFactB( int id, String str, double dbl ) {
        super();
        this.id = id;
        this.str = str;
        this.dbl = dbl;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr( String str ) {
        this.str = str;
    }

    public double getDbl() {
        return dbl;
    }

    public void setDbl( double dbl ) {
        this.dbl = dbl;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits( dbl );
        result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
        result = prime * result + id;
        result = prime * result + ( ( str == null ) ? 0 : str.hashCode() );
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
        BasicFactB other = (BasicFactB) obj;
        if ( Double.doubleToLongBits( dbl ) != Double.doubleToLongBits( other.dbl ) )
            return false;
        if ( id != other.id )
            return false;
        if ( str == null ) {
            if ( other.str != null )
                return false;
        } else if ( !str.equals( other.str ) )
            return false;
        return true;
    }

}
