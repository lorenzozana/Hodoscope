//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import net.objecthunter.exp4j.*;
import net.objecthunter.exp4j.operator.*;

Operator opGT = new Operator(">", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
	        if(args[0]>args[1]) return 1.0;
                return 0.0;
            }
};

Operator opLT = new Operator("<", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                if(args[0]<args[1]) return 1.0;
                return 0.0;
            }
};

Operator opEQ = new Operator("==", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                if(args[0]==args[1]) return 1.0;
                return 0.0;
            }
};

Operator opAND = new Operator("&", 2, true, Operator.PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                if(args[0]>0.0&&args[1]>0.0) return 1.0;
                return 0.0;
            }
};

Expression e = new ExpressionBuilder("x>2.0&(y>2.0)&x==2.3").operator(opAND).operator(opGT).operator(opLT).operator(opEQ)
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
double result = e.evaluate();
System.out.println("result = " + result);

e.setVariable("x",1.0)

System.out.println("second pass  = " + e.evaluate());
