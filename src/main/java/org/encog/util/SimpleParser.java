package org.encog.util;

public class SimpleParser {
	
    private String line;
    private int currentPosition;
    private int marked;

    public SimpleParser(String line)
    {
        this.line = line;
    }

    public int remaining()
    {
        return Math.max(this.line.length() - this.currentPosition, 0);
    }

    public boolean parseThroughComma()
    {
        eatWhiteSpace();
        if (!eol())
        {
            if (peek() == ',')
            {
                advance();
                return true;
            }
        }

        return false;
    }

    public boolean isIdentifier()
    {
        if (eol())
            return false;

        return Character.isLetterOrDigit(peek()) || peek() == '_';
    }

    public char peek()
    {
        if (eol())
            return (char)0;
        else if (currentPosition >= this.line.length())
            return (char)0;
        else
            return this.line.charAt(this.currentPosition);
    }

    public void advance()
    {
        if (currentPosition < this.line.length())
        {
            currentPosition++;
        }
    }

    public boolean isWhiteSpace()
    {
        return " \t\n\r".indexOf(peek()) != -1;
    }

    public boolean eol()
    {
        return (this.currentPosition >= this.line.length());
    }

    public void eatWhiteSpace()
    {
        while (!eol() && isWhiteSpace())
            advance();
    }

    public char readChar()
    {
        if (eol())
            return (char)0;

        char ch = peek();
        advance();
        return ch;
    }

    public String readToWhiteSpace()
    {
        StringBuilder result = new StringBuilder();

        while (!isWhiteSpace() && !eol())
        {
            result.append(readChar());
        }

        return result.toString();
    }

    public boolean lookAhead(String str, boolean ignoreCase)
    {
        if (remaining() < str.length())
            return false;
        for (int i = 0; i < str.length(); i++)
        {
            char c1 = str.charAt(i);
            char c2 = this.line.charAt(this.currentPosition+i);

            if (ignoreCase)
            {
                c1 = Character.toLowerCase(c1);
                c2 = Character.toLowerCase(c2);
            }

            if (c1 != c2)
                return false;
        }

        return true;
    }



    public void advance(int p)
    {
        this.currentPosition=Math.min(line.length(),this.currentPosition+p);
    }

    public void mark()
    {
        this.marked = this.currentPosition;
    }

    public void reset()
    {
        this.currentPosition = this.marked;
    }

	public String readQuotedString() {
		
		if( peek()!='\"')
			return "";
		
		StringBuilder result = new StringBuilder();
		
		advance();
		while( peek()!='\"' && !this.eol() ) {
			result.append(readChar());
		}
		advance();
		
		return result.toString();
	}
}