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

    @Override
    public void setAssetFilter( AssetFilter assetFilter ) {
        super.setAssetFilter( new AssetFilterDecorator( assetFilter ) );
    }

    public static class AssetFilterDecorator implements AssetFilter {
        AssetFilter decorated;

        public AssetFilterDecorator( AssetFilter decorated ) {
            this.decorated = decorated;
        }

        @Override
        public Action accept( Type type, String pkgName, String assetName ) {
            System.out.println( "Type " + type + ". Package: " + pkgName + ". Asset: " + assetName );
            if ( decorated != null ) {
                return decorated.accept( type, pkgName, assetName );
            }
            else {
                // what should I return here?
                return Action.ADD;
            }
        }
    }
}