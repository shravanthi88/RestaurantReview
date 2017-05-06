package cscie56.fp

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class Role implements Serializable {

	private static final long serialVersionUID = 1

	public static final String ROLE_CUSTOMER = 'ROLE_CUSTOMER'
	public static final String ROLE_REP = 'ROLE_REP'
	public static final String ROLE_ANONYMOUS = 'ROLE_ANONYMOUS'

	String authority

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
