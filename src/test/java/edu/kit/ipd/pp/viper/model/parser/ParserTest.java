package edu.kit.ipd.pp.viper.model.parser;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserTest {
    private PrologParser parser;
    
    @Before
    public void init() throws IOException, ParseException {
        // String src = new String(Files.readAllBytes(Paths.get("simpsons.pl")));
        // this.parser = new PrologParser(src);
    }
    
    @Test
    public void parseTest() {
        
    }
}