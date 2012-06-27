package engine.data;

import engine.session.SessionManager;

/**
 * This is basically a mark up interface that is used in the
 * {@link SessionManager}
 */
public interface SessionData {
	/**
	 * @return the id of the Session
	 */
	public int getId();
}
