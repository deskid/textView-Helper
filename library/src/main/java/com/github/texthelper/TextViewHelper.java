package com.github.texthelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 16/12/07.
 */
public class TextViewHelper {
    private static final int SPAN_MODE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private String text;
    private ArrayList<Range> rangeList = new ArrayList<>();
    private Context context;
    private SpannableStringBuilder mSpanBuilder;

    private TextViewHelper(final Context context) {
        this.context = context;
    }

    public static TextViewHelper with(Context context) {
        return new TextViewHelper(context);
    }

    public TextViewHelper create(String text) {
        this.text = text;
        mSpanBuilder = new SpannableStringBuilder(text);
        return this;
    }

    public TextViewHelper first(String target) {
        rangeList.clear();
        int index = text.indexOf(target);
        Range range = Range.create(index, index + target.length());
        checkRange("first", range);
        rangeList.add(range);
        return this;
    }

    public TextViewHelper last(String target) {
        rangeList.clear();
        int index = text.lastIndexOf(target);
        Range range = Range.create(index, index + target.length());
        checkRange("last", range);
        rangeList.add(range);
        return this;
    }

    public TextViewHelper every(String target) {
        rangeList.clear();
        for (int index = text.indexOf(target); index >= 0; index = text.indexOf(target, index + 1)) {
            Range range = Range.create(index, index + target.length());
            checkRange("every", range);
            rangeList.add(range);
        }
        return this;
    }

    public TextViewHelper append(String target) {
        int oLength = text.length();
        text = text.concat(target);
        mSpanBuilder.append(target);
        rangeList.clear();
        Range range = Range.create(oLength, oLength + target.length());
        checkRange("append", range);
        rangeList.add(range);
        return this;
    }

//    /**
//     * text eg: "这是第一部分%1$s,第二部分%2$s,第三部分%3$s"
//     */
//    public TextViewHelper formatWith(String... targets) {
//        text = String.format(text, targets);
//        mSpanBuilder = new SpannableStringBuilder(text);
//        rangeList.clear();
//        for (String target : targets) {
//            for (int index = text.indexOf(target); index >= 0; index = text.indexOf(target, index + 1)) {
//                Range range = Range.create(index, index + target.length());
//                rangeList.add(range);
//            }
//        }
//
//        return this;
//    }

    public TextViewHelper all() {
        rangeList.clear();
        Range range = Range.create(0, text.length());
        checkRange("all", range);
        rangeList.add(range);
        return this;
    }

    public TextViewHelper range(int from, int to) {
        rangeList.clear();
        Range range = Range.create(from, to + 1);
        checkRange("range", range);
        rangeList.add(range);
        return this;
    }

    public TextViewHelper ranges(List<Range> ranges) {
        rangeList.clear();
        for (Range range : ranges) {
            checkRange("ranges", range);
        }
        rangeList.addAll(ranges);
        return this;
    }

    public TextViewHelper between(String startText, String endText) {
        rangeList.clear();
        int startIndex = text.indexOf(startText) + startText.length() + 1;
        int endIndex = text.lastIndexOf(endText) - 1;
        Range range = Range.create(startIndex, endIndex);
        checkRange("between", range);
        rangeList.add(range);
        return this;
    }

    public TextViewHelper size(int dp) {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new AbsoluteSizeSpan(dp, true), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper scaleSize(int proportion) {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new RelativeSizeSpan(proportion), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper bold() {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new StyleSpan(Typeface.BOLD), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper italic() {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new StyleSpan(Typeface.ITALIC), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper font(String font) {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new TypefaceSpan(font), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper strikethrough() {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new StrikethroughSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper underline() {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new UnderlineSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper background(@ColorRes int colorRes) {
        int color = ContextCompat.getColor(context, colorRes);
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new BackgroundColorSpan(color), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper textColor(@ColorRes int colorRes) {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorRes)), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper subscript() {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new SubscriptSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper superscript() {
        for (Range range : rangeList) {
            mSpanBuilder.setSpan(new SuperscriptSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper url(final String link) {
        for (final Range range : rangeList) {
            mSpanBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(false);
                }

            }, range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewHelper onClick(final View.OnClickListener onClickListener) {
        for (final Range range : rangeList) {
            mSpanBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(view);
                }

                @Override
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(false);
                }

            }, range.from, range.to, SPAN_MODE);
        }

        return this;
    }

    public void into(TextView textView) {
        if (textView == null) {
            return;
        }
        textView.setHighlightColor(Color.TRANSPARENT);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(mSpanBuilder);
    }

    private static String region(int start, int end) {
        return "(" + start + " ... " + end + ")";
    }

    private void checkRange(final String operation, Range range) {
        int end = range.to;
        int start = range.from;
        if (end < start) {
            throw new IndexOutOfBoundsException(operation + " " +
                    region(start, end) + " has end before start");
        }

        int len = text.length();

        if (start > len || end > len) {
            throw new IndexOutOfBoundsException(operation + " " +
                    region(start, end) + " ends beyond length " + len);
        }

        if (start < 0 || end < 0) {
            throw new IndexOutOfBoundsException(operation + " " +
                    region(start, end) + " starts before 0");
        }
    }

}
