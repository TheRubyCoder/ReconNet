package petrinet.model;

import java.util.Map;

import petrinet.model.rnw.*;
import exceptions.ShowAsWarningException;

/**
 * Utility class for Renews. This pools the stateless Renews "id", "toggle" and
 * "count" and also offers a factory method {@link Renews#fromString(String)}
 */
public final class Renews {
	public static final IRenew IDENTITY = new Identity();
	public static final IRenew COUNT    = new Count();
	public static final IRenew TOGGLE   = new Toggle();

	public static IRenew fromString(String string) {
		if (string.equalsIgnoreCase("id")) {
			return IDENTITY;
		} else if (string.equalsIgnoreCase("count")) {
			return COUNT;
		} else if (string.equalsIgnoreCase("toggle")) {
			return TOGGLE;
		} else {
			throw new ShowAsWarningException(
				"\"" + string + "\" ist kein gültiger Renew-Bezeichner. Gültige Bezeichner sind: \"id\", \"count\", \"toggle\""
			);
		}
	}

	private Renews() {
	}

	public static final IRenew fromMap(Map<String, String> f) {
		return new Mapping(f);
	}
}
