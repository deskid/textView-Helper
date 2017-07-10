package com.github.supertext;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.logutils.DebugUtils;
import com.github.texthelper.TextViewStyleHelper;

public class MainActivity extends AppCompatActivity {

    private int getColorCompat(@ColorRes int color) {
        return getResources().getColor(color);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugUtils.setApplicationContext(getApplicationContext());

        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.textView);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        TextView tv3 = (TextView) findViewById(R.id.textView3);

        String str = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Curabitur ipsum felis, sagittis vel fermentum eu, euismod aliquet massa. " +
                "Vestibulum ut dignissim tortor. Vivamus iaculis arcu ipsum. " +
                "Aliquam id mattis justo. Maecenas at nisl quis risus auctor congue. " +
                "Vestibulum ultrices nec nulla in facilisis. Nunc a erat pellentesque, lacinia lectus ut, malesuada purus. " +
                "Fusce a fringilla sem. Proin mattis eros eu pulvinar fringilla. In suscipit ut ligula at euismod.";

        String str2 = "同意： ";

        TextViewStyleHelper.with(this, str)
                .every("am")
                .textColor(getColorCompat(R.color.colorAccent))

                .first("adipiscing")
                .textColor(getColorCompat(R.color.colorPrimaryDark))
                .font("monospace")
                .scale(1.1f)

                .last("eu")
                .textColor(getColorCompat(R.color.colorPrimary))
                .size(24)

                .last("Proin")
                .bold()

                .first("Maecenas")
                .italic()

                .first("facilisis")
                .background(getColorCompat(R.color.colorPrimary))

                .first("Vestibulum")
                .strikethrough()

                .first("fringilla")
                .subscript()

                .first("ligula")
                .superscript()

                .first("euismod")
                .underline()

                .between("In", "ut")
                .textColor(getColorCompat(R.color.mainText))
                .into(tv);

        TextViewStyleHelper.with(this, str2)
                .append("《绑卡协议》")
                .textColor(getColorCompat(R.color.colorPrimary))
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Toast.makeText(MainActivity.this, "《绑卡协议》", Toast.LENGTH_SHORT).show();
                    }
                })
                .append("，")
                .append("《用户协议》")
                .textColor(getColorCompat(R.color.colorAccent))
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Toast.makeText(MainActivity.this, "《用户协议》", Toast.LENGTH_SHORT).show();
                    }
                })
                .scale(2)
                .into(tv2);

        String str3 = "";
        TextViewStyleHelper.with(this, str3)
                .append(" HTTP ")
                .textColor(getColorCompat(android.R.color.white))
                .background(getColorCompat(R.color.mainText))
                .tag(true)
                .append(" ")

                .append(" GET ")

                .textColor(getColorCompat(android.R.color.white))
                .background(getColorCompat(R.color.colorAccent))
                .scale(0.8f)
                .tag(true)

                .append(" ")

                .append(" POST ")
                .textColor(getColorCompat(android.R.color.white))
                .background(getColorCompat(R.color.colorPrimary))
                .scale(0.7f)
                .tag(true)

                .append(" ")
                .append(" BETA ")
                .textColor(getColorCompat(android.R.color.white))
                .background(getColorCompat(R.color.mainText))
                .tag(false)

                .append(" ")

                .append(" ALPHA ")
                .textColor(getColorCompat(android.R.color.white))
                .background(getColorCompat(R.color.colorAccent))
                .scale(0.8f)
                .tag(false)

                .append(" ")

                .append(" CANARY ")
                .textColor(getColorCompat(android.R.color.white))
                .background(getColorCompat(R.color.colorPrimary))
                .scale(0.7f)
                .bold()
                .tag(false)

                .into(tv3);

    }

}
