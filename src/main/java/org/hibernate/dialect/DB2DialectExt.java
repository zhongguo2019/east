package org.hibernate.dialect;

public class DB2DialectExt extends DB2Dialect {

	public Class getNativeIdentifierGeneratorClass() {

		if (supportsSequences())
			return org.hibernate.id.SequenceGenerator.class;
		
		if (supportsIdentityColumns())
			return org.hibernate.id.IdentityGenerator.class;
		
		else
			return org.hibernate.id.TableHiLoGenerator.class;

	}

}
