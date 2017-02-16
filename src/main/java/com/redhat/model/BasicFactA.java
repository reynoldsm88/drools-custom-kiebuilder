package com.redhat.model;

public class BasicFactA {

    private int num;
    private String str;

    public BasicFactA( int num, String str ) {
        super();
        this.num = num;
        this.str = str;
    }

    public int getNum() {
        return num;
    }

    public void setNum( int num ) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr( String str ) {
        this.str = str;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + num;
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
        BasicFactA other = (BasicFactA) obj;
        if ( num != other.num )
            return false;
        if ( str == null ) {
            if ( other.str != null )
                return false;
        } else if ( !str.equals( other.str ) )
            return false;
        return true;
    }

}
