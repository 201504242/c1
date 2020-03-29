package analizadores;
import java_cup.runtime.Symbol; 
import olc2.p1_201504242.Ventana;
import olc2.p1_201504242.JError;


public class Lexico implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Lexico (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Lexico (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexico () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
 
    yyline = 1; 
    yychar = 1; 
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NOT_ACCEPT,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"2:9,45,4,2:2,3,2:18,50,20,51,1,2,18,24,2,7,8,15,13,6,14,47,16,49:10,26,5,22" +
",19,21,25,2,38,39,36,42,32,31,46,37,30,46,41,29,44,27,43,46:2,40,33,35,28,4" +
"6,34,46:3,9,52,10,17,48,2,38,39,36,42,32,31,46,37,30,46,41,29,44,27,43,46:2" +
",40,33,35,28,46,34,46:3,11,23,12,2:65410,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,125,
"0,1,2,3,1:14,4,5,6,7,1:4,8,9,10,1:5,11:3,1,11,12,11:13,2,1,13,1,14,12,15,16" +
",14:2,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,11,34,35,36,37,38," +
"39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,11," +
"63,64,65,66,67,68,69,70,71,72,73,74,75,76")[0];

	private int yy_nxt[][] = unpackFromString(77,53,
"1,2,54,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,59,18,19,20,21,22,23,24,25,26," +
"110:2,55,78,116,118,120,121,122,110:2,123,124,110,60,110:2,3,110:2,54,27,3," +
"62,54,-1:54,53:2,28,56,53:48,-1:3,3,-1:41,3,-1:4,3,-1:21,30,-1:52,31,-1:52," +
"32,-1:52,33,-1:60,110,79,110:16,-1,110,80,81:2,-1:50,58,-1,27,-1:7,56,-1:75" +
",110:18,-1,110,80,81:2,-1:26,39,-1:25,39:2,-1:29,34,110:3,35,110:13,-1,110," +
"80,81:2,-1:4,61:3,-1,61:46,37,64,-1:18,29,-1:61,110:5,90,110:10,36,110,-1,1" +
"10,80,81:2,-1:30,110:13,38,110:4,-1,110,80,81:2,-1:4,61:3,-1,61:46,57,64,-1" +
":27,110:2,40,110:15,-1,110,80,81:2,-1:30,110:5,41,110:12,-1,110,80,81:2,-1:" +
"30,110:5,42,110:12,-1,110,80,81:2,-1:30,110:5,43,110:12,-1,110,80,81:2,-1:3" +
"0,110:5,44,110:12,-1,110,80,81:2,-1:30,110:5,45,110:12,-1,110,80,81:2,-1:30" +
",110:14,46,110:3,-1,110,80,81:2,-1:30,110:10,47,110:7,-1,110,80,81:2,-1:30," +
"48,110:17,-1,110,80,81:2,-1:30,110:8,49,110:9,-1,110,80,81:2,-1:30,50,110:1" +
"7,-1,110,80,81:2,-1:30,110:5,51,110:12,-1,110,80,81:2,-1:30,110:11,52,110:6" +
",-1,110,80,81:2,-1:30,110,82,110:9,83,110:4,63,110,-1,110,80,81:2,-1:30,110" +
":2,65,110:15,-1,110,80,81:2,-1:30,81:18,-1,81:4,-1:30,91,110:17,-1,110,80,8" +
"1:2,-1:30,113,110,92,110:15,-1,110,80,81:2,-1:30,110:6,66,110:11,-1,110,80," +
"81:2,-1:30,110:3,117,110:14,-1,110,80,81:2,-1:30,110,67,110:16,-1,110,80,81" +
":2,-1:30,110:6,68,110:11,-1,110,80,81:2,-1:30,110:5,94,110:12,-1,110,80,81:" +
"2,-1:30,110:8,95,110:9,-1,110,80,81:2,-1:30,110:4,96,110:13,-1,110,80,81:2," +
"-1:30,110:9,97,110:8,-1,110,80,81:2,-1:30,110:6,69,110:11,-1,110,80,81:2,-1" +
":30,110:2,70,110:15,-1,110,80,81:2,-1:30,110:11,71,110:6,-1,110,80,81:2,-1:" +
"30,110,100,110:16,-1,110,80,81:2,-1:30,110:11,101,110:6,-1,110,80,81:2,-1:3" +
"0,110:8,115,110:9,-1,110,80,81:2,-1:30,110:9,72,110:8,-1,110,80,81:2,-1:30," +
"110:3,103,110:14,-1,110,80,81:2,-1:30,110:13,73,110:4,-1,110,80,81:2,-1:30," +
"110,104,110:16,-1,110,80,81:2,-1:30,110:6,106,110:11,-1,110,80,81:2,-1:30,1" +
"07,110:17,-1,110,80,81:2,-1:30,110:2,74,110:15,-1,110,80,81:2,-1:30,110:16," +
"75,110,-1,110,80,81:2,-1:30,110:17,108,-1,110,80,81:2,-1:30,110,76,110:16,-" +
"1,110,80,81:2,-1:30,110:3,109,110:14,-1,110,80,81:2,-1:30,110:8,77,110:9,-1" +
",110,80,81:2,-1:30,119,110:17,-1,110,80,81:2,-1:30,110:3,93,110:14,-1,110,8" +
"0,81:2,-1:30,110:8,114,110:9,-1,110,80,81:2,-1:30,110:11,102,110:6,-1,110,8" +
"0,81:2,-1:30,110:3,105,110:14,-1,110,80,81:2,-1:30,110:2,84,110:15,-1,110,8" +
"0,81:2,-1:30,110:8,98,110:9,-1,110,80,81:2,-1:30,110:7,85,110:10,-1,110,80," +
"81:2,-1:30,110:8,99,110:9,-1,110,80,81:2,-1:30,110:10,112,110:7,-1,110,80,8" +
"1:2,-1:30,110:13,86,110:4,-1,110,80,81:2,-1:30,110:11,87,110:4,111,110,-1,1" +
"10,80,81:2,-1:30,110:13,88,110:4,-1,110,80,81:2,-1:30,110:5,89,110:12,-1,11" +
"0,80,81:2,-1:3");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
    Ventana pp = (Ventana)olc2.p1_201504242.OLC2P1_201504242.ven.ggetVentana();
    System.out.println("Este es un ERROR lexico: "+yytext()+
    ", en la linea: "+yyline+", en la columna: "+yychar);
    pp.listaError.add(new JError("Lexico",yyline, yychar, "Caracter invalido: "+yytext()));
}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{yychar=1;}
					case -5:
						break;
					case 5:
						{return new Symbol(sym.PTCOMA,yyline,yychar, yytext());}
					case -6:
						break;
					case 6:
						{return new Symbol(sym.COMA,yyline,yychar, yytext());}
					case -7:
						break;
					case 7:
						{return new Symbol(sym.PARIZQ,yyline,yychar, yytext());}
					case -8:
						break;
					case 8:
						{return new Symbol(sym.PARDER,yyline,yychar, yytext());}
					case -9:
						break;
					case 9:
						{return new Symbol(sym.CORIZQ,yyline,yychar, yytext());}
					case -10:
						break;
					case 10:
						{return new Symbol(sym.CORDER,yyline,yychar, yytext());}
					case -11:
						break;
					case 11:
						{return new Symbol(sym.LLAIZQ,yyline,yychar, yytext());}
					case -12:
						break;
					case 12:
						{return new Symbol(sym.LLADER,yyline,yychar, yytext());}
					case -13:
						break;
					case 13:
						{return new Symbol(sym.MAS,yyline,yychar, yytext());}
					case -14:
						break;
					case 14:
						{return new Symbol(sym.MENOS,yyline,yychar, yytext());}
					case -15:
						break;
					case 15:
						{return new Symbol(sym.POR,yyline,yychar, yytext());}
					case -16:
						break;
					case 16:
						{return new Symbol(sym.DIVIDIDO,yyline,yychar, yytext());}
					case -17:
						break;
					case 17:
						{return new Symbol(sym.POTENCIA,yyline,yychar, yytext());}
					case -18:
						break;
					case 18:
						{ return new Symbol(sym.igual,yyline,yychar, yytext());}
					case -19:
						break;
					case 19:
						{ return new Symbol(sym.Tnot,yyline,yychar, yytext());}
					case -20:
						break;
					case 20:
						{return new Symbol(sym.MAYOR,yyline,yychar, yytext());}
					case -21:
						break;
					case 21:
						{return new Symbol(sym.MENOR,yyline,yychar, yytext());}
					case -22:
						break;
					case 22:
						{ return new Symbol(sym.Tor,yyline,yychar, yytext());}
					case -23:
						break;
					case 23:
						{ return new Symbol(sym.Tand,yyline,yychar, yytext());}
					case -24:
						break;
					case 24:
						{ return new Symbol(sym.ternario,yyline,yychar, yytext());}
					case -25:
						break;
					case 25:
						{ return new Symbol(sym.dospuntos,yyline,yychar, yytext());}
					case -26:
						break;
					case 26:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -27:
						break;
					case 27:
						{return new Symbol(sym.ENTERO,yyline,yychar,  new Integer(yytext()) );}
					case -28:
						break;
					case 28:
						{
    //System.out.println("Comentario: "+yytext().trim()+", en la linea: "+yyline+", en la columna: "+yychar);
}
					case -29:
						break;
					case 29:
						{return new Symbol(sym.MODULO,yyline,yychar, yytext());}
					case -30:
						break;
					case 30:
						{return new Symbol(sym.IGUALDAD,yyline,yychar, yytext());}
					case -31:
						break;
					case 31:
						{return new Symbol(sym.DIFERENTE,yyline,yychar, yytext());}
					case -32:
						break;
					case 32:
						{return new Symbol(sym.MAYORIGUAL,yyline,yychar, yytext());}
					case -33:
						break;
					case 33:
						{return new Symbol(sym.MENORIGUAL,yyline,yychar, yytext());}
					case -34:
						break;
					case 34:
						{return new Symbol(sym.Tin,yyline,yychar, yytext());}
					case -35:
						break;
					case 35:
						{return new Symbol(sym.Tif,yyline,yychar, yytext());}
					case -36:
						break;
					case 36:
						{return new Symbol(sym.Tdo,yyline,yychar, yytext());}
					case -37:
						break;
					case 37:
						{return new Symbol(sym.CAD,yyline,yychar, (yytext()).substring(1,yytext().length()-1));}
					case -38:
						break;
					case 38:
						{return new Symbol(sym.Tfor,yyline,yychar, yytext());}
					case -39:
						break;
					case 39:
						{return new Symbol(sym.DECIMAL,yyline,yychar, new Double(yytext()) );}
					case -40:
						break;
					case 40:
						{return new Symbol(sym.Tnull,yyline,yychar, yytext());}
					case -41:
						break;
					case 41:
						{return new Symbol(sym.Telse,yyline,yychar, yytext());}
					case -42:
						break;
					case 42:
						{return new Symbol(sym.ttrue,yyline,yychar, yytext());}
					case -43:
						break;
					case 43:
						{return new Symbol(sym.Tcase,yyline,yychar, yytext());}
					case -44:
						break;
					case 44:
						{return new Symbol(sym.tfalse,yyline,yychar, yytext());}
					case -45:
						break;
					case 45:
						{return new Symbol(sym.Twhile,yyline,yychar, yytext());}
					case -46:
						break;
					case 46:
						{return new Symbol(sym.Tbreak,yyline,yychar, yytext());}
					case -47:
						break;
					case 47:
						{return new Symbol(sym.Tswitch,yyline,yychar, yytext());}
					case -48:
						break;
					case 48:
						{return new Symbol(sym.Treturn,yyline,yychar, yytext());}
					case -49:
						break;
					case 49:
						{return new Symbol(sym.tdefault,yyline,yychar, yytext());}
					case -50:
						break;
					case 50:
						{return new Symbol(sym.Tfuntion,yyline,yychar, yytext());}
					case -51:
						break;
					case 51:
						{return new Symbol(sym.Tcontinue,yyline,yychar, yytext());}
					case -52:
						break;
					case 52:
						{return new Symbol(sym.fantasmita,yyline,yychar, yytext());}
					case -53:
						break;
					case 54:
						{
    Ventana pp = (Ventana)olc2.p1_201504242.OLC2P1_201504242.ven.ggetVentana();
    System.out.println("Este es un ERROR lexico: "+yytext()+
    ", en la linea: "+yyline+", en la columna: "+yychar);
    pp.listaError.add(new JError("Lexico",yyline, yychar, "Caracter invalido: "+yytext()));
}
					case -54:
						break;
					case 55:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -55:
						break;
					case 56:
						{
    //System.out.println("Comentario: "+yytext().trim()+", en la linea: "+yyline+", en la columna: "+yychar);
}
					case -56:
						break;
					case 57:
						{return new Symbol(sym.CAD,yyline,yychar, (yytext()).substring(1,yytext().length()-1));}
					case -57:
						break;
					case 59:
						{
    Ventana pp = (Ventana)olc2.p1_201504242.OLC2P1_201504242.ven.ggetVentana();
    System.out.println("Este es un ERROR lexico: "+yytext()+
    ", en la linea: "+yyline+", en la columna: "+yychar);
    pp.listaError.add(new JError("Lexico",yyline, yychar, "Caracter invalido: "+yytext()));
}
					case -58:
						break;
					case 60:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -59:
						break;
					case 62:
						{
    Ventana pp = (Ventana)olc2.p1_201504242.OLC2P1_201504242.ven.ggetVentana();
    System.out.println("Este es un ERROR lexico: "+yytext()+
    ", en la linea: "+yyline+", en la columna: "+yychar);
    pp.listaError.add(new JError("Lexico",yyline, yychar, "Caracter invalido: "+yytext()));
}
					case -60:
						break;
					case 63:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -61:
						break;
					case 65:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -62:
						break;
					case 66:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -63:
						break;
					case 67:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -64:
						break;
					case 68:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -65:
						break;
					case 69:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -66:
						break;
					case 70:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -67:
						break;
					case 71:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -68:
						break;
					case 72:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -69:
						break;
					case 73:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -70:
						break;
					case 74:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -71:
						break;
					case 75:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -72:
						break;
					case 76:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -73:
						break;
					case 77:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -74:
						break;
					case 78:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -75:
						break;
					case 79:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -76:
						break;
					case 80:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -77:
						break;
					case 81:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -78:
						break;
					case 82:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -79:
						break;
					case 83:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -80:
						break;
					case 84:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -81:
						break;
					case 85:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -82:
						break;
					case 86:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -83:
						break;
					case 87:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -84:
						break;
					case 88:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -85:
						break;
					case 89:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -86:
						break;
					case 90:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -87:
						break;
					case 91:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -88:
						break;
					case 92:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -89:
						break;
					case 93:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -90:
						break;
					case 94:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -91:
						break;
					case 95:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -92:
						break;
					case 96:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -93:
						break;
					case 97:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -94:
						break;
					case 98:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -95:
						break;
					case 99:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -96:
						break;
					case 100:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -97:
						break;
					case 101:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -98:
						break;
					case 102:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -99:
						break;
					case 103:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -100:
						break;
					case 104:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -101:
						break;
					case 105:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -102:
						break;
					case 106:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -103:
						break;
					case 107:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -104:
						break;
					case 108:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -105:
						break;
					case 109:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -106:
						break;
					case 110:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -107:
						break;
					case 111:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -108:
						break;
					case 112:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -109:
						break;
					case 113:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -110:
						break;
					case 114:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -111:
						break;
					case 115:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -112:
						break;
					case 116:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -113:
						break;
					case 117:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -114:
						break;
					case 118:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -115:
						break;
					case 119:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -116:
						break;
					case 120:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -117:
						break;
					case 121:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -118:
						break;
					case 122:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -119:
						break;
					case 123:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -120:
						break;
					case 124:
						{return new Symbol(sym.ID,yyline,yychar, yytext().toLowerCase());}
					case -121:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
