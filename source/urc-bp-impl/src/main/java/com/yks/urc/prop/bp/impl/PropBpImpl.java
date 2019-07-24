package com.yks.urc.prop.bp.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class PropBpImpl {
//	@Value("${ldap.root}")
	public String LDAP_ROOT;
}