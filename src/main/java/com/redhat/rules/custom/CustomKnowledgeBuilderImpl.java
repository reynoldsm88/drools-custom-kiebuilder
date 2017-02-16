package com.redhat.rules.custom;

import org.drools.compiler.builder.impl.CustomCompositeKnowledgeBuilderImpl;
import org.drools.compiler.builder.impl.KnowledgeBuilderConfigurationImpl;
import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.internal.builder.CompositeKnowledgeBuilder;
import org.kie.internal.builder.ResourceChange.Type;

public class CustomKnowledgeBuilderImpl extends KnowledgeBuilderImpl {

    public CustomKnowledgeBuilderImpl() {
        super();
    }

    @Override
    public CompositeKnowledgeBuilder batch() {
        return new CustomCompositeKnowledgeBuilderImpl( this );
    }

    public CustomKnowledgeBuilderImpl( KnowledgeBuilderConfigurationImpl conf ) {
        super( conf );
    }

    public CustomKnowledgeBuilderImpl( InternalKnowledgeBase kbase ) {
        super( kbase );
    }

    public CustomKnowledgeBuilderImpl( InternalKnowledgeBase kbase, KnowledgeBuilderConfigurationImpl conf ) {
        super( kbase, conf );
    }
}