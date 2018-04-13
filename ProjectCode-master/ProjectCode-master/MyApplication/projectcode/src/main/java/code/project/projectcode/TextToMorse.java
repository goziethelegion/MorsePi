package code.project.projectcode;

/*
Createed By Andrew McGuire
TEAM PANADA
PROJECT CODE
 */

public class TextToMorse {

    public static String textToMorse(String morse)
    {
        String s = morse.toLowerCase();
        String message = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz .1234567890";
        String[] morseAlphabet = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....",
                "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-",
                "...-", ".--", "-..-", "-.--", "--..", " ", ".-.-.-",".-----","..---","...--","....-",".....",
                "-....","--...","---..","----.","-----"};

        for (char alphabetLetter : s.toCharArray())
        {
            int index = -1;
            String letter = " ";
            for (int i = 0; i < alphabet.length(); i++)
            {
                if (alphabet.charAt(i) == alphabetLetter)
                {
                    index = i;
                }
            }
            if (index >=0)
            {
                letter = morseAlphabet[index];
            }
            message += letter + " ";
        }

        return message;
    }
}
