package com.redhat.rules.custom;

import org.drools.compiler.builder.impl.KnowledgeBuilderConfigurationImpl;
import org.drools.compiler.builder.impl.KnowledgeBuilderFactoryServiceImpl;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderConfiguration;
import org.kie.internal.builder.KnowledgeBuilderFactoryService;
import org.kie.internal.utils.KieService;

public class CustomKnowledgeBuilderFactory extends KnowledgeBuilderFactoryServiceImpl implements KieService {

    public KnowledgeBuilder newKnowledgeBuilder() {
        return new CustomKnowledgeBuilderImpl();
    }

    public KnowledgeBuilder newKnowledgeBuilder( KnowledgeBuilderConfiguration conf ) {
        return new CustomKnowledgeBuilderImpl( (KnowledgeBuilderConfigurationImpl) conf );
    }

    public KnowledgeBuilder newKnowledgeBuilder( KnowledgeBase kbase ) {
        if ( kbase != null ) {
            return new CustomKnowledgeBuilderImpl( (InternalKnowledgeBase) kbase );
        }
        else {
            return new CustomKnowledgeBuilderImpl();
        }
    }

    public KnowledgeBuilder newKnowledgeBuilder( KnowledgeBase kbase, KnowledgeBuilderConfiguration conf ) {
        if ( kbase != null ) {
            return new CustomKnowledgeBuilderImpl( (InternalKnowledgeBase) kbase, (KnowledgeBuilderConfigurationImpl) conf );
        }
        else {
            return new CustomKnowledgeBuilderImpl( (KnowledgeBuilderConfigurationImpl) conf );
        }
    }

    public Class getServiceInterface() {
        return KnowledgeBuilderFactoryService.class;
    }
}
