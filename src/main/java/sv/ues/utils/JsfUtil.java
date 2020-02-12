/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.utils;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;

/**
 *
 * @author PC
 */
public class JsfUtil {
    public static boolean isDummySelectItem(UIComponent component, String value) {
	for (UIComponent children : component.getChildren()) {
	    if (children instanceof UISelectItem) {
		UISelectItem item = (UISelectItem) children;
		if (item.getItemValue() == null && item.getItemLabel().equals(value)) {
		    return true;
		}
		break;
	    }
	}
	return false;
    }
}
