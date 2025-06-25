package com.selenium.framework.controls.elements;

import com.selenium.framework.controls.internals.ControlBase;
import org.openqa.selenium.WebElement;

/**
 * Created by Karthik-pc on 12/10/2016.
 */
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