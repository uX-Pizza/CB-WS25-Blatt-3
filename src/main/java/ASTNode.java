import java.util.List;

abstract class ASTNode {}
class ASTProgram extends ASTNode { List<ASTNode> statements; }
class ASTVarDecl extends ASTNode { String name; ASTExpr expr; }
class ASTWhile extends ASTNode { ASTExpr condition; List<ASTNode> body; }
class ASTIf extends ASTNode { ASTExpr condition; List<ASTNode> thenBlock; List<ASTNode> elseBlock; }
abstract class ASTExpr extends ASTNode {}
class ASTBinaryExpr extends ASTExpr { ASTExpr left; String op; ASTExpr right; }
class ASTInt extends ASTExpr { int value; }
class ASTIdent extends ASTExpr { String name; }
