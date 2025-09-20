package com.selenium.framework.controls.elements;

import com.selenium.framework.controls.internals.ControlBase;
import org.openqa.selenium.WebElement;

public class TextBoxBase extends ControlBase implements TextBox {


    public TextBoxBase(WebElement element) {
        super(element);
    }

    @Override
    public void EnterText(String text) {

        getWrappedElement().sendKeys(text);

    }

    @Override
    public String GetTextValue() {
        return getWrappedElement().getText();
    }
}