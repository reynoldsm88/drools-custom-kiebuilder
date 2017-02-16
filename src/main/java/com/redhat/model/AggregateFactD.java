package com.redhat.model;

import java.util.ArrayList;
import java.util.Collection;

public class AggregateFactD {
    private Collection<CompositeFactC> facts = new ArrayList<CompositeFactC>();

    public Collection<CompositeFactC> getFacts() {
        return facts;
    }

    public void addFact( CompositeFactC fact ) {
        this.facts.add( fact );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( facts == null ) ? 0 : facts.hashCode() );
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
        AggregateFactD other = (AggregateFactD) obj;
        if ( facts == null ) {
            if ( other.facts != null )
                return false;
        } else if ( !facts.equals( other.facts ) )
            return false;
        return true;
    }

}
