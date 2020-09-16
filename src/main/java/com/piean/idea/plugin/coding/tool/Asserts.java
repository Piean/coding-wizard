package com.piean.idea.plugin.coding.tool;

import com.piean.idea.plugin.coding.error.HintMsg;
import com.piean.idea.plugin.coding.error.WizardException;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/9
 */
public final class Asserts {
    public static void notNull(Object obj, HintMsg hintMsg) {
        if (obj == null) {
            throw new WizardException(hintMsg);
        }
    }


    private Asserts() {
    }
}
