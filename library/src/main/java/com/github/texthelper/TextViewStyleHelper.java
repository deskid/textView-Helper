package com.github.texthelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
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

import com.github.logutils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

/**
 TextView tv = (TextView) findViewById(R.id.textView);

 String str = "阅读并同意《绑卡协议》、《用户协议》。短信已由[xx银行]发出";

 TextViewHelper.with(getContext())
 .create(str)

 .first("《绑卡协议》")
 .textColor(ResUtils.getColor(R.color.colorAccent))
 .url("https://f.mogujie.com/fund/help/535")

 .first("《用户协议》")
 .textColor(ResUtils.getColor(R.color.colorPrimaryDark))
 .onClick(new OnClickListener(){...})

 .between("[", "]")
 .textColor(ResUtils.getColor(R.color.mainText))
 .into(tv);
 */

public class TextViewStyleHelper {
    private static final int SPAN_MODE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private String mText;
    private ArrayList<Range> mRanges = new ArrayList<>();
    private Context mContext;
    private SpannableStringBuilder mSpanBuilder;

    private TextViewStyleHelper(final Context context) {
        mContext = context;
    }

    private TextViewStyleHelper(final Context context, final String text) {
        mContext = context;
        mText = text;
    }

    @Deprecated
    public static TextViewStyleHelper with(Context context) {
        return new TextViewStyleHelper(context);
    }

    public static TextViewStyleHelper with(Context context, String text) {
        return new TextViewStyleHelper(context).create(text);
    }

    public TextViewStyleHelper create(String text) {
        mText = text;
        mSpanBuilder = new SpannableStringBuilder(text);
        return this;
    }

    public TextViewStyleHelper first(String target) {
        mRanges.clear();
        int index = mText.indexOf(target);
        Range range = Range.create(index, index + target.length());
        if (checkRange("first", range)) {
            mRanges.add(range);
        }
        return this;
    }

    public TextViewStyleHelper last(String target) {
        mRanges.clear();
        int index = mText.lastIndexOf(target);
        Range range = Range.create(index, index + target.length());
        if (checkRange("last", range)) {
            mRanges.add(range);
        }
        return this;
    }

    public TextViewStyleHelper every(String target) {
        mRanges.clear();
        for (int index = mText.indexOf(target); index >= 0; index = mText.indexOf(target, index + 1)) {
            Range range = Range.create(index, index + target.length());
            if (checkRange("every", range)) {
                mRanges.add(range);
            }
        }
        return this;
    }

    public TextViewStyleHelper append(String target) {
        int oLength = mText.length();
        mText = mText.concat(target);
        mSpanBuilder.append(target);
        mRanges.clear();
        Range range = Range.create(oLength, oLength + target.length());
        if (checkRange("append", range)) {
            mRanges.add(range);
        }
        return this;
    }

    public TextViewStyleHelper all() {
        mRanges.clear();
        Range range = Range.create(0, mText.length());
        if (checkRange("all", range)) {
            mRanges.add(range);
        }
        return this;
    }

    //[from, to)
    public TextViewStyleHelper range(int from, int to) {
        mRanges.clear();
        Range range = Range.create(from, to);
        if (checkRange("range", range)) {
            mRanges.add(range);
        }
        return this;
    }

    public TextViewStyleHelper ranges(List<Range> ranges) {
        mRanges.clear();
        for (Range range : ranges) {
            if (checkRange("ranges", range)) {
                mRanges.add(range);
            }
        }
        return this;
    }

    public TextViewStyleHelper between(String startText, String endText) {
        mRanges.clear();
        int startIndex = mText.indexOf(startText) + startText.length();
        int endIndex = mText.lastIndexOf(endText);
        Range range = Range.create(startIndex, endIndex);
        if (checkRange("between", range)) {
            mRanges.add(range);
        }
        return this;
    }

    public TextViewStyleHelper size(int dp) {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new AbsoluteSizeSpan(dp, true), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper scaleSize(int proportion) {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new RelativeSizeSpan(proportion), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper bold() {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new StyleSpan(Typeface.BOLD), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper italic() {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new StyleSpan(Typeface.ITALIC), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper font(String font) {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new TypefaceSpan(font), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper strikethrough() {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new StrikethroughSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper underline() {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new UnderlineSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper background(@ColorInt int colorInt) {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new BackgroundColorSpan(colorInt), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper textColor(@ColorInt int colorInt) {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new ForegroundColorSpan(colorInt), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper subscript() {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new SubscriptSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper superscript() {
        for (Range range : mRanges) {
            mSpanBuilder.setSpan(new SuperscriptSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper url(final String link) {
        for (final Range range : mRanges) {
            mSpanBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(link)) {
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(false);
                }

            }, range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public TextViewStyleHelper onClick(final View.OnClickListener onClickListener) {
        for (final Range range : mRanges) {
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

    private boolean checkRange(final String operation, Range range) {
        int len = mText.length();

        if (range.to > len) {

            CheckUtils.throwExceptionIfDebug(new IndexOutOfBoundsException(operation + " " +
                    "(" + range.from + " ... " + range.to + ")" + " ends beyond length " + len));
            return false;
        }
        return true;
    }

    private static class Range {

        public final int from;
        public final int to;

        //[from, to)
        private Range(int from, int to) {
            if (from < 0 || to < 0) {
                CheckUtils.throwExceptionIfDebug(new IndexOutOfBoundsException("(" + from + " ... " + to + ")" + " starts before 0"));
                this.to = 0;
                this.from = 0;
            } else if (to < from) {
                CheckUtils.throwExceptionIfDebug(new IndexOutOfBoundsException("(" + from + " ... " + to + ")" + " has end before start"));
                this.to = 0;
                this.from = 0;
            } else {
                this.from = from;
                this.to = to;
            }

        }

        public static Range create(int from, int to) {
            return new Range(from, to);
        }

    }
}
