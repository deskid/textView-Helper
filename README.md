# textView-Helper

### Useage

```java
TextView tv = (TextView) findViewById(R.id.textView);

String str = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Curabitur ipsum felis, sagittis vel fermentum eu, euismod aliquet massa. " +
                "Vestibulum ut dignissim tortor. Vivamus iaculis arcu ipsum. " +
                "Aliquam id mattis justo. Maecenas at nisl quis risus auctor congue. " +
                "Vestibulum ultrices nec nulla in facilisis. Nunc a erat pellentesque, lacinia lectus ut, malesuada purus. " +
                "Fusce a fringilla sem. Proin mattis eros eu pulvinar fringilla. In suscipit ut ligula at euismod.";

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
```

### todo

- release to maven

### thanks
- [SimpleText](https://github.com/jaychang0917/SimpleText)