package com.wrbug.jsposeddemo;

import android.text.TextUtils;

import com.wrbug.jsposed.JsPosedExecutor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedInit implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        String str = readFile(new File("/data/local/tmp/tmp.js"));
        if (TextUtils.isEmpty(str)) {
            return;
        }
        JsPosedExecutor executor = JsPosedExecutor.init(lpparam, str, lpparam.packageName.equals("com.wrbug.jsposed"));
        executor.addJavaMethod(new Test());
        executor.run("start()");
    }

    public static String readFile(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            int ch = 0;
            while ((ch = fr.read()) != -1) {
                builder.append((char) ch);
            }
        } catch (IOException e) {
        }
        return builder.toString();
    }
}
