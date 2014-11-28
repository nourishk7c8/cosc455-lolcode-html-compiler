//package edu.towson.cis.cosc455.lab2.Compiler;

/**
 * COSC 455 Programming Languages: Implementation and Design.
 *
 * A Simple Syntax Analyzer adapted from Sebesta (2010) by Josh Dehlinger,
 * modified by Adam Conover (2012) and interfaced by Josh Dehlinger (2013).
 *
 * Note that these are not the only methods necessary to parse the BNF
 * grammar rules. You will likely need to add new methods to your implementation
 * of this interface.
 *
 */
public interface SyntaxAnalyzer {

	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * <lolcode> ::= #HAI <comment> <variable-define> <head> <body> #KTHXBYE
	 * @throws CompilerException
	 */
	void lolcode() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	void comment() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	void head() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException

	 */
	void title() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 */
	void paragraph() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 */
	void bold() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the italics annotation.
	 * @throws CompilerException
	 */
	void italics() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the list annotation.
	 * @throws CompilerException
	 */
	void list() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the item annotation.
	 * @throws CompilerException
	 */
	void item() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @throws CompilerException
	 */
	void audio() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 */
	void video() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the define annotation.
	 * @throws CompilerException
	 */
	void define() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the use annotation.
	 * @throws CompilerException
	 */
	void use() throws CompilerException;
}

