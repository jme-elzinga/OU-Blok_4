package adOpdr1bCore;

import java.util.HashMap;
import java.util.Map;

/**
 * Representeert een binaire rekenkundige operatie.
 * @author Stijn de Gouw
 */
public enum Operation {
        /**
         * Constante voor operator +
         */
        PLUS("+") {
                @Override
                public int apply(int x, int y) {
                        return x + y;
                }
        },
        /**
         * Constante voor operator -
         */
        MIN("-") {
                @Override
                public int apply(int x, int y) {
                        return x - y;
                }
        },
        /**
         * Constante voor operator *
         */
        MAAL("*") {
                @Override
                public int apply(int x, int y) {
                        return x * y;
                }
        },
        /**
         * Constante voor operator /
         */
        DELING("/") {
                @Override
                public int apply(int x, int y) throws PostfixException {
                        if (y == 0) {
                                throw new PostfixException("Deling door 0 is niet toegestaan");
                        }
                        return x / y;
                }
        };

        private final String inhoud;

        private Operation(String inhoud) {
                this.inhoud = inhoud;
        }

        private static final Map<String, Operation> stringToOp;
        
        static {
                stringToOp = new HashMap<String, Operation>();
                for(Operation op: Operation.values()) {
                        stringToOp.put(op.toString(), op);
                }
        }

        /**
         * Past deze binaire rekenkundige operatie toe op de meegegeven (integer) waarden.
         * 
         * @param x  het eerste argument van de operatie.
         * @param y  het tweede argument van de operatie.
         * @return  het resultaat na toepassing van deze operatie op de meegegeven argumenten
         * @throws  PostfixException als de operatie ongedefinieerd is op de argumenten (bv. deling door 0)
         */
        public abstract int apply(int x, int y) throws PostfixException;

        /**
         * Returnt het wiskundige symbool voor deze operatie als een string.
         * @return het wiskundige symbool voor deze operatie
         */
        @Override
        public String toString() {
                return inhoud;
        }

        /**
         * Converteert een string representatie van een operator naar zijn corresponderende enum constante.
         * 
         * @param opStr de string representatie van de gewenste rekenkundige operator.
         * @return de enum constante die correspondeert met de gegeven string, met {@code null} bij ongeldige strings
         */
        public static Operation fromString(String opStr) {
                return stringToOp.get(opStr);
        }
}