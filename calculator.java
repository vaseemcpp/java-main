import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class FDemo extends JFrame implements ActionListener {
    JTextField tx1;
    JButton b[] = new JButton[20];
    int k = 0;
    int x, y, w, h;
    String[] data = { "B", "C", "1/x", "sqrt", "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".",
            "=", "+" };

    public double evaluateExpression(String expression) {
        char[] tokens = expression.toCharArray();

        // Stacks to store operands and operators
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        // Iterate through each character in the expression
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            // If the character is a digit or a decimal
            // point, parse the number
            if ((tokens[i] >= '0' && tokens[i] <= '9')
                    || tokens[i] == '.') {
                StringBuilder sb = new StringBuilder();
                // Continue collecting digits and the
                // decimal point to form a number
                while (i < tokens.length
                        && (Character.isDigit(tokens[i])
                                || tokens[i] == '.')) {
                    sb.append(tokens[i]);
                    i++;
                }
                // Parse the collected number and push it to
                // the values stack
                values.push(
                        Double.parseDouble(sb.toString()));
                i--; // Decrement i to account for the extra
                     // increment in the loop
            } else if (tokens[i] == '(') {
                // If the character is '(', push it to the
                // operators stack
                operators.push(tokens[i]);
            } else if (tokens[i] == ')') {
                // If the character is ')', pop and apply
                // operators until '(' is encountered
                while (operators.peek() != '(') {
                    values.push(applyOperator(
                            operators.pop(), values.pop(),
                            values.pop()));
                }
                operators.pop(); // Pop the '('
            } else if (tokens[i] == '+' || tokens[i] == '-'
                    || tokens[i] == '*'
                    || tokens[i] == '/') {
                // If the character is an operator, pop and
                // apply operators with higher precedence
                while (!operators.isEmpty()
                        && hasPrecedence(tokens[i],
                                operators.peek())) {
                    values.push(applyOperator(
                            operators.pop(), values.pop(),
                            values.pop()));
                }
                // Push the current operator to the
                // operators stack
                operators.push(tokens[i]);
            }
        }

        // Process any remaining operators in the stack
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(),
                    values.pop(),
                    values.pop()));
        }

        // The result is the only remaining element in the
        // values stack
        return values.pop();
    }

    private boolean hasPrecedence(char operator1, char operator2) {
        if (operator2 == '(' || operator2 == ')')
            return false;
        return (operator1 != '*' && operator1 != '/')
                || (operator2 != '+' && operator2 != '-');
    }

    // Function to apply the operator to two operands
    private double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException(
                            "Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    @SuppressWarnings("deprecation")
    FDemo() {
        x = 0;
        y = 100;
        w = 100;
        h = 100;
        setLayout(null);
        Font f = new Font("", Font.BOLD, 30);

        tx1 = new JTextField();
        tx1.setSize(400, 100);
        tx1.setLocation(0, 0);
        tx1.setFont(f);
        tx1.setHorizontalAlignment(JTextField.RIGHT);
        add(tx1);
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 4; j++) {
                b[k] = new JButton(data[k]);
                b[k].setSize(w, h);
                b[k].setLocation(x, y);
                b[k].setFont(f);
                add(b[k]);
                b[k].addActionListener(this);
                k++;
                x += 100;
            }
            x = 0;
            y = y + 100;
        }
        // b[0].setLabel("B");
        // b[1].setLabel("C");
        // b[2].setLabel("1/x");
        // b[3].setLabel("sqrt");

        // b[4].setLabel("7");
        // b[5].setLabel("8");
        // b[6].setLabel("9");
        // b[7].setLabel("/");

        // b[8].setLabel("4");
        // b[9].setLabel("5");
        // b[10].setLabel("6");
        // b[11].setLabel("*");

        // b[12].setLabel("1");
        // b[13].setLabel("2");
        // b[14].setLabel("3");
        // b[15].setLabel("-");

        // b[16].setLabel("0");
        // b[17].setLabel(".");
        // b[18].setLabel("=");
        // b[19].setLabel("+");
    }

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b[0]) {
            String s1 = tx1.getText();
            tx1.setText(s1.substring(0, s1.length() - 1));
        } else if (e.getSource() == b[1]) {
            tx1.setText("");
        } else if (e.getSource() == b[2]) {
            String s1 = tx1.getText();
            double a = Double.parseDouble(s1);
            a = 1 / a;
            tx1.setText(String.valueOf(a));
        } else if (e.getSource() == b[3]) {
            String s1 = tx1.getText();
            double a = Double.parseDouble(s1);
            tx1.setText(String.valueOf(Math.sqrt(a)));
            // tx1.setText(""+Math.sqrt(a));
        } else if (e.getSource() == b[18]) {
            String s1 = tx1.getText();
            tx1.setText("" + evaluateExpression(s1));
        } else {
            JButton b1 = (JButton) e.getSource();
            String s5 = tx1.getText() + b1.getLabel();
            tx1.setText(s5);
        }
    }
}

class calculator {
    public static void main(String[] args) {
        FDemo f = new FDemo();
        f.setVisible(true);
        f.setSize(410, 635);
        f.setLocation(200, 100);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
    }
}