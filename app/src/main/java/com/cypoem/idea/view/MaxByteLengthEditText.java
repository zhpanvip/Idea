package com.cypoem.idea.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import java.io.UnsupportedEncodingException;

/**
 * Created by zhpan on 2016/8/30.
 *
 */
public class MaxByteLengthEditText extends AppCompatEditText {

    private int maxByteLength = 60;

    private String encoding = "GBK";

    public MaxByteLengthEditText(Context context) {
        super(context);
        init();
    }

    public MaxByteLengthEditText(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    private void init() {
        setFilters(new InputFilter[] {inputFilter1});
    }

    public int getMaxByteLength() {
        return maxByteLength;
    }

    public void setMaxByteLength(int maxByteLength) {
        this.maxByteLength = maxByteLength;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * input输入过滤
     */
    private InputFilter inputFilter1 = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            try {
                int len ;
                boolean more;
                do {
                    SpannableStringBuilder builder =
                            new SpannableStringBuilder(dest).replace(dstart, dend, source.subSequence(start, end));
                    len = builder.toString().getBytes(encoding).length;
                    more = len > maxByteLength;
                    if (more) {
                        end--;
                        source = source.subSequence(start, end);
                    }
                } while (more);
                return source;
            } catch (UnsupportedEncodingException e) {
                return "Exception";
            }
        }
    };
    /*private InputFilter inputFilter2 = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
            Spanned dest, int dstart,
            int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character
                            .isDigit(source.charAt(i)) && !Character
                            .toString(source.charAt(i)).equals("_") && !Character
                            .toString(source.charAt(i)).equals("-")
                            &&!Character
                            .toString(source.charAt(i)).equals(" ")) {
                        return "";
                    }
                }
                return null;
            }
        };*/

}