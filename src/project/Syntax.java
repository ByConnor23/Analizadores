package project;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Syntax {
    private final List<String> tokens;
    private int index;

    public Syntax(List<String> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    public String parse() {
        try {
            A();
            if (index != tokens.size()) {
                throw new Exception("Error: código fuente no válido");
            }
            return "Análisis sintáctico existoso.";
        } catch (Exception e) {
            return "Error de análisis sintáctico: " + e.getMessage();
        }
    }

    private void A() throws Exception {
        B();
        C();
        D();
        E();
        F();
    }

    private void B() throws Exception {
        if (match("public")) {
            consume();
        } else {
            throw new Exception("Se esperaba un 'public'");
        }
    }

    private void C() throws Exception {
        if (match("static")) {
            consume();
        } else {
            throw new Exception("Se esperaba 'static'");
        }
    }

    private void D() throws Exception {
        if (match("void")) {
            consume();
        } else {
            throw new Exception("Se esperaba 'void'");
        }
    }

    private void E() throws Exception {
        if (match("main")) {
            consume();
        } else {
            throw new Exception("Se esperaba 'main'");
        }
        //()
        PA();
        PC();
    }



    private void F() throws Exception {
        if (match("\\{")) {
            consume();
            CD();
            if (match("\\}")) {
                consume();
            } else {
                throw new Exception("Se esperaba '}'");
            }
        } else {
            throw new Exception("Se esperaba '{'");
        }
    }

    private void CD() throws Exception{
        if(match("define")) {
            consume();
            N();
            CD();
        } else if(match("Screen")){
            consume();
            AI();
            CD();
        }else if (match("Image") || match("Sound")){
            consume();
            AC();
            CD();
        }
    }

    private void N() throws Exception{
        if (match("int") || match("double") || match("string") || match("bool")) {
            consume();
            T();
            IG();
            R();
        } else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            consume();
            X();
        }else {
            throw new Exception("Se esperaba otro valor v1");
        }
    }


    private void T() throws Exception{
        System.out.println(tokens.get(index));
        if (match("public") || match("static") || match("void") || match("main")
                || match("int") || match("double") || match("string") || match("bool") || match("false")
                || match("true") || match("If") || match("else") || match("breaker") || match("Menu")
                || match("Screen") || match("go") || match("define") || match("color") || match("Image")
                || match("Sound") || match("setBackground") || match("playSound") || match("stopSound")
                || match("Character")) {
            throw new Exception("No se puede usar palabras reservadas");
        } else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            consume();
        } else {
            throw new Exception("Identificador no valido");
        }
    }
    private void R() throws Exception{
        if(match("\\d+")){
            consume();
        } else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            consume();
        }else{
            throw new Exception("Se esperaba otro valor");
        }
        //Punto y coma
        YC();
    }

    private void X() throws Exception{
        IG();
        if (match("Character")){
            consume();
        }else {
            throw new Exception("Se esperaba 'Character'");
        }
        S();
    }

    private void S() throws Exception{
        PA();
        DD();
        PC();
        YC();
    }

    private void DD() throws Exception{
        P();
        if (match(",")){
            consume();
        }else {
            throw new Exception("Se esperaba una ','");
        }
        U();
    }

    private void U() throws Exception{
        if (match("Color")){
            consume();
        }else{
            throw new Exception("Se esperaba una 'Color'");
        }
        DE();
    }

    private void DE() throws Exception{
        IG();
        P();////////////////////////////////////////////
    }

    private void BQ() throws Exception{
        if(match("/")){
            consume();
        }else{
            throw new Exception("Se esperaba un #");
        }
        P();
    }

    private void P() throws Exception{
        if (match("(['\"])(.*?)\\1")){
            consume();
        }else{
            throw new Exception("Se esperaba una Cadena válida");
        }
    }

    private void IG() throws Exception{
        if(match("=")){
            consume();
        }else{
            throw new Exception("Se esperaba '='");
        }
    }

    private void AC() throws Exception{
        T();
        IG();
        P();
        YC();
    }

    private void AI() throws Exception{
        T();
        PA();
        PC();
        AJ();
    }

    private void AJ() throws Exception{
        if (match("\\{")) {
            consume();
            CE();
            if (match("\\}")) {
                consume();
            } else {
                throw new Exception("Se esperaba '}'");
            }
        } else {
            throw new Exception("Se esperaba '{'");
        }
    }

    private void YC() throws Exception{
        System.out.println("Token actual: " + tokens.get(index));
        if (match(";")){
            consume();
        }else{
            throw new Exception("Se esperaba ';'");
        }
    }

    private void PA() throws Exception{
        if (match("\\(")){
            consume();
        }else{
            throw new Exception("Se esperaba '('");
        }
    }

    private void PC() throws Exception{
        if (match("\\)")){
            consume();
        }else{
            throw new Exception("Se esperaba ')'");
        }
    }

    public void CE()throws Exception{
        System.out.println("Para por CE");
        if(match("define")){
            consume();
            N();
            CE();
        }else if(match("int") || match("double") || match("string") || match("bool")){
            consume();
            T();
            if(match("=")){
                consume();
                CE();
            }
            YC();
            CE();
        }

        if(match("\\(")){
            System.out.println("pasa");
            consume();
            if(match("(['\"])(.*?)\\1")){
                consume();
                PC();
                YC();
                CE();
            } else if(match("\\(") || match("\\d+") || match("[a-zA-Z_][a-zA-Z0-9_]*")){
                K();
                CE();
            }

        }

        if(match("\\d+")){
            consume();
            KP();
            CE();
        }
        if(match("setBackground") || match("playSound") || match("stopSound") || match("show") || match("hide")){
            AL();
            CE();
        }


        if (match("Image") || match("Sound")){
            consume();
            AC();
            CE();
        }else if (match("Menu")){
            consume();
            if(match(":")){
                consume();
            }else{
                throw new Exception("Esperando ':'");
            }
            AO();
            CE();

        }else if(match("if")){
            BB();
            CE();

        }else if(match("for")){
            consume();
            PA();
            BK();
            PC();
            BN();
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            System.out.println("------------?");
            consume();
            //
            if(match("[+*-/]")){
                System.out.println("asina");
                consume();
                L();
                KP();
                YC();
            }

            if (match("=")){
                consume();
                R();
            }

            if (match("\\(")){

                //consume();
                PA();
                P();
                PC();
                YC();
                System.out.println("aqui termina (string)");
            }else {
                throw new Exception("Se esperaba '('");
            }
            CE();


        }
    }

    public void BK() throws Exception{
        BL();
        YC();
        BM();
        YC();
        BP();
    }

    public void BL() throws Exception{
        T();
        IG();
        if (match("\\d+")){
            consume();
        }else {
            throw new Exception("Se esperaba un número");
        }
    }

    public void BM() throws Exception{
        T();
        BU();
        R();
    }

    public void BU() throws Exception{
        System.out.println(tokens.get(index));
        if (match("<") || match(">") || match(">=") || match("<=")){
            consume();
            T();
        }else{
            throw new Exception("Se esperaba un signo de comparación");
        }

    }

    public void BP() throws Exception{
        T();
        if (match("++") || match("--")){
            consume();
        } else if(match("+") || match("-")){
            consume();
            R();
        }else{
            throw new Exception("Se esperaba un incremento valido");
        }

    }

    private void BN() throws Exception{
        if (match("\\{")){
            consume();
            BO();
            if (match("\\}")){
                consume();
            }else{
                throw  new Exception("Se esperaba '}'");
            }
        }else {
            throw new Exception("Se esperaba '{'");
        }
    }

    private void BO() throws Exception{
        if (match("if")){
            consume();
            BO();
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            consume();
            //
            if(match("+=") || match("-=") || match("==") || match("/=")
                    || match(("*="))){
                consume();
                T();
            }

            if(match("[+*-/]")){
                consume();
                L();
                KP();
                YC();
            }

            if (match("\\(")){
                consume();
                PA();
                P();
                PC();
                YC();
            }else {
                throw new Exception("Se esperaba '('");
            }

        }

        if(match("\\(")){
            consume();
            if(match("(['\"])(.*?)\\1")){
                consume();
                PC();
                YC();
            }
            if(match("\\(") || match("\\d+") || match("[a-zA-Z_][a-zA-Z0-9_]*")){
                K();
                CE();
            }

        }

    }

    public void K() throws Exception{
        L();
        KP();
        YC();
    }

    public void KP()throws Exception{
        if(match("[+*-/]")){
            consume();
            L();
            KP();
            YC();
        }
    }

    public void L()throws Exception{
        if(match("\\(")){
            consume();
            K();
            if(match("\\)")){
                consume();
            }else {
                throw new Exception("Se espera una ')'");
            }
        } else if (match("[a-zA-Z_][a-zA-Z0-9_]*")) {
            consume();
        } else if (match("\\d+")) {
            consume();
        }else {
            throw new Exception("Se esperaba '(', identificador o numero");
        }
    }

    public void AL()throws Exception{
        if(match("setBackground") || match("playSound") || match("stopSound") || match("show") || match("hide")){
            consume();
            AN();
            YC();
        }else{
            throw new Exception("Se esperaba setBackground, playSound,stopSound,show, hide");
        }
    }

    public void AN()throws Exception{
        PA();
        T();
        PC();
    }

    public void AP()throws Exception{
        if(match(":")){
            consume();
        }else {
            throw new Exception("Se espera un ':'");
        }
        AO();
    }

    public void AO()throws Exception{

        if(match("\\(")){
            DF();//("")
            if(match(":")){
                consume();
                AT();//...
                LL();
                //AO();
            }else {
                throw new Exception("Esperando ':'");
            }

        }

    }

    public void DF()throws Exception{
        PA();
        P();
        PC();
    }

    public void AT()throws Exception{
        if(match("\\(")){
            consume();
            if(match("(['\"])(.*?)\\1")){

                P();
                PC();
                YC();
                AT();
            }
            if(match("\\(") || match("[a-zA-Z_][a-zA-Z0-9_]*") || match("\\d+")){

                K();
                AT();
            }
        }
        if(match("setBackground") || match("playSound") || match("stopSound") || match("show") || match("hide")){
            consume();
            AL();
            AT();
        }
        if(match("if")){

            BB();
            AT();
        }
        if(match("\\d+")){
            K();
            AT();
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            consume();
            //
            if(match("[+*-/]")){

                L();
                KP();
                YC();
                AT();
            }

            if(match("\\(")){

                P();
                PC();
                YC();
                AT();
            }
        }

    }

    public void LL() throws Exception{
        if(match("go")){
            PP();
        }else if (match("breaker")){
            AY();
        }
    }

    public void PP()throws Exception{
        if(match("go")){
            consume();
        }else{
            throw new Exception("Se espera go");
        }
        T();
        YC();
    }

    public void AY()throws Exception {
        if (match("breaker")) {
            consume();
            YC();
        } else {
            throw new Exception("Esperando breaker");
        }

    }

    public void BB()throws Exception{// BB de funcion no se refuere a mi xd
        if(match("if")){
            consume();
        }else {
            throw new Exception("Esperando palabra 'if'");
        }
        PA();
        BC();
        PC();
        BF();
        BI();
    }

    public void BC()throws Exception{
        T();
        BD();
    }

    public void BD()throws Exception{
        BU();
    }

    public void BF()throws Exception{
        if(match("\\{")){
            consume();
        }else {
            throw new Exception("Esperando {");
        }
        CE();

        if(match("\\}")){
            consume();
        }else {
            throw new Exception("Esperando }");
        }
    }

    public void BI()throws Exception{
        if(match("else")){
            consume();
            if(match("\\{")){
                BF();
            } else if (match("if")) {
                BB();
            }
        }
    }

    // Métodos auxiliares
    private boolean match(String expectedToken) {
        //System.out.println(tokens.get(index));
        if (index < tokens.size()) {
            String token = tokens.get(index);
            Pattern pattern = Pattern.compile(expectedToken);
            Matcher matcher = pattern.matcher(token);
            return matcher.matches();
        }
        return false;
    }

    private void consume() {
        System.out.print("pec");
        System.out.println(tokens.get(index));
        index++;
    }
}

