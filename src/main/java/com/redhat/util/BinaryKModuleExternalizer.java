/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.util;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.core.common.DroolsObjectInputStream;
import org.drools.core.common.DroolsObjectOutputStream;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.builder.model.KieSessionModel.KieSessionType;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.definition.KnowledgePackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Binary KieModule tool
 * 
 * @author okuniyas
 */
public class BinaryKModuleExternalizer {
    private static Logger logger = LoggerFactory.getLogger( BinaryKModuleExternalizer.class );

    /**
     * Binary KieModule
     */
    public static class BinKieModule implements Externalizable {
        ReleaseId releaseId;
        HashMap<ReleaseId, BinKieModule> dependencyMap;
        HashMap<String, BinKieBaseModel> binKBaseModelMap;

        public BinKieModule() {
            dependencyMap = new HashMap<ReleaseId, BinKieModule>();
            binKBaseModelMap = new HashMap<String, BinKieBaseModel>();
        }

        public void copy( KieServices ks, InternalKieModule kModule ) {
            releaseId = kModule.getReleaseId();
            /*
            // dump with dependencies
            kModule.getKieDependencies().forEach((depReleaseId, depKModule) -> {
            	BinKieModule depBinKModule = new BinKieModule();
            	depBinKModule.copy(ks, depKModule);
            	dependencyMap.put(depReleaseId, depBinKModule);
            });
            */
            KieContainer kContainer = ks.newKieContainer( kModule.getReleaseId() );
            kModule.getKieModuleModel().getKieBaseModels().forEach( ( name, kBaseModel ) -> {
                BinKieBaseModel binKBaseModel = new BinKieBaseModel();
                binKBaseModel.copy( kContainer, kBaseModel );
                binKBaseModelMap.put( name, binKBaseModel );
            } );
        }

        @Override
        public void writeExternal( ObjectOutput out ) throws IOException {
            out.writeObject( releaseId );
            out.writeObject( dependencyMap );
            out.writeObject( binKBaseModelMap );
        }

        @SuppressWarnings( "unchecked" )
        @Override
        public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException {
            releaseId = (ReleaseId) in.readObject();
            dependencyMap = (HashMap<ReleaseId, BinKieModule>) in.readObject();
            binKBaseModelMap = (HashMap<String, BinKieBaseModel>) in.readObject();
        }
    }

    /**
     * Binary KieBaseModel
     */
    public static class BinKieBaseModel implements Externalizable {
        String name;
        Boolean isDefault;
        EqualityBehaviorOption equalsBahavior;
        EventProcessingOption eventProcessingMode;
        Collection<KiePackage> kPackages;
        HashMap<String, BinKieSessionModel> binKieSessionModelMap;

        public BinKieBaseModel() {
            binKieSessionModelMap = new HashMap<String, BinKieSessionModel>();
        }

        public void copy( KieContainer kContainer, KieBaseModel kBaseModel ) {
            name = kBaseModel.getName();
            isDefault = kBaseModel.isDefault();
            equalsBahavior = kBaseModel.getEqualsBehavior();
            eventProcessingMode = kBaseModel.getEventProcessingMode();

            KieBase kBase = kContainer.getKieBase( name );
            kPackages = kBase.getKiePackages();
            kBaseModel.getKieSessionModels().forEach( ( name, kSessionModel ) -> {
                BinKieSessionModel binKSessionModel = new BinKieSessionModel();
                binKSessionModel.copy( kSessionModel );
                binKieSessionModelMap.put( name, binKSessionModel );
            } );
        }

        @Override
        public void writeExternal( ObjectOutput out ) throws IOException {
            out.writeObject( name );
            out.writeObject( isDefault );
            out.writeObject( equalsBahavior );
            out.writeObject( eventProcessingMode );
            out.writeObject( kPackages );
            out.writeObject( binKieSessionModelMap );
        }

        @SuppressWarnings( "unchecked" )
        @Override
        public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            isDefault = (Boolean) in.readObject();
            equalsBahavior = (EqualityBehaviorOption) in.readObject();
            eventProcessingMode = (EventProcessingOption) in.readObject();
            kPackages = (Collection<KiePackage>) in.readObject();
            binKieSessionModelMap = (HashMap<String, BinKieSessionModel>) in.readObject();
        }
    }

    /**
     * Binary KieSessionModel
     */
    public static class BinKieSessionModel implements Externalizable {
        String name;
        Boolean isDefault;
        KieSessionType type;
        ClockTypeOption clockType;

        public void copy( KieSessionModel kSessionModel ) {
            name = kSessionModel.getName();
            isDefault = kSessionModel.isDefault();
            type = kSessionModel.getType();
            clockType = kSessionModel.getClockType();
        }

        @Override
        public void writeExternal( ObjectOutput out ) throws IOException {
            out.writeObject( name );
            out.writeObject( isDefault );
            out.writeObject( type );
            out.writeObject( clockType );
        }

        @Override
        public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            isDefault = (Boolean) in.readObject();
            type = (KieSessionType) in.readObject();
            clockType = (ClockTypeOption) in.readObject();
        }
    }

    /**
     * Make a Binary File from an existing KJAR
     * 
     * @param kjarFile
     * @param binFile
     */
    public static void kjarToBinary( File kjarFile, File binFile ) throws Exception {
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();

        InternalKieModule kModule = (InternalKieModule) kr.addKieModule( ks.getResources().newFileSystemResource( kjarFile ) );
        kieModuleToBinary( kModule, binFile );
    }

    /**
     * Make a Binary File from a KieModule<BR>
     * ※ using KieBuilder#getKieModule() to get the KieModule
     * 
     * @param kModule
     * @param binFile
     */
    public static void kieModuleToBinary( KieModule kModule, File binFile ) throws Exception {
        KieServices ks = KieServices.Factory.get();
        BinKieModule binKModule = new BinKieModule();
        binKModule.copy( ks, (InternalKieModule) kModule );

        FileOutputStream fo = new FileOutputStream( binFile );
        ObjectOutputStream so = new DroolsObjectOutputStream( fo );
        so.writeObject( binKModule );
        so.flush();
        so.close();
    }

    /**
     * Get a KieContainer from a Binary File
     * 
     * @param binFile
     * @return KieContainer
     */
    public static KieContainer getKieContainer( File binFile ) throws Exception {
        FileInputStream fi = new FileInputStream( binFile );
        ObjectInputStream si = new DroolsObjectInputStream( fi );
        BinKieModule binKModule = (BinKieModule) si.readObject();
        si.close();

        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        // kmodule info
        KieModuleModel kModuleModel = getKieModuleModel( binKModule, ks );
        kfs.writeKModuleXML( kModuleModel.toXML() );
        kfs.generateAndWritePomXML( binKModule.releaseId );

        // build with empty resources
        KieBuilder kb = ks.newKieBuilder( kfs );
        kb.buildAll();
        if ( kb.getResults().hasMessages( Level.ERROR ) ) {
            throw new RuntimeException( "Build Errors:\n" + kb.getResults().toString() );
        }
        // create container
        KieContainer kContainer = ks.newKieContainer( binKModule.releaseId );
        binKModule.binKBaseModelMap.forEach( ( kBaseName, binKBaseModel ) -> {
            // add KnowledgePackages to KnowledgeBase
            KnowledgeBaseImpl knowledgeBase = (KnowledgeBaseImpl) kContainer.getKieBase( kBaseName );
            @SuppressWarnings( "unchecked" )
            Collection<KnowledgePackage> knowledgePackages = (Collection<KnowledgePackage>) (Collection<?>) binKBaseModel.kPackages;
            knowledgeBase.addKnowledgePackages( knowledgePackages );
        } );
        return kContainer;

    }

    /**
     * Get a KieModuleModel from a Binary KieModule
     * 
     * @param binKModule
     * @param ks
     * @return KieModuleModel
     */
    private static KieModuleModel getKieModuleModel( BinKieModule binKModule, KieServices ks ) {
        KieModuleModel kModuleModel = ks.newKieModuleModel();
        binKModule.binKBaseModelMap.forEach( ( kBaseName, binKBaseModel ) -> {
            KieBaseModel kieBaseModel = kModuleModel.newKieBaseModel( kBaseName ).setDefault( binKBaseModel.isDefault ).setEqualsBehavior( binKBaseModel.equalsBahavior )
                    .setEventProcessingMode( binKBaseModel.eventProcessingMode );
            binKBaseModel.binKieSessionModelMap.forEach( ( kSessionName, binKSessionModel ) -> {
                kieBaseModel.newKieSessionModel( kSessionName ).setDefault( binKSessionModel.isDefault ).setType( binKSessionModel.type )
                        .setClockType( binKSessionModel.clockType );
            } );
        } );
        return kModuleModel;
    }
}
