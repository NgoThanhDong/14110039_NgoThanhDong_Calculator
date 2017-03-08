package spkt.com.a14110039_ngothanhdong_calculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/*  Phương thức matches() trong Java xác định có hay không chuỗi này so khớp (match) với regular expression đã cho.
    Một dẫn chứng của phương thức này là form dạng str.matches(regex) biến đổi chính xác kết quả giống như trong Pattern.matches(regex, str).
    Trả về true nếu và chỉ nếu chuỗi này so khớp với regular expression đã cung cấp.*/

public class Calculator {
    private String expression;  // Chuỗi phép tính đầu vào

    private Map<String, Integer> priority;  // Độ ưu tiên

    public Calculator(String expression) {
        this.expression = expression;
        initPriority();
    }

    // Quy ước độ ưu tiên
    private void initPriority() {
        this.priority = new HashMap<>();
        this.priority.put("#", 0); // quy ước dấu sai định dạng
        this.priority.put("+", 1);
        this.priority.put("-", 1);
        this.priority.put("×", 2);
        this.priority.put("÷", 2);
    }

    // Lấy độ ưu tiên
    public int getPriority(String operator) {
        if (operator.matches("[()]")) // độ ưu tiên của dấu ( và ) là -1
            return -1;
        return priority.get(operator);
    }

    // Kiểm tra độ ưu tiên của hai toán tử
    private boolean isPrior(String one, String another) {
        return getPriority(one) <= getPriority(another);
    }

    // Lấy phần tử trên cùng của stack
    private <T> T getTopEle(Stack<T> stack) {
        if (stack == null) return null;
        return stack.get(stack.size() - 1);
    }

    // Kiểm tra đúng cú pháp
    private boolean isSyntaxChecked() {
        int left_bracket = 0;               // dấu ngoặc (
        int right_bracket = 0;              // dấu ngoặc )
        String current = "";                // kí tự hiện tại
        String current_left = "";           // bên trái chuỗi hiện tại
        String current_right = "";          // bên phải chuỗi hiện tại
        int length0 = expression.length();  // chiều dài của chuỗi phép tính đầu vào (đa thức)

        for (int i = 0; i < length0; i++) {
            // curent: lấy ký tự hiện tại của chuỗi đa thức
            current = String.valueOf(expression.charAt(i));
            // Nếu không là kí tự đầu thì cập nhật giá trị bên trái
            if (i != 0)
                current_left = String.valueOf(expression.charAt(i - 1));
            // Nếu current là + - và (current_left là (  thì thêm 0 theo trường họp i có bằng hoặc không bằng 0
            if (current.matches("[\\+\\-]") && (current_left.matches("\\(") || i == 0)) {
                if (i == 0)
                    expression = "0" + expression;
                else
                    expression = expression.substring(0, i) + "0" + expression.substring(i);
            }
        }

        int length1 = expression.length();
        for (int i = 0; i < length1; i++) {
            current = String.valueOf(expression.charAt(i));
            if (i != 0) current_left = String.valueOf(expression.charAt(i - 1));
            if (i != length1 - 1) current_right = String.valueOf(expression.charAt(i + 1));
            // Nếu current là ngoặc trái
            if (current.matches("[\\(]")) {
                // Nếu bên trái current là số hay bên phải là dấu thì bỏ qua
                if (current_left.matches("[\\d\\.\\)]") || current_right.matches("[\\+\\-\\×÷]") || i == length1 - 1)
                    return false;
                left_bracket++; // Nếu tính được thì tăng biến đếm ngoặc (
            }
            // Nếu là ngoặc phải
            else if (current.matches("[\\)]")) {
                // Nếu bên phải current là số hay bên trái là dấu thì bỏ qua
                if (current_left.matches("[\\+\\-\\×/]") || current_right.matches("[\\d\\.\\(]") || i == 0)
                    return false;
                right_bracket++;
            }
            // Cuối đa thức là . thì không tính
            else if (current.matches("[\\.]") && i == length1 - 1) {
                return false;
            }
            // Nếu current là phép toán thì kt bên trái, phải có là dấu luôn thì bỏ qua
            else if (current.matches("[\\+\\-\\×÷]")) {
                if (current_left.matches("[\\+\\-\\×÷]") || current_right.matches("[\\+\\-\\×÷]") || i == length1 - 1 || length1 == 1)
                    return false;
            }
        }
        return left_bracket == right_bracket; // số lượng ngoặc bằng nhau
    }

    // Chuyển infix thành một biểu thức postfix
    public Queue<String> toSuffix() {
        Queue<String> operandQueue = new LinkedList<>();    // Hàng đợi số hạng
        if (!isSyntaxChecked()) {   // Nếu phép tính đầu vào sai cú pháp
            operandQueue.add("#");  // thì cho dấu # vào queue số hạng
            return operandQueue;
        }

        Stack<String> operatorStack = new Stack<>();// stack toán hạng
        operatorStack.push("#");

        String current = "";    // Vị trí hiện tại
        String operator = "";   // toán hạng
        String number = "";     // số
        int start = 0;
        int end = 0;
        for (int i = 0; i < expression.length(); i++) {
            current = String.valueOf(expression.charAt(i));
            // Nếu nó là số
            if (current.matches("[\\d\\.]")) {
                // Nếu là dãy số sẽ đưa vào hàng đợi
                if (i == expression.length() - 1) { // Nếu là kí tự cuối
                    current = String.valueOf(expression.substring(start, end + 1));// tăng lên 1 kí tự
                    operandQueue.add(current);
                } else end++;
            } else {
                // Nếu nó là ( thì đưa vào stack
                if (current.equals("(")) {
                    operatorStack.push(current);
                } else {
                    number = expression.substring(start, end);
                    if (!number.isEmpty())
                        operandQueue.add("0" + number); // Thêm số 0 vào queue
                    // Nếu đóng ) thì kiểm tra bên trong có số hạng để tính không
                    if (current.equals(")")) {
                        while (!getTopEle(operatorStack).equals("("))
                            operandQueue.add(operatorStack.pop());
                        operatorStack.pop();
                    } else {
                        // Nếu không phải đóng ) thì kiểm tra độ ưu tiên đưa vào queue số hạng để tính
                        operator = current;
                        while (isPrior(operator, getTopEle(operatorStack)))
                            operandQueue.add(operatorStack.pop());
                        operatorStack.push(operator);
                    }
                }
                start = end = i + 1; // Cập nhật lại vị trí đầu, cuối
            }
        }

        // Duyệt ngược stack và đưa vào queue
        for (int i = operatorStack.size() - 1; i > 0; i--)
            operandQueue.add(operatorStack.pop());
        return operandQueue;
    }

    // Phương thức tính toán
    public String getResult() {
        Queue<String> suffixQueue = toSuffix();
        if (suffixQueue.peek().equals("#")) return "Sai định dạng!";
        Stack<String> suffixStack = new Stack<String>();
        String current = "";
        BigDecimal frontOperand; //số hạng trước
        BigDecimal backOperand; // sô hạng sau

        double value = 0;
        for (int i = suffixQueue.size(); i > 0; i--) {
            current = suffixQueue.poll();
            // Nếu là số
            if (current.matches("^\\d+(\\.\\d+)*$")) {
                suffixStack.push(current);
            } else {
                // Toán hạng
                backOperand = BigDecimal.valueOf(Double.valueOf(suffixStack.pop())); // toán hạng 1
                frontOperand = BigDecimal.valueOf(Double.valueOf(suffixStack.pop())); // toán hạng 2
                // Tính toán
                if (current.equals("+")) {
                    value = frontOperand.add(backOperand).doubleValue();
                } else if (current.equals("-")) {
                    value = frontOperand.subtract(backOperand).doubleValue();
                } else if (current.equals("×")) {
                    value = frontOperand.multiply(backOperand).doubleValue();
                } else if (current.equals("÷")) {
                    if (backOperand.doubleValue() == 0 && frontOperand.doubleValue() == 0) return "Không xác định!";
                    if (backOperand.doubleValue() == 0) return "Không thể chia 0!";
                    value = frontOperand.divide(backOperand, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                suffixStack.push(String.valueOf(value));
            }
        }
        return suffixStack.get(0);
    }
}