import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class PrettyPrinterVisitor extends SheetGrammarBaseVisitor<String>{
    private int indentLevel = 0;

    private String indent() {
        return "    ".repeat(indentLevel);
    }

    @Override
    public String visitStart(SheetGrammarParser.StartContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (var stmt : ctx.stmt()) {
            sb.append(stmt.accept(this)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String visitStmt(SheetGrammarParser.StmtContext ctx) {
        StringBuilder sb = new StringBuilder();

        for (ParseTree p: ctx.children) {
            sb.append(p.accept(this));
        }
        return sb.toString();
    }

    @Override
    public String visitTerminal(TerminalNode node) {
        return "";
    }

    @Override
    public String visitVardecl(SheetGrammarParser.VardeclContext ctx) {
        return indent() + ctx.IDENT().getText() + " := " + visit(ctx.expr());
    }

    @Override
    public String visitWhile(SheetGrammarParser.WhileContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent()).append("while ");
        indentLevel++;
        sb.append(visit(ctx.expr()));
        sb.append(" do\n");
        for (var stmt : ctx.stmt()) {
            sb.append(visit(stmt)).append("\n");
        }
        indentLevel--;
        sb.append(indent()).append("end");
        return sb.toString();
    }

    @Override
    public String visitCond(SheetGrammarParser.CondContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent()).append("if ");
        indentLevel++;
        sb.append(visit(ctx.expr()));
        sb.append(" do\n");
        for (var stmt : ctx.stmt()) {
            sb.append(visit(stmt)).append("\n"); // Irgendwas ist hier in der Region komisch
        }
        if (ctx.stmt().size() > 1) {
            sb.append(indent()).append("else\n");
            for (var stmt : ctx.stmt()) {
                sb.append(visit(stmt)).append("\n");
            }
        }
        indentLevel--;
        sb.append(indent()).append("end");
        return sb.toString();
    }

    @Override
    public String visitExpr(SheetGrammarParser.ExprContext ctx) {
        if (ctx.INT() != null) return ctx.INT().getText();
        if (ctx.IDENT() != null) return ctx.IDENT().getText();
        if (ctx.STRING() != null) return ctx.STRING().getText();
        if (ctx.expr().size() == 1) return "(" + visit(ctx.expr(0)) + ")";
        if (ctx.expr().size() == 2) {
            String left = visit(ctx.expr(0));
            String right = visit(ctx.expr(1));
            String op = ctx.getChild(1).getText();
            return left + " " + op + " " + right;
        }
        return "";
    }

}
