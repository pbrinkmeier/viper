package edu.kit.ipd.pp.viper.model.ast;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

import org.junit.*;
import static org.junit.Assert.*;

public class UnsetVariableExceptionTest {
    @Test
    public void getMessageTest() {
        Variable unsetVar = new Variable("X");
        TermEvaluationException e = new UnsetVariableException(unsetVar);
        String messageFormat = LanguageManager.getInstance().getString(LanguageKey.ARITHMETIC_UNSET_VARIABLE);

        assertEquals(String.format(messageFormat, unsetVar), e.getMessage());
    }
}
