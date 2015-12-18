package org.kaaproject.kaa.server.common.core.plugin.generator.java.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.kaaproject.kaa.server.common.core.plugin.generator.common.entity.Method;

public class JavaMethod implements Method {

    protected static final String DEFAULT_TEMPLATE = "%s { %s }";

    protected JavaMethodSignature signature;
    protected String body;

    public static JavaMethod getter(String propertyName, String propertyType) {
        String name = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        String body = "return this.${propertyName};";
        Map<String, String> values = new HashMap<>();
        values.put("${propertyName}", propertyName);
        return new JavaMethod(name, propertyType, new String[] {}, new String[] { "public" }, body, values);
    }

    public static JavaMethod setter(String propertyName, String propertyType) {
        String name = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        String body = "this.${propertyName} = ${propertyName};";
        Map<String, String> values = new HashMap<>();
        values.put("${propertyName}", propertyName);
        Map<String, String> params = new HashMap<>();
        params.put(propertyName, propertyType);
        return new JavaMethod(name, null, params, new String[] { "public" }, body, values);
    }

    public JavaMethod(String name, String returnType, Map<String, String> params, String[] modifiers, String body, Map<String, String> values) {

        this.signature = new JavaMethodSignature(name, returnType, params, modifiers) {
            @Override
            public boolean requiresTermination() {
                return false;
            }
        };

        this.body = this.insertValues(body, values);
    }

    public JavaMethod(String name, String returnType, String[] paramTypes, String[] modifiers, String body, Map<String, String> values) {

        this.signature = new JavaMethodSignature(name, returnType, paramTypes, modifiers) {
            @Override
            public boolean requiresTermination() {
                return false;
            }
        };

        this.body = this.insertValues(body, values);
    }

    @Override
    public String getBody() {
        return String.format(DEFAULT_TEMPLATE, this.signature.toString(), this.body).trim();
    }

    @Override
    public boolean requiresTermination() {
        return false;
    }

    @Override
    public String toString() {
        return this.getBody();
    }

    @Override
    public Set<String> getModifiers() {
        return this.signature.getModifiers();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((signature == null) ? 0 : signature.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JavaMethod other = (JavaMethod) obj;
        if (body == null) {
            if (other.body != null) {
                return false;
            }
        } else if (!body.equals(other.body)) {
            return false;
        }
        if (signature == null) {
            if (other.signature != null) {
                return false;
            }
        } else if (!signature.equals(other.signature)) {
            return false;
        }
        return true;
    }
}
