package com.selenium.framework.controls.elements;

import com.selenium.framework.controls.api.ImplementedBy;
import com.selenium.framework.controls.internals.Control;

/**
 * Created by Karthik-pc on 12/10/2016.
 */

@ImplementedBy(HyperLinkBase.class)
public interface HyperLink extends Control{


    void ClickLink();
    String GetUrlText();
    boolean CheckUrlTextContains(String containsText);
}
