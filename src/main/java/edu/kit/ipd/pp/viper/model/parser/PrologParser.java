package edu.kit.ipd.pp.viper.model.parser;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

import edu.kit.ipd.pp.viper.model.ast.AdditionOperation;
import edu.kit.ipd.pp.viper.model.ast.ArithmeticGoal;
import edu.kit.ipd.pp.viper.model.ast.CutGoal;
import edu.kit.ipd.pp.viper.model.ast.EqualGoal;
import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.GreaterThanEqualGoal;
import edu.kit.ipd.pp.viper.model.ast.GreaterThanGoal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.LessThanEqualGoal;
import edu.kit.ipd.pp.viper.model.ast.LessThanGoal;
import edu.kit.ipd.pp.viper.model.ast.MultiplicationOperation;
import edu.kit.ipd.pp.viper.model.ast.NotEqualGoal;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.ast.SubtractionOperation;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.UnificationGoal;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.parser.TokenType;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

public class PrologParser {
    /**
     * lexer to translate a String into tokens
     */
    private final PrologLexer lexer;
    /**
     * Next token to use while parsing. The following invariant holds: When calling
     * a parseX method, token is the first token of X (as opposed to the last token
     * of the previous construct).
     */
    private Token token;

    /**
     * Constructs a parser with the specified String
     * 
     * @param program String to parse
     * @throws ParseException if there are syntactical errors in the program
     */
    public PrologParser(String program) throws ParseException {
        this.lexer = new PrologLexer(program);
        this.nextToken();
    }

    /**
     * Sets token to the next available token.
     * 
     * @throws ParseException if the next token is invalid
     */
    private void nextToken() throws ParseException {
        this.token = this.lexer.nextToken();
    }

    /**
     * Checks that the token type of current token matches the token type given as
     * parameter. Throws a ParseException otherwise.
     * 
     * @param type the token type to compare the current token type to
     * @throws ParseException if the token type of the current token does not match
     *         the one given as parameter
     */
    private void expect(TokenType type) throws ParseException {
        if (this.token.getType() != type) {
            throw new ParseException(
                    String.format(LanguageManager.getInstance().getString(LanguageKey.EXPECTED_INSTEAD),
                            "'" + type.getString() + "'", this.token.getType().getString())
                            + this.getTokenPositionString());
        }
        this.nextToken();
    }

    /**
     * Parses the String given in the constructor as a program.
     * 
     * @return the program given by the String
     * @throws ParseException if the program String is not syntactically valid
     */
    public KnowledgeBase parse() throws ParseException {
        return this.parseProgram();
    }

    /**
     * Parses a program.
     * 
     * @return the program
     * @throws ParseException if a parser error occurs
     */
    private KnowledgeBase parseProgram() throws ParseException {
        List<Rule> rules = new LinkedList<>();
        while (this.token.getType() != TokenType.EOF) {
            rules.add(this.parseRule());
        }
        return new KnowledgeBase(rules);
    }

    /**
     * Parses a rule (including facts).
     * 
     * @return the rule
     * @throws ParseException if a rule is syntactically incorrect
     */
    private Rule parseRule() throws ParseException {
        Functor lhs = this.parseFunctor();
        if (this.token.getType() == TokenType.DOT) {
            this.nextToken();
            return new Rule(lhs, Arrays.asList());
        }
        this.expect(TokenType.COLON_MINUS);
        List<Goal> goals = this.parseGoalList();
        return new Rule(lhs, goals);
    }

    /**
     * Parses a (non-empty) goal list.
     * 
     * @return the goal list
     * @throws ParseException if a parser error occurs
     */
    public List<Goal> parseGoalList() throws ParseException {
        List<Goal> goals = new LinkedList<>();
        goals.add(this.parseGoal());
        while (this.token.getType() != TokenType.DOT) {
            this.expect(TokenType.COMMA);
            goals.add(this.parseGoal());
        }
        this.nextToken();
        return goals;
    }

    /**
     * Parses a single goal.
     * 
     * @return the goal
     * @throws ParseException if a parser error occurs
     */
    private Goal parseGoal() throws ParseException {
        switch (this.token.getType()) {
        case IDENTIFIER:
            Functor f = this.parseFunctor();
            if (this.token.getType() == TokenType.COMMA || this.token.getType() == TokenType.DOT) {
                return new FunctorGoal(f);
            } else {
                return this.parseGoalRest(f);
            }

        case VARIABLE:
        case NUMBER:
        case LB:
            Term t = this.parseTerm();
            return this.parseGoalRest(t);

        case EXCLAMATION:
            this.nextToken();
            return new CutGoal();

        default:
            throw new ParseException(
                    String.format("%s: %s %s", LanguageManager.getInstance().getString(LanguageKey.GOAL_NOT_SUPPORTED),
                            this.token.getType().getString(), this.getTokenPositionString()));
        }
    }

    /**
     * Parses a functor.
     * 
     * @return the functor
     * @throws ParseException if a parser error occurs
     */
    private Functor parseFunctor() throws ParseException {
        switch (this.token.getType()) {
        case IDENTIFIER:
            String name = this.token.getText();
            this.nextToken();
            if (this.token.getType() != TokenType.LP) {
                return Functor.atom(name);
            }
            this.expect(TokenType.LP);
            List<Term> terms = new LinkedList<>();
            terms.add(this.parseTerm());
            while (this.token.getType() != TokenType.RP) {
                this.expect(TokenType.COMMA);
                terms.add(this.parseTerm());
            }
            this.nextToken();
            return new Functor(name, terms);
        case LB:
            return this.parseList();
        default:
            throw new ParseException(
                    String.format(LanguageManager.getInstance().getString(LanguageKey.EXPECTED_INSTEAD),
                            LanguageManager.getInstance().getString(LanguageKey.FUNCTOR),
                            this.token.getType().getString()) + this.getTokenPositionString());
        }
    }

    /**
     * Parses a remainder of a goal, given the beginning term.
     * 
     * @param t the beginning term of the goal
     * @return the resulting goal
     * @throws ParseException if a parser error occurs
     */
    private Goal parseGoalRest(Term t) throws ParseException {
        Term lhs = this.parseTerm(Optional.of(t));
        TokenType op = this.token.getType();
        this.nextToken();
        Term rhs = this.parseTerm();

        switch (op) {
        case EQ:
            return new UnificationGoal(lhs, rhs);
        case IS:
            return new ArithmeticGoal(lhs, rhs);
        case LESS:
            return new LessThanGoal(lhs, rhs);
        case EQ_LESS:
            return new LessThanEqualGoal(lhs, rhs);
        case GREATER:
            return new GreaterThanGoal(lhs, rhs);
        case GREATER_EQ:
            return new GreaterThanEqualGoal(lhs, rhs);
        case EQ_COLON_EQ:
            return new EqualGoal(lhs, rhs);
        case EQ_BS_EQ:
            return new NotEqualGoal(lhs, rhs);
        default:
            throw new ParseException(
                    String.format(LanguageManager.getInstance().getString(LanguageKey.EXPECTED_GOALREST),
                            this.token.getType().getString()) + this.getTokenPositionString());
        }
    }

    /**
     * Parses a term.
     * 
     * @return the term
     * @throws ParseException if a parser error occurs
     */
    private Term parseTerm() throws ParseException {
        return this.parseTerm(Optional.empty());
    }

    /**
     * Parses a term, with possibly the first part given as an Optional parameter.
     * 
     * @param maybeTerm Optional term signifying the first part of the term. When
     *        present, it is the first part of the term, which will not be parsed
     *        again. When empty, the full term is parsed.
     * @return the resulting term
     * @throws ParseException if a parser error occurs
     */
    private Term parseTerm(Optional<Term> maybeTerm) throws ParseException {
        Term t = this.parseSummand(maybeTerm);
        while (this.token.getType() == TokenType.PLUS || this.token.getType() == TokenType.MINUS) {
            TokenType op = this.token.getType();
            this.nextToken();
            Term rhs = this.parseSummand(Optional.empty());

            if (op == TokenType.PLUS) {
                t = new AdditionOperation(t, rhs);
            // the else if below is not needed because it's clear from the looping condition
            // } else if (op == TokenType.MINUS) {
            } else {
                t = new SubtractionOperation(t, rhs);
            }
        }

        return t;
    }

    /**
     * Parses a term, with possibly the first part given as an Optional parameter.
     * Parses only operators with higher precedence than '+'/'-'.
     * 
     * @param maybeTerm Optional term signifying the first part of the term. When
     *        present, it is the first part of the term, which will not be parsed
     *        again. When empty, the full term is part.
     * @return the resulting term
     * @throws ParseException if a parser error occurs
     */
    private Term parseSummand(Optional<Term> maybeTerm) throws ParseException {
        Term t = this.parseFactor(maybeTerm);

        while (this.token.getType() == TokenType.STAR) {
            this.nextToken();
            Term t2 = this.parseFactor(Optional.empty());
            t = new MultiplicationOperation(t, t2);
        }

        return t;
    }

    /**
     * Parses a term, with it possibly already given as an Optional parameter.
     * Parses only functors, numbers, variables and terms in parentheses.
     * 
     * @param maybeTerm Optional term. When present, it is simply returned. When
     *        empty, the full term is part.
     * @return the resulting term
     * @throws ParseException if a parser error occurs
     */
    private Term parseFactor(Optional<Term> maybeTerm) throws ParseException {
        if (maybeTerm.isPresent()) {
            return maybeTerm.get();
        }
        switch (this.token.getType()) {
        case IDENTIFIER:
        case LB:
            return this.parseFunctor();

        case NUMBER:
            return this.parseNumber();

        case VARIABLE:
            String name = this.token.getText();
            this.nextToken();
            return new Variable(name);

        case LP:
            this.nextToken();
            Term t = this.parseTerm();
            this.expect(TokenType.RP);
            return t;

        default:
            throw new ParseException(
                    String.format(LanguageManager.getInstance().getString(LanguageKey.EXPECTED_INSTEAD),
                            LanguageManager.getInstance().getString(LanguageKey.TERM), this.token.getType().getString())
                            + this.getTokenPositionString());
        }
    }

    /**
     * Parses a non-negative number literal.
     * 
     * @return the number literal
     * @throws ParseException if a parser error occurs
     */
    private Number parseNumber() throws ParseException {
        BigInteger n = new BigInteger(this.token.getText());
        this.nextToken();
        return new Number(n);
    }

    /**
     * Parses a list functor.
     * 
     * @return the list
     * @throws ParseException if a parser error occurs
     */
    private Functor parseList() throws ParseException {
        this.expect(TokenType.LB);

        switch (this.token.getType()) {
        case RB:
            this.nextToken();

            return Functor.atom("[]");

        case IDENTIFIER:
        case NUMBER:
        case VARIABLE:
        case LB:
            Term t = this.parseTerm();
            return this.parseListRest(t);

        default:
            throw new ParseException(String.format(LanguageManager.getInstance().getString(LanguageKey.EXPECTED_LIST),
                    this.token.toString()) + this.getTokenPositionString());
        }
    }

    /**
     * Parses the remainder of a list functor.
     * 
     * @param t the first element of the list to parse
     * @return the resulting list
     * @throws ParseException if a parser error occurs
     */
    private Functor parseListRest(Term t) throws ParseException {
        Term rest;

        switch (this.token.getType()) {
        case RB:
            this.nextToken();
            rest = Functor.atom("[]");

            break;

        case COMMA:
            this.nextToken();
            rest = this.parseListRest(this.parseTerm());

            break;

        case BAR:
            this.nextToken();
            rest = this.parseTerm();
            this.expect(TokenType.RB);

            break;

        default:
            throw new ParseException(
                    String.format(LanguageManager.getInstance().getString(LanguageKey.EXPECTED_LIST_REST),
                            this.token.toString()) + this.getTokenPositionString());
        }

        return new Functor("[|]", Arrays.asList(t, rest));
    }

    private String getTokenPositionString() {
        return " " + String.format(LanguageManager.getInstance().getString(LanguageKey.POSITION),
                this.lexer.getCodeBeforeCurrentPos(), this.token.getLine(), this.token.getCol());
    }
}
