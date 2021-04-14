package com.shearf.cloud.base.util;

import com.shearf.cloud.base.error.IError;
import com.shearf.cloud.base.exception.BusinessException;
import com.shearf.cloud.base.exception.RuleException;

/**
 * 错误类工具
 *
 * @author xiahaihu2009@gmail.com
 * @since 2021/4/14 4:01 下午
 */
public class ExceptionUtil {

    private ExceptionUtil() {};

    /**
     * 抛出业务错误
     *
     * @param error 错误类
     * @throws BusinessException 业务错误
     */
    public static void throwBusinessException(IError error) throws BusinessException {
        throw new BusinessException(error);
    }

    /**
     * 抛出校验错误
     *
     * @param error 错误类
     * @throws RuleException 校验错误
     */
    public static void throwRuleException(IError error) throws RuleException {
        throw new RuleException(error);
    }

    /**
     * 抛出校验错误
     *
     * @param message 错误描述
     * @throws RuleException 校验错误
     */
    public static void throwRuleException(String message) throws RuleException {
        throw new RuleException(message);
    }
}
