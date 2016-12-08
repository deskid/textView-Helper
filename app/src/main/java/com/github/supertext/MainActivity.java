package com.github.supertext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.texthelper.TextViewHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.textView);
        TextView tv2 = (TextView) findViewById(R.id.textView2);

        String str = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Curabitur ipsum felis, sagittis vel fermentum eu, euismod aliquet massa. " +
                "Vestibulum ut dignissim tortor. Vivamus iaculis arcu ipsum. " +
                "Aliquam id mattis justo. Maecenas at nisl quis risus auctor congue. " +
                "Vestibulum ultrices nec nulla in facilisis. Nunc a erat pellentesque, lacinia lectus ut, malesuada purus. " +
                "Fusce a fringilla sem. Proin mattis eros eu pulvinar fringilla. In suscipit ut ligula at euismod.";

        String str2 = "同意：《绑卡协议》，《用户协议》";

        TextViewHelper.with(this)
                .create(str)
                .every("am")
                .textColor(R.color.colorAccent)

                .first("adipiscing")
                .textColor(R.color.colorPrimaryDark)
                .font("monospace")
                .scaleSize(2)

                .last("eu")
                .textColor(R.color.colorPrimary)
                .size(24)

                .between("In", "ut")
                .textColor(R.color.mainText)
                .into(tv);

        TextViewHelper.with(this)
                .create(str2)
                .append("《绑卡协议》")
                .textColor(R.color.colorPrimary)
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Toast.makeText(MainActivity.this, "《绑卡协议》", Toast.LENGTH_SHORT).show();
                    }
                })
                .append("，")
                .append("《用户协议》")
                .textColor(R.color.colorAccent)
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Toast.makeText(MainActivity.this, "《用户协议》", Toast.LENGTH_SHORT).show();
                    }
                })
                .scaleSize(2)
                .into(tv2);
    }

}
