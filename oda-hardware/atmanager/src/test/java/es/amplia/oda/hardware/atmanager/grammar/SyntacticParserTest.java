package es.amplia.oda.hardware.atmanager.grammar;

import es.amplia.oda.hardware.atmanager.grammar.SyntacticParser;
import es.amplia.oda.hardware.atmanager.grammar.SyntacticParser.Tokens;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SyntacticParserTest {

    private SyntacticParser assertTokens(String toParse, Tokens... tokens) {
        SyntacticParser syntacticParser = new SyntacticParser(toParse);

        for (Tokens token : tokens) {
            assertEquals(syntacticParser.current(), token);
            syntacticParser.advance();
        }

        return syntacticParser;
    }

    @Test
    public void empty() {
        assertTokens("", Tokens.EOF, Tokens.EOF);
    }

    @Test
    public void letter() {
        assertTokens("A", Tokens.BASIC_NAME, Tokens.EOF);
    }

    @Test
    public void ampersand() {
        assertTokens("&A", Tokens.BASIC_NAME, Tokens.EOF);
    }

    @Test
    public void lettersAreUppercased() {
        String actual = assertTokens("a", Tokens.BASIC_NAME).getLastCommand();
        assertEquals(actual, "A");
    }

    @Test
    public void number() {
        int actual = assertTokens("123", Tokens.NUMBER).getLastNumber();
        assertEquals(actual, 123);
    }

    @Test
    public void numberLetter() {
        assertTokens("123a", Tokens.NUMBER, Tokens.BASIC_NAME);
    }

    @Test
    public void ipAddress() {
        String actual = assertTokens("192.168.2.2",Tokens.IPV4).getLastIPAddress();
        assertEquals(actual, "192.168.2.2");
    }

    @Test
    public void constant() {
        String actual = assertTokens("DGRAM", Tokens.CONSTANT).getLastConstant();
        assertEquals(actual, "DGRAM");
    }

    @Test
    public void semicolon() {
        assertTokens(";", Tokens.SEMICOLON, Tokens.EOF);
    }

    @Test
    public void otherSingleChars() {
        assertTokens(",?= \r", Tokens.COMMA, Tokens.QUESTION, Tokens.EQUAL, Tokens.SPACE, Tokens.SPACE, Tokens.EOF);
    }

    @Test
    public void equalQuestion() {
        assertTokens("=?", Tokens.EQUAL_QUESTION, Tokens.EOF);
    }

    @Test
    public void equalQuestionLetter() {
        assertTokens("=?a", Tokens.EQUAL_QUESTION, Tokens.BASIC_NAME, Tokens.EOF);
    }

    @Test
    public void error() {
        assertTokens("*", Tokens.ERROR);
    }

    @Test
    public void namePrefixWithoutName() {
        assertTokens("+", Tokens.ERROR);
    }

    @Test
    public void extendedName() {
        String actual = assertTokens("+a0!%-_./:", Tokens.EXTENDED_NAME, Tokens.COLON, Tokens.EOF).getLastCommand();
        assertEquals(actual, "+A0!%-_./");
    }

    @Test
    public void sParameter() {
        String actual = assertTokens("S13=2", Tokens.S_NAME, Tokens.EQUAL, Tokens.NUMBER, Tokens.EOF).getLastCommand();
        assertEquals(actual, "S13");
    }

    @Test
    public void string() {
        String expected = "This is text";
        String actual = assertTokens("\"" + expected + "\"", Tokens.STRING, Tokens.EOF).getLastString();
        assertEquals(actual, expected);
    }

    @Test
    public void unfinishedString() {
        String expected = "This is text";
        String actual = assertTokens("\"" + expected, Tokens.STRING, Tokens.EOF).getLastString();
        assertEquals(actual, expected);
    }

    @Test
    public void stringAmpersand() {
        assertTokens("\"text\",", Tokens.STRING, Tokens.COMMA, Tokens.EOF);
    }

    @Test
    public void colon() {
        assertTokens(":", Tokens.COLON, Tokens.EOF);
    }
}
