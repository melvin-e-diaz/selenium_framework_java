package com.selenium.framework.controls.elements;

import com.selenium.framework.controls.api.ImplementedBy;
import com.selenium.framework.controls.internals.Control;

/**
 * Created by Karthik-pc on 12/10/2018.
 */

@ImplementedBy(TextBoxBase.class)
public interface TextBox extends Control {

    void EnterText(String text);
    String GetTextValue();

}
