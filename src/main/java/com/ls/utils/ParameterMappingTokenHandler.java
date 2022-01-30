package com.ls.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ls
 * @Description 参数映射处理器
 * @date 2022/1/29 20:51
 **/
public class ParameterMappingTokenHandler implements TokenHandler {
    /**
     * ParameterMapping 数组
     */
    private List<ParameterMapping> parameterMappings = new ArrayList<>();
    /**
     * 参数类型
     */
    private Class<?> parameterType;

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    @Override
    public String handleToken(String content) {
        // 构建 ParameterMapping 对象，并添加到 parameterMappings 中
        parameterMappings.add(buildParameterMapping(content));
        // 返回 ? 占位符
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }
}
