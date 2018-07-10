package edu.kit.ipd.pp.viper.model.parser;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;
import edu.kit.ipd.pp.viper.model.parser.TokenType;

import java.util.HashMap;

/**
 * This class lexes a program given as String into tokens.
 * Tokens are lexed one by one as requested by the parser.
 */
public class PrologLexer {
    /**
     * String of characters that may be part of an operator
     * (excluding operators whose starting character are not at the start of another operator).
     */
    private static final String SYMBOLS = ":-=<>\\";

    /**
     * Maps a String of SYMBOLS to the correct operator.
     * Characters where the only recognized operator starting with it contains just that symbol
     * are lexed directly and therefore not part of this map.
     */
    private static final HashMap<String, TokenType> SYMBOL_STRING_TO_TOKEN = new HashMap<>();
    
    /**
     * The given program as a String
     */
    private final String program;
    /**
     * current position in the program
     */
    private int pos = 0;
    /**
     * current line in the program
     */
    private int line = 1;
    /**
     * current column in the program
     */
    private int col = 1;

    /*
     * Initialize SYMBOL_STRING_TO_TOKEN map.
     * Every used operator that is not lexed directly must be put into this map.
     */
    static {
        SYMBOL_STRING_TO_TOKEN.put(":-", TokenType.COLON_MINUS);
        SYMBOL_STRING_TO_TOKEN.put("=", TokenType.EQ);
        SYMBOL_STRING_TO_TOKEN.put("<", TokenType.LESS);
        SYMBOL_STRING_TO_TOKEN.put("=<", TokenType.EQ_LESS);
        SYMBOL_STRING_TO_TOKEN.put(">", TokenType.GREATER);
        SYMBOL_STRING_TO_TOKEN.put(">=", TokenType.GREATER_EQ);
        SYMBOL_STRING_TO_TOKEN.put("=:=", TokenType.EQ_COLON_EQ);
        SYMBOL_STRING_TO_TOKEN.put("=\\=", TokenType.EQ_BS_EQ);
    }

    /**
     * Constructs a lexer that lexes the given program
     * @param program the program to lex
     */
    public PrologLexer(String program) {
        this.program = program;
    }

    /**
     * Advances the current char to the next char in the program.
     * Adapts line and column number accordingly.
     */
    private void advance() {
        if (program.charAt(pos) == '\n') {
            line += 1;
            col = 1;
        } else {
            col += 1;
        }
        pos += 1;
    }

    /**
     * Lexes a comment: Ignores characters until the end of the line.
     */
    private void lexComment() {
        while (pos < program.length() && program.charAt(pos) != '\n') {
            pos += 1;
            col += 1;
        }
        if (pos < program.length()) {
            advance();
        }
    }

    /**
     * Lexes and returns the next token.
     * @return the next token
     * @throws ParseException if the next token is invalid
     */
    public Token nextToken() throws ParseException {
        while (pos < program.length()
                && (Character.isWhitespace(program.charAt(pos)) || program.charAt(pos) == '%')) {
            // ignore whitespace and comments
            if (program.charAt(pos) == '%') {
                lexComment();
            } else {
                advance();
            }
        }
        if (pos >= program.length()) {
            // program ended, return EOF
            return new Token(TokenType.EOF, "", line, col);
        }
        Token t;
        char c = program.charAt(pos);
        switch (c) {
            case ':':
            case '=':
            case '<':
            case '>':
                return handleMultiSimOperators();
            // bunch of single-character tokens
            case '.':
                t = new Token(TokenType.DOT, ".", line, col);
                advance();
                return t;
            case ',':
                t = new Token(TokenType.COMMA, ",", line, col);
                advance();
                return t;
            case '(':
                t = new Token(TokenType.LP, "(", line, col);
                advance();
                return t;
            case ')':
                t = new Token(TokenType.RP, ")", line, col);
                advance();
                return t;
            case '[':
                t = new Token(TokenType.LB, "[", line, col);
                advance();
                return t;
            case ']':
                t = new Token(TokenType.RB, "]", line, col);
                advance();
                return t;
            case '|':
                t = new Token(TokenType.BAR, "|", line, col);
                advance();
                return t;
            case '+':
                t = new Token(TokenType.PLUS, "+", line, col);
                advance();
                return t;
            case '-':
                t = new Token(TokenType.MINUS, "-", line, col);
                advance();
                return t;
            case '*':
                t = new Token(TokenType.STAR, "*", line, col);
                advance();
                return t;
            case '!':
                t = new Token(TokenType.EXCLAMATION, "!", line, col);
                advance();
                return t;
            default:
                return handleDefault(c);
        }
    }
    
    private Token handleMultiSimOperators() throws ParseException {
        // these operators may consist of multiple SYMBOLS
        int colStart = col;
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(program.charAt(pos));
            advance();
        } while (pos < program.length() && SYMBOLS.indexOf(program.charAt(pos)) > -1);
        TokenType type = SYMBOL_STRING_TO_TOKEN.get(sb.toString());
        if (type == null) {
            throw new ParseException(
                    String.format(LanguageManager.getInstance().getString(LanguageKey.OPERATOR_NOT_RECOGNIZED),
                    sb.toString())
                    + getTokenPositionString());
        }
        return new Token(type, sb.toString(), line, colStart);
    }
    
    private Token handleDefault(char c) throws ParseException {
        int colStart = col;
        if (Character.isLetter(c)) {
            // variable, identifier or the special token "is"
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(program.charAt(pos));
                advance();
            } while (pos < program.length() && Character.isLetterOrDigit(program.charAt(pos)));
            String s = sb.toString();
            TokenType type;
            if ("is".equals(s)) {
                type = TokenType.IS;
            } else if (Character.isUpperCase(c)) {
                type = TokenType.VARIABLE;
            } else {
                type = TokenType.IDENTIFIER;
            }
            return new Token(type, s, line, colStart);
        } else if (Character.isDigit(c)) {
            // number literal
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(program.charAt(pos));
                advance();
            } while (pos < program.length() && Character.isDigit(program.charAt(pos)));
            return new Token(TokenType.NUMBER, sb.toString(), line, colStart);
        } else {
            throw new ParseException(LanguageManager.getInstance().getString(LanguageKey.ILLEGAL_CHAR)
                    + " '" + program.charAt(pos) + "'"
                    + getTokenPositionString());
        }
    }

    /** 
     * Return the last line (max 50 char.) of code before the current token
     * @return code block
     */
    String getCodeBeforeCurrentPos() {
        String codeBefore = this.program.substring(Math.max(0, this.pos - 50), this.pos - 1);
        String[] lines = codeBefore.split("\n");
        return lines[lines.length - 1].trim();
    }

    private String getTokenPositionString() {
        return " " + String.format(LanguageManager.getInstance().getString(LanguageKey.POSITION),
                getCodeBeforeCurrentPos(), line, col);
    }
}