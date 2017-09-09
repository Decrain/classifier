package decrain;


import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Decrain on 2017/8/1.
 */


public class test2 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Tokenizer tokenizer = new Tokenizer() ;
        List<Token> tokens = tokenizer.tokenize("半年後に銀行融資を受けたいのだが、今から何を準備すればいいのか？");
        for (Token token : tokens) {
            System.out.println(token.getSurface());
        }
        Set<Character.UnicodeBlock> japaneseUnicodeBlocks = new HashSet<Character.UnicodeBlock>()
        {{
            add(Character.UnicodeBlock.HIRAGANA);
            add(Character.UnicodeBlock.KATAKANA);
            add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
        }};

       /* String mixed = "ラドクリフ、マラソン五輪代表に1万m出場にも含み";
        isJapanese.isJapanese(mixed);

        for (char c : mixed.toCharArray())
        {
            if (japaneseUnicodeBlocks.contains(Character.UnicodeBlock.of(c)))
            {
                System.out.println(c + " is a Japanese character");
            }
            else
            {
                System.out.println(c + " is not a Japanese character");
            }
        }*/

    }
}

