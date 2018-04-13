package code.project.projectcode;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/*
Createed By Andrew McGuire
TEAM PANADA
PROJECT CODE
 */

public class TestToMorseTest {

    @Test
    public void testTranslation() throws Exception{
        //Expected SOS
        assertEquals("... --- ... ", TextToMorse.textToMorse("sos"),"... --- ... ");
    }
}
