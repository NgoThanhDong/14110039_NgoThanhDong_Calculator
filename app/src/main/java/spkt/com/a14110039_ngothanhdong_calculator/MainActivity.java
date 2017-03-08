package spkt.com.a14110039_ngothanhdong_calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Khai báo textview thể hiện phép tính và kết quả trên máy tính
    private TextView tvExpression;
    // Khai báo các nút sô, các nút toán tử, nút clear, nút bằng, nút ., nút âm dương trên máy tính
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDiv, btnMul, btnPlus, btnSub,
            btnC, btnBracket, btnEqual, btnDoc, btnRev;
    // Khai báo nút xóa
    private ImageButton ibDel;

    // Khai báo chuỗi đầu vào và chuỗi kết quả; gán giá trị bằng ""
    private String calculation = "", calculationResults = "";
    // Khai báo biến đêm số lượng ngoặc (
    private int brackets = 0;
    // Khai bào cờ đếm nút ., cờ nút âm dương, cờ phát hiện lỗi(chẳng hạn như kết quả của trường hợp chia 0, sai định dạng chuỗi đầu vào)
    // và gán giá trị tương ứng
    private boolean fDot = true, fNegative = true, fError = false;

    // Phương thức đầu tiên được gọi dùng để tạo một activity vào lần đầu tiên activity được gọi.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        setEventClickViews();
    }

    // Khởi tạo Widget
    private void initWidget() {
        tvExpression = (TextView) findViewById(R.id.tvExpression);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnSub = (Button) findViewById(R.id.btnSub);

        ibDel = (ImageButton) findViewById(R.id.ibDel);
        btnC = (Button) findViewById(R.id.btnC);
        btnBracket = (Button) findViewById(R.id.btnBracket);
        btnEqual = (Button) findViewById(R.id.btnEqual);
        btnDoc = (Button) findViewById(R.id.btnDoc);
        btnRev = (Button) findViewById(R.id.btnRev);
    }

    // Phương thức sét xự kiện onClick cho các nút khai báo ở trên tương ứng cho các view
    private void setEventClickViews() {
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btnDiv.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnPlus.setOnClickListener(this);

        ibDel.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnBracket.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnDoc.setOnClickListener(this);
        btnRev.setOnClickListener(this);
    }

    // Phương thức xự kiện onclick tương ứng cho từng wiew
    @Override
    public void onClick(View v) {
        calculation = tvExpression.getText().toString(); // gán calculation bằng chuỗi text đa thức đầu vào hiển thị trên tvExpression
        // s: ký tự cuối cùng của calculation
        char s = ' ';
        if (calculation.length() > 0)
            s = calculation.charAt(calculation.length() - 1);

        switch (v.getId()) {
            // Nếu là các nút sô từ 0 -> 9
            case R.id.btn0:
                clickButtonNumber("0", s); // phương thức click nút số (cụ thể ở đây là số 0)
                break;
            case R.id.btn1:
                clickButtonNumber("1", s);
                break;
            case R.id.btn2:
                clickButtonNumber("2", s);
                break;
            case R.id.btn3:
                clickButtonNumber("3", s);
                break;
            case R.id.btn4:
                clickButtonNumber("4", s);
                break;
            case R.id.btn5:
                clickButtonNumber("5", s);
                break;
            case R.id.btn6:
                clickButtonNumber("6", s);
                break;
            case R.id.btn7:
                clickButtonNumber("7", s);
                break;
            case R.id.btn8:
                clickButtonNumber("8", s);
                break;
            case R.id.btn9:
                clickButtonNumber("9", s);
                break;
            // Nếu là các toán tử + - × ÷
            case R.id.btnPlus:
                // Nếu chuỗi đa thức đầu vào không là rỗng thì gọi phương thức click nút toán tử
                if (calculation.length() > 0) clickButtonOperator("+", s);
                break;
            case R.id.btnSub:
                // Nếu ký tự cuối cùng mà là ( thì append vào tvExpression, đồng thời fDot = true
                if(s == '('){
                    Spannable sub = assignColor("-");
                    tvExpression.append(sub);
                    fDot = true;
                    return;
                }
                clickButtonOperator("-", s);
                break;
            case R.id.btnMul:
                if (calculation.length() > 0) clickButtonOperator("×", s);
                break;
            case R.id.btnDiv:
                if (calculation.length() > 0) clickButtonOperator("÷", s);
                break;
            // Nếu là nút Clear
            case R.id.btnC:
                calculationResults = "";
                resetCalculator();
                break;
            // Nếu là nút xóa
            case R.id.ibDel:
                 // Nếu calculationResults (biến chuỗi nhớ kết quả) không là rỗng thì return
                if (calculationResults.length() > 0) return;

                // Nếu xóa là các ký tự đặc biệt thì gán chúng lại cho phù hợp, sau đó mới xóa
                try {
                    if (s == '.') fDot = true; // được phép nấp nút .
                    if (s == '(') brackets--; // giảm số lượng ngoặc ( lên 1
                    if (s == ')') brackets++; // tăng số lượng ngoặc ( lên 1
                    updateExpression(calculation.substring(0, tvExpression.length() - 1)); // xóa ký tự cuối cùng của đa thức đầu vào
                } catch (Exception e) {
                    tvExpression.setText(""); // clear tvExpression
                }
                break;
            // Nếu là nút dấu .
            case R.id.btnDoc:
                // Nếu calculationResults không là rỗng thì return
                if (calculationResults.length() > 0) return;
                // Nết fDot (cờ được phép nhấn nút .) là true thì
                if (fDot) {
                    // Nếu ký tụ cuối cùng của chuỗi đa thức đầu vào là sô hoặc ) hoặc ( hoặc chuỗi đầu vào là rống thì thêm "0." vào chuỗi
                    // sau đó cập nhật fDot = false
                    if (isOperator(s) || s == ')' || s == '(' || calculation.length() == 0)
                        tvExpression.append("0.");
                    else tvExpression.append(".");
                    fDot = false;
                }
                break;
            // Nếu là nút ()
            case R.id.btnBracket:
                if (fError) return; // Nếu cò fError bằng true thì return
                // Nếu cò fError bằng false và chuỗi kết quả không rỗng
                if (!fError && calculationResults.length() > 0) {
                    resetCalculator(); // reset lại máy tính
                    // chuỗi đa thức phép tính chính là kết quả + "×(""
                    tvExpression.setText(calculationResults);
                    calculationResults = "";
                    Spannable mul = assignColor("×");
                    tvExpression.append(mul);
                    tvExpression.append("(");
                    brackets++; // tăng số lượng ngoặc ( lên 1
                    fDot = true; // có thể nhấn được nút .
                    return;
                }


                boolean fOparen = false;
                // Trường hợp nếu chuỗi đa thức đầu vào có dạng (XX.XX vói X là số thì fOparen = true (thêm vào "×(")
                if (calculation.length() > 0 && isNumber(calculation.charAt(calculation.length() - 1))) {
                    for (int i = calculation.length() - 1; i >= 0; i--) {
                        if (isNumber(calculation.charAt(i)) || calculation.charAt(i) == '.') { // Nếu là ký tự số hoặc . thì bỏ qua
                        } else {
                            if (calculation.charAt(i) == '(') fOparen = true; // Nếu là ngoặc thì fOparen = true và dừng vòng lặp
                            break;
                        }
                    }
                }

                // Trường hợp thêm vào đấu (
                if (calculation.length() == 0 || s == '(' || isOperator(s)) {
                    tvExpression.append("(");
                    brackets++;
                }
                // Trường hợp thêm vào ×(
                else if (((s == ')' || isNumber(s)) && brackets == 0) || fOparen) {
                    Spannable mul = assignColor("×");
                    tvExpression.append(mul);
                    tvExpression.append("(");
                    brackets++;
                }
                // Trường hợp thêm vào )
                else {
                    // Nếu ký tự cuối cùng của chuỗi đầu vào là . thì xóa . trước khi thêm vào )
                    if (s == '.') {
                        updateExpression(calculation.substring(0, tvExpression.length() - 1));
                        tvExpression.append(")");
                        brackets--;
                    }
                    // Trường hợp thêm ) bình thường
                     else if ((isNumber(s) || s == ')') && brackets > 0) {
                        tvExpression.append(")");
                        brackets--;
                    }
                }
                fDot = true; // Cập nhật lại fDot
                break;
            // Nếu là nút âm dương
            case R.id.btnRev:
                // Nếu chuỗi đầu vào là rỗng và ký tự cuối cùng không phải là sô thì return
                if (calculation.length() == 0 || !isNumber(s)) return;
                if (fError) return; // Nếu cò fError bằng true thì return
                // Nếu cò fError bằng false và chuỗi kết quả không rỗng
                if (!fError && calculationResults.length() > 0) {
                    resetCalculator(); // reset lại máy tính
                    String str;
                    // Nếu chuỗi kết quả không âm thì đổi thành âm
                    if (calculationResults.indexOf('-') == -1)
                        str = "-" + calculationResults;
                    else // Nếu kết quả là âm thì đổi thành dương
                        str = calculationResults.substring(1, calculationResults.length());
                    updateExpression(str);
                    calculationResults = "";
                    return;
                }

                int vt = -1; // biến vị trí
                String temp1 = "";
                boolean f = false; // cờ kiểm tra xem ký tự đầu có phải là '-' không

                // Duyệt ngược chuỗi đa phép tính lấy ra toán hạng cuối cùng, xét vị trí của toán hạng cuối cùng trong chuỗi phép tính
                for (int i = calculation.length() - 1; i >= 0; i--) {
                    // Nếu là ký tự cuối là số hoặc chấm thì cho vào biến temp1
                    if (isNumber(calculation.charAt(i)) || calculation.charAt(i) == '.')
                        temp1 += calculation.charAt(i);
                    else {
                        // Nếu ký tự đầu tiên của chuỗi phép tính thì bật cờ f
                        if (calculation.charAt(i) == '-' && i == 0) f = true;
                        // Nếu trước toán hạng cuối là (- tức là số âm
                        else if (calculation.charAt(i) == '-' && calculation.charAt(i - 1) == '(') {
                            fNegative = false;
                            vt = i - 1;
                        }
                        // Trường hợp trước toán hạng cuối là một toán tử
                        else {
                            fNegative = true;
                            vt = i;
                        }
                        break;
                    }
                }
                // Trường hợp có dấu - ở đầu phép tính thì chuỗi phép tính bằng chuỗi phép tính sau khi cắt đi ký tự đầu tiên
                if (f) {
                    String str = calculation.substring(1, calculation.length());
                    tvExpression.setText("");
                    tvExpression.append(str);
                    return;
                }

                // Vì duyệt ngược chuỗi ở trên nên cần reverse lại để tạo ra toán hạng cuối của chuỗi phép tính
                StringBuilder temp2 = new StringBuilder(temp1);
                String operand = temp2.reverse().toString(); // operand là toán hạng cuối của phép tính

                // Nếu cờ âm bật tức là hiện tại là toán hạng cuối là số dương
                if (fNegative) {
                    Spannable sub = assignColor("-"); // xét màu cho dâu -
                    // Nếu phép tính chỉ có 1 toán hạng thì thêm (- rồi đến toán hạng
                    if (vt == -1) {
                        tvExpression.setText("(");
                        tvExpression.append(sub);
                        tvExpression.append(calculation);
                    }
                    // Nếu phép tính chỉ có nhiều toán hạng thì cắt previousString: phép tính sau khi bỏ đi toán hạng cuối của phép tính
                    // sau đó thêm (- rồi đến toán hạng
                    else {
                        String previousString = calculation.substring(0, vt + 1);
                        updateExpression(previousString);
                        tvExpression.append("(");
                        tvExpression.append(sub);
                        tvExpression.append(operand);
                    }
                    // Tắt cò âm và tăng số lượng ngoặc ( lên 1
                    fNegative = false;
                    brackets += 1;
                }
                // Trường hợp toán hạng cuối là số âm ví dụ như (-333
                // previousString: phép tính sau khi bỏ đi dấu (- và toán hạng cuối của phép tính, sau đó thêm toán hạng
                else {
                    String previousString = calculation.substring(0, vt);
                    updateExpression(previousString);
                    tvExpression.append(operand);
                    // Bật cờ âm và giảm số lượng ( xuống 1
                    fNegative = true;
                    brackets -= 1;
                }
                break;
            // Nếu là nút =
            case R.id.btnEqual:
                // Nếu chuỗi phép tính trống, ký tự cuối của chuỗi phép tính là toán tử, ( và đã có kết quả (tức là đã bấm dấu =) thì return
                if (calculation.length() == 0 || isOperator(s) || s == '(' || calculationResults.length() > 0) return;
                // Nếu ký tự cuối là . thì xóa đi
                if (s == '.')
                    updateExpression(calculation.substring(0, tvExpression.length() - 1));
                // Nếu chuỗi phép tính thiếu ) thì thêm vào
                while (brackets > 0) {
                    tvExpression.append(")");
                    brackets--;
                }
                // result: kết quả của chuỗi phép tính
                Spannable result = new SpannableString(resultsFormat());
                // xét màu cho chuỗi kết quả
                result.setSpan(new ForegroundColorSpan(Color.parseColor("#46A749")), 0, result.length()
                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                // append kết quả vào chuỗi phép tính
                tvExpression.append(result);
                break;
            default:
                break;
        }
    }

    // Phương thức kiểm tra ký tự có phải là toán tử
    private boolean isOperator(char s) {
        if (s == '+' || s == '-' || s == '×' || s == '÷')
            return true;
        return false;
    }

    // Phương thức kiểm tra ký tự có phải là số
    private boolean isNumber(char s) {
        if (s == '0' || s == '1' || s == '2' || s == '3' || s == '4' || s == '5' || s == '6' || s == '7' || s == '8' || s == '9')
            return true;
        return false;
    }

    // Phương thức xét màu cho ký tự
    private Spannable assignColor(String str) {
        Spannable color = new SpannableString(str);
        color.setSpan(new ForegroundColorSpan(Color.parseColor("#10A5C2")), 0, 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return color;
    }

    // Phương thức click nút số
    private void clickButtonNumber(String number, char str) {
        // Nếu đã có kết quả thì reset lại máy tính và append số vào textview hiện thị phép tính
        if (calculationResults.length() > 0) {
            resetCalculator();
            calculationResults = "";
            tvExpression.append(number);
            return;
        }

        // Nếu ký tự cuối của phép tính là ) thì thêm × trước khi thêm số vào textview hiện thị phép tính
        if (str == ')') {
            Spannable mul = assignColor("×");
            tvExpression.append(mul);
            tvExpression.append(number);
        }
        // Trường hợp ký tự cuối của phép tính là số 0 và cờ dấu . là true
        else if (str == '0' && fDot) {
            char s1 = ' '; // s1 là ký tự áp cuối của chuỗi phép tính
            if (calculation.length() > 1)
                s1 = calculation.charAt(calculation.length() - 2);
            // Nếu ký tự áp chót không phải là số thì xóa ký tự cuối (tức số 0) sau đó thêm số vào chuỗi phép tính
            if(!isNumber(s1)) {
                updateExpression(calculation.substring(0, tvExpression.length() - 1));
                tvExpression.append(number);
            }
            else tvExpression.append(number);
        } else tvExpression.append(number);
    }

    // Phương thức click nút toán tử
    private void clickButtonOperator(String operator, char s) {
        // Nếu kết quả không phải là số và ký tự cuối là ( thì return : ngoại trừ dấu - (có bắt ở trên) thì return
        if (fError || s == '(') return;
        // Nếu chuỗi phép tính chỉ có 1 ký tự duy nhất và là dấu - thì return
        if (calculation.length() == 1 && calculation.charAt(0) == '-') return;
        // Nếu kết quả là số thì reset lại máy tính
        if (!fError && calculationResults.length() > 0) {
            resetCalculator();
            tvExpression.setText(calculationResults);
            calculationResults = "";
        }
        // s1 là ký tự áp chót của chuỗi phép tính
        char s1 = ' ';
        if (calculation.length() > 1)
            s1 = calculation.charAt(calculation.length() - 2);
        // Nếu ký tự áp chót là ( và ký tự cuối là - của chuỗi phép tính thì return
        if(s1 == '(' && s == '-') return;
        // Trường hợp ký tự cuối của phép tính là toán hạng hoặc là . thì xóa ký tự cuối phép tính
        if (isOperator(s) || s == '.')
            updateExpression(calculation.substring(0, tvExpression.length() - 1));

        Spannable ope = assignColor(operator);  // Xét màu cho toán hạng
        tvExpression.append(ope); // Thêm toán hạng vào tvExpression
        fDot = true; // bật cờ .
    }

    // Phương thức ghi lại chuỗi, sét màu cho chuỗi hiện thị phép tính
    private void updateExpression(String str) {
        tvExpression.setText(""); // reset lại chuỗi đa thức ban đầu
        // duyệt chuỗi, nếu là toán tử thì xét màu
        // sau đó append vào tvExpression: textview thể hiện chuỗi đa thức phép tính ban đầu
        for (int i = 0; i < str.length(); i++) {
            if (isOperator(str.charAt(i))) {
                Spannable operator = assignColor(String.valueOf(str.charAt(i)));
                tvExpression.append(operator);
            } else tvExpression.append(String.valueOf(str.charAt(i)));
        }
    }

    // Phương thức chuẩn hóa lại chuỗi hiện thị sau khi nhấn nút =
    private String resultsFormat() {
        String str = tvExpression.getText().toString(); // str là chuỗi phép tính
        // Nếu ký tự đầu tiên của phép tính không phải là dấu - thì str = "0+" + str;
        if(str.charAt(0) != '-')
            str = "0+" + str;

        // kq: là biến calculator
        Calculator kq = new Calculator(str);
        // result: là biến kết quả
        String result = kq.getResult().trim();

        // Nếu kết quả không phải là một con số
        // calculationResults: chuỗi kết quả (nhớ)
        if (result == "Sai định dạng!" || result == "Không xác định!" || result == "Không thể chia 0!") {
            fError = true;
            calculationResults = result;
            return ("\n" + result);
        }

        result = String.format("%1$,.10f", Double.parseDouble(result)); // format kết quả (lúc này kết quả là một con số)
        String integerPart = ""; // phần nguyên
        String decimalFraction = ""; // phân thập phân
        int vt = -1; // biến vị trí (đánh đấu phần nguyên và thập phân)
        String temp1 = "";

        // duyệt ngược chuỗi kết quả lấy phần thập phân của kết quả
        for (int i = result.length() - 1; i >= 0; i--) {
            if (isNumber(result.charAt(i)))
                temp1 += String.valueOf(result.charAt(i));
            else if (result.charAt(i) == '.') {
                vt = i;
                break;
            }
        }

        // Vì duyệt ngược chuỗi kết quả nên cần phải reverse lại
        StringBuilder temp2 = new StringBuilder(temp1);
        decimalFraction = temp2.reverse().toString(); // lấy phần thập phân
        integerPart = result.substring(0, vt); // lấy phần nguyên

        // Xóa các số 0 thừa của phần thập phân
        while (decimalFraction.length() > 0 && decimalFraction.lastIndexOf("0") == decimalFraction.length() - 1) {
            decimalFraction = decimalFraction.substring(0, decimalFraction.length() - 1);
        }

        // Nếu không có phần thập phân (tức = 0) thì return phân nguyên
        if (decimalFraction.length() == 0) {
            calculationResults = integerPart;
            return ("\n=" + integerPart);
        }

        // Lưu kết quả và return kết quả (cả phần nguyên và phần thập phân)
        calculationResults = integerPart + "." + decimalFraction;
        return ("\n=" + integerPart + "." + decimalFraction);
    }

    // Phương thức reset lại máy tính
    private void resetCalculator() {
        tvExpression.setText("");
        calculation = "";
        brackets = 0;
        fDot = true;
        fNegative = true;
        fError = false;
    }
}