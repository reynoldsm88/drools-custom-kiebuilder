/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.drools.compiler.builder.impl;

import java.util.Properties;

import org.drools.core.builder.conf.impl.DecisionTableConfigurationImpl;
import org.drools.core.builder.conf.impl.JaxbConfigurationImpl;
import org.drools.core.builder.conf.impl.ScoreCardConfigurationImpl;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.JaxbConfiguration;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderConfiguration;
import org.kie.internal.builder.KnowledgeBuilderFactoryService;
import org.kie.internal.builder.ScoreCardConfiguration;
import org.kie.internal.utils.KieService;

import com.redhat.rules.custom.CustomKnowledgeBuilderImpl;
//import com.redhat.rules.custom.CustomKnowledgeBuilderImpl;
import com.sun.tools.xjc.Options;

public class CustomKnowledgeBuilderFactoryServiceImpl extends KnowledgeBuilderFactoryServiceImpl implements KieService {

    public KnowledgeBuilderConfiguration newKnowledgeBuilderConfiguration() {
        return new KnowledgeBuilderConfigurationImpl();
    }

    public KnowledgeBuilderConfiguration newKnowledgeBuilderConfiguration( Properties properties, ClassLoader... classLoaders ) {
        return new KnowledgeBuilderConfigurationImpl( properties, classLoaders );
    }

    public DecisionTableConfiguration newDecisionTableConfiguration() {
        return new DecisionTableConfigurationImpl();
    }

    public ScoreCardConfiguration newScoreCardConfiguration() {
        return new ScoreCardConfigurationImpl();
    }

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

    public JaxbConfiguration newJaxbConfiguration( Options xjcOpts, String systemId ) {
        return new JaxbConfigurationImpl( xjcOpts, systemId );
    }

    @Override
    public Class getServiceInterface() {
        return KnowledgeBuilderFactoryService.class;
    }
}