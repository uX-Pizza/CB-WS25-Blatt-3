import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class GrammarPrettyPrinter {
  public static void main(String[] args) throws IOException, URISyntaxException {
    // Einlesen über den Classpath
    CharStream charStream;
    try (InputStream in = Main.class.getResourceAsStream("/SheetGrammar/instructions.txt")) {
        String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        charStream = CharStreams.fromString(text);
        IO.println("/SheetGrammar/instructions.txt");
        IO.println(text);
    }

    SheetGrammarLexer lexer = new SheetGrammarLexer(charStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SheetGrammarParser parser = new SheetGrammarParser(tokens);

    ParseTree tree = parser.start();
    IO.println(tree.toStringTree(parser));

    PrettyPrinterVisitor p = new PrettyPrinterVisitor();
    IO.println(tree.accept(p));
  }
}
