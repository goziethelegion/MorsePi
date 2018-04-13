package code.project.projectcode;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/*
Createed By Andrew McGuire
TEAM PANADA
PROJECT CODE
 */

public class MorseToTextTest {

    @Test
    public void testTranslation() throws Exception{
        //Expected SOS
        assertEquals("sos", MorseToText.fromMorse("... --- ..."),"sos");
    }

}
