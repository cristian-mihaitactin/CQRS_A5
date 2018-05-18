package com.ip.requestmanager.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Request.class)
public abstract class Request_ {

	public static volatile SingularAttribute<Request, Long> id;
	public static volatile SingularAttribute<Request, String> body;
	public static volatile SingularAttribute<Request, String> command;

}

