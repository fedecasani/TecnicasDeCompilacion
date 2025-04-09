// Generated from com\compilador\MiLenguaje.g4 by ANTLR 4.9.3
package com.compilador;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiLenguajeLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ID=1, INTEGER=2, FLOAT=3, STRING=4, CHAR=5, BOOLEAN=6, NULL=7, KEYWORD=8, 
		OPERATOR=9, SEPARATOR=10, WS=11, COMMENT=12, BLOCK_COMMENT=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ID", "INTEGER", "FLOAT", "STRING", "CHAR", "BOOLEAN", "NULL", "KEYWORD", 
			"OPERATOR", "SEPARATOR", "WS", "COMMENT", "BLOCK_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ID", "INTEGER", "FLOAT", "STRING", "CHAR", "BOOLEAN", "NULL", 
			"KEYWORD", "OPERATOR", "SEPARATOR", "WS", "COMMENT", "BLOCK_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MiLenguajeLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiLenguaje.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17\u0156\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\7\2 \n\2\f\2\16\2#\13\2\3\3"+
		"\6\3&\n\3\r\3\16\3\'\3\3\5\3+\n\3\3\4\6\4.\n\4\r\4\16\4/\3\4\3\4\7\4\64"+
		"\n\4\f\4\16\4\67\13\4\3\4\5\4:\n\4\3\4\3\4\6\4>\n\4\r\4\16\4?\3\4\5\4"+
		"C\n\4\3\4\6\4F\n\4\r\4\16\4G\3\4\5\4K\n\4\3\5\3\5\3\5\3\5\7\5Q\n\5\f\5"+
		"\16\5T\13\5\3\5\3\5\3\6\3\6\3\6\3\6\5\6\\\n\6\3\6\3\6\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\5\7i\n\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\5\t\u00f2\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n"+
		"\u0133\n\n\3\13\3\13\3\f\6\f\u0138\n\f\r\f\16\f\u0139\3\f\3\f\3\r\3\r"+
		"\3\r\3\r\7\r\u0142\n\r\f\r\16\r\u0145\13\r\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\7\16\u014d\n\16\f\16\16\16\u0150\13\16\3\16\3\16\3\16\3\16\3\16\3\u014e"+
		"\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\3"+
		"\2\20\4\2C\\c|\6\2\62;C\\aac|\3\2\62;\4\2NNnn\6\2FFHHffhh\5\2\f\f\17\17"+
		"$$\6\2\f\f\17\17))^^\7\2\'\',-//\61\61??\4\2>>@@\7\2##((``~~\u0080\u0080"+
		"\4\2<<AA\13\2*+..\60\60==BB]]__}}\177\177\5\2\13\f\17\17\"\"\4\2\f\f\17"+
		"\17\2\u019a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5%\3\2\2\2\7J\3\2\2\2"+
		"\tL\3\2\2\2\13W\3\2\2\2\rh\3\2\2\2\17j\3\2\2\2\21\u00f1\3\2\2\2\23\u0132"+
		"\3\2\2\2\25\u0134\3\2\2\2\27\u0137\3\2\2\2\31\u013d\3\2\2\2\33\u0148\3"+
		"\2\2\2\35!\t\2\2\2\36 \t\3\2\2\37\36\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\""+
		"\3\2\2\2\"\4\3\2\2\2#!\3\2\2\2$&\t\4\2\2%$\3\2\2\2&\'\3\2\2\2\'%\3\2\2"+
		"\2\'(\3\2\2\2(*\3\2\2\2)+\t\5\2\2*)\3\2\2\2*+\3\2\2\2+\6\3\2\2\2,.\t\4"+
		"\2\2-,\3\2\2\2./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\61\3\2\2\2\61\65\7\60"+
		"\2\2\62\64\t\4\2\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2"+
		"\2\2\669\3\2\2\2\67\65\3\2\2\28:\t\6\2\298\3\2\2\29:\3\2\2\2:K\3\2\2\2"+
		";=\7\60\2\2<>\t\4\2\2=<\3\2\2\2>?\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2"+
		"\2AC\t\6\2\2BA\3\2\2\2BC\3\2\2\2CK\3\2\2\2DF\t\4\2\2ED\3\2\2\2FG\3\2\2"+
		"\2GE\3\2\2\2GH\3\2\2\2HI\3\2\2\2IK\t\6\2\2J-\3\2\2\2J;\3\2\2\2JE\3\2\2"+
		"\2K\b\3\2\2\2LR\7$\2\2MQ\n\7\2\2NO\7^\2\2OQ\7$\2\2PM\3\2\2\2PN\3\2\2\2"+
		"QT\3\2\2\2RP\3\2\2\2RS\3\2\2\2SU\3\2\2\2TR\3\2\2\2UV\7$\2\2V\n\3\2\2\2"+
		"W[\7)\2\2X\\\n\b\2\2YZ\7^\2\2Z\\\13\2\2\2[X\3\2\2\2[Y\3\2\2\2\\]\3\2\2"+
		"\2]^\7)\2\2^\f\3\2\2\2_`\7v\2\2`a\7t\2\2ab\7w\2\2bi\7g\2\2cd\7h\2\2de"+
		"\7c\2\2ef\7n\2\2fg\7u\2\2gi\7g\2\2h_\3\2\2\2hc\3\2\2\2i\16\3\2\2\2jk\7"+
		"p\2\2kl\7w\2\2lm\7n\2\2mn\7n\2\2n\20\3\2\2\2op\7e\2\2pq\7n\2\2qr\7c\2"+
		"\2rs\7u\2\2s\u00f2\7u\2\2tu\7r\2\2uv\7w\2\2vw\7d\2\2wx\7n\2\2xy\7k\2\2"+
		"y\u00f2\7e\2\2z{\7r\2\2{|\7t\2\2|}\7k\2\2}~\7x\2\2~\177\7c\2\2\177\u0080"+
		"\7v\2\2\u0080\u00f2\7g\2\2\u0081\u0082\7r\2\2\u0082\u0083\7t\2\2\u0083"+
		"\u0084\7q\2\2\u0084\u0085\7v\2\2\u0085\u0086\7g\2\2\u0086\u0087\7e\2\2"+
		"\u0087\u0088\7v\2\2\u0088\u0089\7g\2\2\u0089\u00f2\7f\2\2\u008a\u008b"+
		"\7u\2\2\u008b\u008c\7v\2\2\u008c\u008d\7c\2\2\u008d\u008e\7v\2\2\u008e"+
		"\u008f\7k\2\2\u008f\u00f2\7e\2\2\u0090\u0091\7x\2\2\u0091\u0092\7q\2\2"+
		"\u0092\u0093\7k\2\2\u0093\u00f2\7f\2\2\u0094\u0095\7k\2\2\u0095\u0096"+
		"\7p\2\2\u0096\u00f2\7v\2\2\u0097\u0098\7d\2\2\u0098\u0099\7q\2\2\u0099"+
		"\u009a\7q\2\2\u009a\u009b\7n\2\2\u009b\u009c\7g\2\2\u009c\u009d\7c\2\2"+
		"\u009d\u00f2\7p\2\2\u009e\u009f\7U\2\2\u009f\u00a0\7v\2\2\u00a0\u00a1"+
		"\7t\2\2\u00a1\u00a2\7k\2\2\u00a2\u00a3\7p\2\2\u00a3\u00f2\7i\2\2\u00a4"+
		"\u00a5\7e\2\2\u00a5\u00a6\7j\2\2\u00a6\u00a7\7c\2\2\u00a7\u00f2\7t\2\2"+
		"\u00a8\u00a9\7h\2\2\u00a9\u00aa\7n\2\2\u00aa\u00ab\7q\2\2\u00ab\u00ac"+
		"\7c\2\2\u00ac\u00f2\7v\2\2\u00ad\u00ae\7f\2\2\u00ae\u00af\7q\2\2\u00af"+
		"\u00b0\7w\2\2\u00b0\u00b1\7d\2\2\u00b1\u00b2\7n\2\2\u00b2\u00f2\7g\2\2"+
		"\u00b3\u00b4\7k\2\2\u00b4\u00f2\7h\2\2\u00b5\u00b6\7g\2\2\u00b6\u00b7"+
		"\7n\2\2\u00b7\u00b8\7u\2\2\u00b8\u00f2\7g\2\2\u00b9\u00ba\7h\2\2\u00ba"+
		"\u00bb\7q\2\2\u00bb\u00f2\7t\2\2\u00bc\u00bd\7y\2\2\u00bd\u00be\7j\2\2"+
		"\u00be\u00bf\7k\2\2\u00bf\u00c0\7n\2\2\u00c0\u00f2\7g\2\2\u00c1\u00c2"+
		"\7f\2\2\u00c2\u00f2\7q\2\2\u00c3\u00c4\7u\2\2\u00c4\u00c5\7y\2\2\u00c5"+
		"\u00c6\7k\2\2\u00c6\u00c7\7v\2\2\u00c7\u00c8\7e\2\2\u00c8\u00f2\7j\2\2"+
		"\u00c9\u00ca\7e\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc\7u\2\2\u00cc\u00f2"+
		"\7g\2\2\u00cd\u00ce\7d\2\2\u00ce\u00cf\7t\2\2\u00cf\u00d0\7g\2\2\u00d0"+
		"\u00d1\7c\2\2\u00d1\u00f2\7m\2\2\u00d2\u00d3\7t\2\2\u00d3\u00d4\7g\2\2"+
		"\u00d4\u00d5\7v\2\2\u00d5\u00d6\7w\2\2\u00d6\u00d7\7t\2\2\u00d7\u00f2"+
		"\7p\2\2\u00d8\u00d9\7p\2\2\u00d9\u00da\7g\2\2\u00da\u00f2\7y\2\2\u00db"+
		"\u00dc\7v\2\2\u00dc\u00dd\7j\2\2\u00dd\u00de\7k\2\2\u00de\u00f2\7u\2\2"+
		"\u00df\u00e0\7u\2\2\u00e0\u00e1\7w\2\2\u00e1\u00e2\7r\2\2\u00e2\u00e3"+
		"\7g\2\2\u00e3\u00f2\7t\2\2\u00e4\u00e5\7k\2\2\u00e5\u00e6\7o\2\2\u00e6"+
		"\u00e7\7r\2\2\u00e7\u00e8\7q\2\2\u00e8\u00e9\7t\2\2\u00e9\u00f2\7v\2\2"+
		"\u00ea\u00eb\7r\2\2\u00eb\u00ec\7c\2\2\u00ec\u00ed\7e\2\2\u00ed\u00ee"+
		"\7m\2\2\u00ee\u00ef\7c\2\2\u00ef\u00f0\7i\2\2\u00f0\u00f2\7g\2\2\u00f1"+
		"o\3\2\2\2\u00f1t\3\2\2\2\u00f1z\3\2\2\2\u00f1\u0081\3\2\2\2\u00f1\u008a"+
		"\3\2\2\2\u00f1\u0090\3\2\2\2\u00f1\u0094\3\2\2\2\u00f1\u0097\3\2\2\2\u00f1"+
		"\u009e\3\2\2\2\u00f1\u00a4\3\2\2\2\u00f1\u00a8\3\2\2\2\u00f1\u00ad\3\2"+
		"\2\2\u00f1\u00b3\3\2\2\2\u00f1\u00b5\3\2\2\2\u00f1\u00b9\3\2\2\2\u00f1"+
		"\u00bc\3\2\2\2\u00f1\u00c1\3\2\2\2\u00f1\u00c3\3\2\2\2\u00f1\u00c9\3\2"+
		"\2\2\u00f1\u00cd\3\2\2\2\u00f1\u00d2\3\2\2\2\u00f1\u00d8\3\2\2\2\u00f1"+
		"\u00db\3\2\2\2\u00f1\u00df\3\2\2\2\u00f1\u00e4\3\2\2\2\u00f1\u00ea\3\2"+
		"\2\2\u00f2\22\3\2\2\2\u00f3\u0133\t\t\2\2\u00f4\u00f5\7-\2\2\u00f5\u0133"+
		"\7?\2\2\u00f6\u00f7\7/\2\2\u00f7\u0133\7?\2\2\u00f8\u00f9\7,\2\2\u00f9"+
		"\u0133\7?\2\2\u00fa\u00fb\7\61\2\2\u00fb\u0133\7?\2\2\u00fc\u00fd\7\'"+
		"\2\2\u00fd\u0133\7?\2\2\u00fe\u00ff\7-\2\2\u00ff\u0133\7-\2\2\u0100\u0101"+
		"\7/\2\2\u0101\u0133\7/\2\2\u0102\u0103\7?\2\2\u0103\u0133\7?\2\2\u0104"+
		"\u0105\7#\2\2\u0105\u0133\7?\2\2\u0106\u0133\t\n\2\2\u0107\u0108\7>\2"+
		"\2\u0108\u0133\7?\2\2\u0109\u010a\7@\2\2\u010a\u0133\7?\2\2\u010b\u010c"+
		"\7(\2\2\u010c\u0133\7(\2\2\u010d\u010e\7~\2\2\u010e\u0133\7~\2\2\u010f"+
		"\u0133\t\13\2\2\u0110\u0111\7>\2\2\u0111\u0133\7>\2\2\u0112\u0113\7@\2"+
		"\2\u0113\u0133\7@\2\2\u0114\u0115\7@\2\2\u0115\u0116\7@\2\2\u0116\u0133"+
		"\7@\2\2\u0117\u0118\7(\2\2\u0118\u0133\7?\2\2\u0119\u011a\7~\2\2\u011a"+
		"\u0133\7?\2\2\u011b\u011c\7`\2\2\u011c\u0133\7?\2\2\u011d\u011e\7>\2\2"+
		"\u011e\u011f\7>\2\2\u011f\u0133\7?\2\2\u0120\u0121\7@\2\2\u0121\u0122"+
		"\7@\2\2\u0122\u0133\7?\2\2\u0123\u0124\7@\2\2\u0124\u0125\7@\2\2\u0125"+
		"\u0126\7@\2\2\u0126\u0133\7?\2\2\u0127\u0133\t\f\2\2\u0128\u0129\7k\2"+
		"\2\u0129\u012a\7p\2\2\u012a\u012b\7u\2\2\u012b\u012c\7v\2\2\u012c\u012d"+
		"\7c\2\2\u012d\u012e\7p\2\2\u012e\u012f\7e\2\2\u012f\u0130\7g\2\2\u0130"+
		"\u0131\7q\2\2\u0131\u0133\7h\2\2\u0132\u00f3\3\2\2\2\u0132\u00f4\3\2\2"+
		"\2\u0132\u00f6\3\2\2\2\u0132\u00f8\3\2\2\2\u0132\u00fa\3\2\2\2\u0132\u00fc"+
		"\3\2\2\2\u0132\u00fe\3\2\2\2\u0132\u0100\3\2\2\2\u0132\u0102\3\2\2\2\u0132"+
		"\u0104\3\2\2\2\u0132\u0106\3\2\2\2\u0132\u0107\3\2\2\2\u0132\u0109\3\2"+
		"\2\2\u0132\u010b\3\2\2\2\u0132\u010d\3\2\2\2\u0132\u010f\3\2\2\2\u0132"+
		"\u0110\3\2\2\2\u0132\u0112\3\2\2\2\u0132\u0114\3\2\2\2\u0132\u0117\3\2"+
		"\2\2\u0132\u0119\3\2\2\2\u0132\u011b\3\2\2\2\u0132\u011d\3\2\2\2\u0132"+
		"\u0120\3\2\2\2\u0132\u0123\3\2\2\2\u0132\u0127\3\2\2\2\u0132\u0128\3\2"+
		"\2\2\u0133\24\3\2\2\2\u0134\u0135\t\r\2\2\u0135\26\3\2\2\2\u0136\u0138"+
		"\t\16\2\2\u0137\u0136\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u0137\3\2\2\2"+
		"\u0139\u013a\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013c\b\f\2\2\u013c\30"+
		"\3\2\2\2\u013d\u013e\7\61\2\2\u013e\u013f\7\61\2\2\u013f\u0143\3\2\2\2"+
		"\u0140\u0142\n\17\2\2\u0141\u0140\3\2\2\2\u0142\u0145\3\2\2\2\u0143\u0141"+
		"\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0146\3\2\2\2\u0145\u0143\3\2\2\2\u0146"+
		"\u0147\b\r\2\2\u0147\32\3\2\2\2\u0148\u0149\7\61\2\2\u0149\u014a\7,\2"+
		"\2\u014a\u014e\3\2\2\2\u014b\u014d\13\2\2\2\u014c\u014b\3\2\2\2\u014d"+
		"\u0150\3\2\2\2\u014e\u014f\3\2\2\2\u014e\u014c\3\2\2\2\u014f\u0151\3\2"+
		"\2\2\u0150\u014e\3\2\2\2\u0151\u0152\7,\2\2\u0152\u0153\7\61\2\2\u0153"+
		"\u0154\3\2\2\2\u0154\u0155\b\16\2\2\u0155\34\3\2\2\2\26\2!\'*/\659?BG"+
		"JPR[h\u00f1\u0132\u0139\u0143\u014e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}