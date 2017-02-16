package com.redhat.kie.serialization;

import static com.redhat.kie.serialization.util.Utils.createKieModule;
import static com.redhat.kie.serialization.util.Utils.deserializeKieBase;
import static com.redhat.kie.serialization.util.Utils.serializeKieBase;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

import com.redhat.kie.serialization.util.Utils;

/**
 * 
 * The stack size is no longer an issue as a result of https://github.com/droolsjbpm/drools/commit/cc4cd848e292914187db2396eb27f8e72d740863
 * 
 * As a reminder, the following three requirements apply 
 * 1) Rules must be built and serialized in a binary format that an be saved to a DB 
 * 2) Rules must be loaded from binary and not rebuilt at runtime 
 * 3) The stack size should not exceed the JVM defaults
 *
 */
@SuppressWarnings( "serial" )
public class SerializeKieBaseTest {

    private static final String KBASE_BIN_FILE = Utils.TARGET_DIR + File.separator + "kbase.bin";

    @Before
    public void setUp() throws Exception {
        Files.deleteIfExists( Paths.get( KBASE_BIN_FILE ) );
    }

    /**
     * This is just for debugging purpose to prove this works
     */
    @Test
    public void serializeKieBaseAsOneFile() throws Exception {
        List<Map<String,String>> resources = new ArrayList<Map<String,String>>();
        resources.add( new HashMap<String, String>() { { put( "package", "com.redhat.rules" ); put( "filename", "RulesAndDeclaredFact.drl" ); } } );

        serializeKieBase( KBASE_BIN_FILE, createKieModule( resources ) );
        
        KieBase kbase = deserializeKieBase( KBASE_BIN_FILE );
        KieSession session = kbase.newKieSession();
        int count = session.fireAllRules();

        assertEquals( 2, count );

    }

    /**
     * This is just for debugging purpose to prove this works
     */
    @Test
    public void serializeKieBaseAsTwoFiles() throws Exception {
        List<Map<String, String>> resources = new ArrayList<Map<String, String>>();
        resources.add( new HashMap<String, String>() { { put( "package", "com.redhat.rules.generated.facts" ); put( "filename", "DeclaredFact.drl" ); } } );
        resources.add( new HashMap<String, String>() { { put( "package", "com.redhat.rules.generated.facts" ); put( "filename", "RulesOnly.drl" ); } } );

        serializeKieBase( KBASE_BIN_FILE, createKieModule( resources ) );

        KieBase kbase = deserializeKieBase( KBASE_BIN_FILE );
        KieSession session = kbase.newKieSession();
        int count = session.fireAllRules();

        assertEquals( 2, count );

    }
}