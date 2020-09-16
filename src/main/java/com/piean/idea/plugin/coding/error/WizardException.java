package com.piean.idea.plugin.coding.error;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/9
 */
public class WizardException extends RuntimeException {
    private static final long serialVersionUID = 222019438558063622L;

    public WizardException(HintMsg hintMsg) {
        super(hintMsg.getMsg());
    }

    public WizardException(HintMsg hintMsg, Throwable cause) {
        super(hintMsg.getMsg(), cause);
    }
}
