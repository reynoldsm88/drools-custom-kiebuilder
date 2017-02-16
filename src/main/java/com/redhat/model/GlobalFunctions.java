package com.redhat.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalFunctions {

    private long numEval = 0;
    private static final Logger LOG = LoggerFactory.getLogger( GlobalFunctions.class );

    public boolean evaluate( Object o ) {
        LOG.info( "executing global function on object " + o.toString() );
        numEval++;
        return o != null;
    }

    public long getEvals() {
        return this.numEval;
    }

}
