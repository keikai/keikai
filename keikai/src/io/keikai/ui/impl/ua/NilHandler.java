package io.keikai.ui.impl.ua;

import io.keikai.api.model.Book;
import io.keikai.api.model.Sheet;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.UserActionHandler;

/**
 * always disable and do nothing
 * @author dennis
 * @since 3.0.0
 */
public class NilHandler implements UserActionHandler {

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		return false;
	}

	@Override
	public boolean process(UserActionContext ctx) {
		return true;
	}

}
