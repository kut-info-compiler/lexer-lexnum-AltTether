import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/*
 * 文字列から数値を読み取るプログラム
 *   0                           -> 整数 (例: 0)
 *   [1-9][0-9]*                 -> 整数 (例: 100)
 *   0[xX][0-9a-fA-F]+           -> 整数 (例: 0xabc)
 *   [0-9]*[a-fA-F][0-9a-fA-F]*  -> 整数 (例: 0123456789a)
 *   [1-9][0-9]*\.[0-9]*         -> 小数 (例: 10.3)
 *   0\.[0-9]*                   -> 小数 (例: 0.12)
 *   \.[0-9]+                    -> 小数 (例: .12)
 *
 * ^([1-9][0-9]*|(0[xX])?(([0-9]*[a-f][0-9a-fA-F]*)|0)|0) INT
 * ^((([1-9][0-9]*)|0)?\.[0-9]*) DEC
 */

public class Lexer {
	static class Token {
		static final String TYPE_INT = "INT";
		static final String TYPE_DEC = "DEC";
		static final String TYPE_ERR = "ERR";

    Token(String tokenType, int start, int len) {
			this.tokenType = tokenType;
			this.start = start;
			this.len = len;
		}

		String tokenType;  /* トークンの種類 */
		int start;         /* 文字列中のトークン開始位置 */
		int len;           /* トークンの長さ */
	}

	/*
	 * 文字列 str を字句解析しトークンを一つ返す
	 */
  Token getToken(String str) {
      String regex = "^([1-9]+[0-9]*\\.[1-9]*|[1-9][0-9]*|[0-9]*[a-fA-F][0-9a-fA-F]*|[1-9]+[0-9]*\\.|0\\.[0-9]*|\\.[0-9]+|0[xX][0-9a-fA-F]+|0)";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(str);
      String acceptMarker = Token.TYPE_ERR;
      int start = 0;
      int acceptPos = 0;
      if (matcher.find()) {
          String matchStr = matcher.group();
          start = str.indexOf(matchStr);
          acceptPos = matchStr.length();
          if (matchStr.indexOf(".") == -1) {
              acceptMarker = Token.TYPE_INT;
          } else {
              acceptMarker = Token.TYPE_DEC;
          }
      }

		return new Token(acceptMarker, start, acceptPos - start);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();  /* 1行読み取る */
		Lexer lex = new Lexer();
		Token t = lex.getToken(str);
		System.out.print(t.tokenType);
		System.out.println(str.substring(t.start, t.start + t.len));
	}
}
