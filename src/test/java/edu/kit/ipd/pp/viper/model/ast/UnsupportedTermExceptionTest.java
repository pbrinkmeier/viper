package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

import org.junit.*;
import static org.junit.Assert.*;

public class UnsupportedTermExceptionTest {
    @Test
    public void getMessageTest() {
        Term unsupported = Functor.atom("lame");
        TermEvaluationException e = new UnsupportedTermException(unsupported);
        String messageFormat = LanguageManager.getInstance().getString(LanguageKey.ARITHMETIC_UNSUPPORTED_TERM);

        assertEquals(String.format(messageFormat, unsupported), e.getMessage());
    }
}
