/*
 * @(#)CobolParser.java	 0.1.0
 *
 * Copyright (c) 2019 Julian M. Bass
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */
package cobol;

import parse.Alternation;
import parse.Empty;
import parse.Parser;
import parse.Repetition;
import parse.Sequence;
import parse.tokens.CaselessLiteral;
import parse.tokens.Num;
import parse.tokens.QuotedString;
import parse.tokens.Symbol;
import parse.tokens.Tokenizer;
import parse.tokens.Word;

public class CobolParser {
	/**
	 * Return a parser that will recognize the selected COBOL source code constructs:
	 * 
	 * 
	 * This parser creates a <code>COBOL</code> object
	 * as an assembly's target.
	 *
	 * @return a parser that will recognize and build a 
	 *         <object>COBOL</object> from a source code file.
	 */
	public Parser cobol() {
		Alternation a = new Alternation();	
		Symbol fullstop = new Symbol('.');
		fullstop.discard();
		
		a.add(constantValue() );
		
		a.add(CommentLine());
		
		a.add( ProgramID() );
		
		a.add( DivisionName() );
		
		a.add( SectionName() );
		
		a.add( DateWritten() );
		
		a.add(Display());
		
		a.add(StringDeclaration());
		
		a.add(VariableDeclaration());
		
		a.add(new Empty());
		return a;
	}
	/*
	* Return a parser that will recognize the grammar:
	* 
	* ***--- comment text
	*
	*/
	protected Parser CommentLine() {
		//System.out.println("commentLine()");
		Sequence s = new Sequence();
		s.add(new Symbol("*").discard());
		s.add(new Symbol("*").discard());
		s.add(new Symbol("*").discard());
		s.add(new Symbol("-").discard());
		s.add(new Symbol("-").discard());
		s.add(new Symbol("-").discard());
		
		Alternation alternation = new Alternation();
		alternation.add(new Word());
		alternation.add(new Symbol("("));
		alternation.add(new Symbol(")"));
		alternation.add(new Num());
		
		Repetition repe = new Repetition(alternation);		
		s.add(repe);
		
		s.setAssembler(new CommentLineAssembler());
		return s;
	}

	protected Parser constantValue() {
		//System.out.println("constantValue()");
		Sequence sequence = new Sequence();
		sequence.add(new Num() );
		sequence.add(new Word() );
		sequence.add(new CaselessLiteral("value") );
		sequence.add(new Num() );
		sequence.setAssembler(new ConstantValueAssembler());
		return sequence;
	}


	protected Parser ProgramID() {
		Sequence sequence = new Sequence();
		sequence.add(new CaselessLiteral("program-id") );
		sequence.add(new Symbol('.').discard());	
		sequence.add(new Word().setAssembler(new Program_idAssembler()));
		return sequence;
	}

	protected Parser VariableDeclaration() {
		Sequence sequence = new Sequence();
		sequence.add(new Num());
		sequence.add(new Word());
		sequence.add(new CaselessLiteral("pic"));
		sequence.add(new Num());
		sequence.add(new Symbol("("));
		sequence.add(new Num());
		sequence.add(new Symbol(")"));
		
		sequence.setAssembler(new VariableAssembler());
		return sequence;
	}
	

	/*
	 * Return a parser that will recognise the grammar:
	 * 
	 *    <divisionName> division
	 *
	 */
	protected Parser DivisionName() {
		Sequence s = new Sequence();
		s.add(new Word().setAssembler(new DivisionAssembler()));
		s.add(new CaselessLiteral("division") );
		s.add(new Symbol('.').discard());
		return s;
	}
	
	/*
	 * Return a parser that will recognize the grammar:
	 * 
	 *    Section Name = Word
	 *
	 */
	protected Parser SectionName() {
		Sequence s = new Sequence();
		s.add(new Word().setAssembler(new SectionNameAssembler()));
		s.add(new CaselessLiteral("section") );
		s.add(new Symbol('.').discard());	

		return s;
	}
	
	/*
	 * Return a parser that will recognise the grammar:
	 * 
	 *    working-storage section
	 *
	 */
	protected Parser DateWritten() {
		Sequence s = new Sequence();
		s.add(new CaselessLiteral("date-written") );
		s.add(new Symbol('.').discard());
		s.add(new Num());
		s.add(new Symbol('-').discard());

		//This next Word actually contains month and year (which are extracted in DateAssembler
		s.add(new Word());
		s.add(new Symbol('-').discard());
		s.add(new Word().discard());
		s.add(new Symbol('.').discard());
		s.setAssembler(new DateAssembler());
		return s;
	}


	/**
	 * Return the primary parser for this class -- cobol().
	 *
	 * @return the primary parser for this class -- cobol()
	 */
	public static Parser start() {
		return new CobolParser().cobol();
	}

	/**
	 * Returns a tokenizer that does not allow spaces to appear inside
	 * the "words" that identify cobol's grammar.
	 *
	 * @return a tokenizer that does not allow spaces to appear inside
	 * the "words" that identify cobol grammar.
	 */
	public static Tokenizer tokenizer() {
		Tokenizer t = new Tokenizer();
		t.wordState().setWordChars(' ', ' ', false);
		return t;
	}

	/*
	* Return a parser that will recog the grammar:
	*
	* display <Text to Display>
	*
	*/
	protected Parser Display() {
		Sequence sequence = new Sequence();
		sequence.add(new CaselessLiteral("display").discard());
		
		Alternation alternation = new Alternation();
		alternation.add(new Word());
		alternation.add(new QuotedString());		
		Repetition repetition = new Repetition(alternation);
		
		sequence.add(repetition);		
		sequence.setAssembler(new DisplayAssembler());
		return sequence;
	}
	
	protected Parser StringDeclaration() {
		Sequence sequence = new Sequence();
		sequence.add(new Num());
		sequence.add(new Word());
		sequence.add(new CaselessLiteral("pic"));
		sequence.add(new CaselessLiteral("x"));
		sequence.add(new Symbol("("));
		sequence.add(new Num());
		sequence.add(new Symbol(")"));
		sequence.add(new CaselessLiteral("value"));
		sequence.add(new QuotedString());
		
		sequence.setAssembler(new StringAssembler());
		return sequence;
	}
	
	
	


	

}
